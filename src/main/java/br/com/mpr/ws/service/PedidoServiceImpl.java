package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.EstoqueServiceException;
import br.com.mpr.ws.exception.PedidoServiceException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.*;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 12/21/18.
 */
@Service
public class PedidoServiceImpl implements PedidoService{

    private static final Log LOG = LogFactory.getLog(PedidoServiceImpl.class);



    @Resource(name = "PedidoService.findPedido")
    private String QUERY_FIND_PEDIDO;

    @Resource(name = "PedidoService.findPedidoParamStatus")
    private String QUERY_FIND_PARAM_STATUS;

    @Resource(name = "PedidoService.findPedidoParamCliente")
    private String QUERY_FIND_PARAM_CLIENTE;

    @Resource(name = "PedidoService.findPedidoParamCodigo")
    private String QUERY_FIND_PARAM_CODIGO;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ClienteService clienteService;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PedidoEntity createPedido(CheckoutForm checkoutForm) throws PedidoServiceException {
        LOG.info("m=createPedido, idCheckout=" + checkoutForm.getIdCheckout());
        CheckoutVo checkout = checkoutService.getCheckout(checkoutForm.getIdCheckout());
        PedidoEntity pedido = this.createPedido("START", checkout, checkoutForm.getFormaPagamento());
        pedido = commonDao.save(pedido);
        pedido.setItens(this.createItens(checkout.getCarrinho(), pedido.getId()));

        if (pedido.getId() != null){
            LOG.info("m=createPedido, pedido criado id=" + pedido.getId());
            LOG.debug("m=createPedido, criando historico");
            this.createNovoHistorico(pedido.getId(),SysCodeType.PECR);

            LOG.debug("m=createPedido, removendo dados de checkout");
            this.deleteDadosCheckout(checkout);
            LOG.debug("m=createPedido, dados removidos com sucesso.");
            //dando baixa no estoque.
            try {
                LOG.debug("m=createPedido, dando baixa no estoque.");
                estoqueService.baixarEstoque(checkout.getCarrinho());
            } catch (EstoqueServiceException e) {
                throw new PedidoServiceException(e.getMessage());
            }


        }else{
            throw new PedidoServiceException("Erro ao gerar o pedido");
        }

        return pedido;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public HistoricoPedidoEntity createNovoHistorico(Long idPedido, SysCodeType sysCode) throws PedidoServiceException {
        LOG.info("m=createNovoHistorico");
        HistoricoPedidoEntity historicoPedido = new HistoricoPedidoEntity();
        historicoPedido.setIdPedido(idPedido);
        StatusPedidoEntity status = commonDao.findByPropertiesSingleResult(StatusPedidoEntity.class,
                new String[]{"syscode"},
                new Object[]{sysCode});

        if (status == null){
            throw new PedidoServiceException("O status não foi encontrado com seu código = " + sysCode);
        }
        LOG.debug("m=createNovoHistorico, status.code=" + status.getSyscode() +
                "status.nome=" + status.getNome());

        historicoPedido.setStatusPedido(status);
        historicoPedido.setData(DateUtils.now());
        historicoPedido = commonDao.save(historicoPedido);

        LOG.debug("m=createNovoHistorico, historico criado.");

        return historicoPedido;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PedidoEntity cancelarPedido(Long idPedido) throws PedidoServiceException {
        LOG.info("m=cancelarPedido");
        PedidoEntity pedido = this.getPedido(idPedido);
        if (pedido == null){
            throw new PedidoServiceException("Pedido não existe!");
        }

        LOG.debug("m=cancelarPedido, criando historico de cancelamento");
        HistoricoPedidoEntity historico = this.createNovoHistorico(pedido.getId(), SysCodeType.CACL);
        pedido.setStatusAtual(historico.getStatusPedido());

        //TODO VERIFICAR SE EXISTE EXCESSOES PARA ESTORNAR PRODUTOS PARA O ESTOQUE.
        LOG.debug("m=cancelarPedido, retornando produtos para o estoque.");
        try {
            this.estoqueService.retornarEstoque(pedido);
        } catch (EstoqueServiceException e) {
            throw new PedidoServiceException(e.getMessage());
        }
        notificationService.sendPedidoCancelado(clienteService.getClienteById(pedido.getIdCliente()),
                pedido);
        return pedido;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PedidoEntity confirmarPedido(String code, Long idPedido, String urlBoleto) throws PedidoServiceException {
        PedidoEntity pedido = commonDao.get(PedidoEntity.class, idPedido);
        if (pedido == null){
            throw new PedidoServiceException("Pedido não existe!");
        }
        pedido.setCodigoTransacao(org.springframework.util.StringUtils.isEmpty(code) ? "NO_TRANSACTION" : code);
        pedido.setUrlBoleto(urlBoleto);
        pedido = commonDao.update(pedido);
        HistoricoPedidoEntity historicoPedidoEntity = this.createNovoHistorico(pedido.getId(),SysCodeType.AGPG);
        pedido.setStatusAtual(historicoPedidoEntity.getStatusPedido());
        pedido.setItens(getItens(idPedido));

        ClienteEntity cliente = clienteService.getClienteById(pedido.getIdCliente());
        if (PagamentoType.BOLETO.equals(pedido.getPagamentoType())){
            this.sendTransactionCriadaBoleto(cliente, pedido);
        }else{
            this.sendTransactionCriadaCartao(cliente, pedido);
        }

        return pedido;
    }

    @Override
    public PedidoEntity confirmarPagamento(String code, Long idPedido) throws PedidoServiceException {

        PedidoEntity pedido = commonDao.get(PedidoEntity.class, idPedido);
        if (pedido == null){
            throw new PedidoServiceException("Pedido não existe!");
        }
        pedido.setCodigoTransacao(org.springframework.util.StringUtils.isEmpty(code) ? "NO_TRANSACTION" : code);
        pedido = commonDao.update(pedido);
        HistoricoPedidoEntity historicoPedidoEntity = this.createNovoHistorico(pedido.getId(), SysCodeType.PGCF);
        pedido.setStatusAtual(historicoPedidoEntity.getStatusPedido());
        pedido.setItens(getItens(idPedido));

        this.sendPagamentoConfirmado(clienteService.getClienteById(pedido.getIdCliente()), pedido);

        return pedido;


    }

    @Override
    public PedidoEntity getPedido(Long idPedido) {
        PedidoEntity pedido = commonDao.get(PedidoEntity.class, idPedido);
        pedido.setStatusAtual(this.getStatusAtual(idPedido));
        pedido.setItens(this.getItens(pedido.getId()));

        return pedido;
    }

    @Override
    public PedidoEntity findPedidoByTransactionCode(String transactionCode) {
        PedidoEntity pedido = commonDao.findByPropertiesSingleResult(PedidoEntity.class,
                new String[]{"codigoTransacao"},
                new Object[]{transactionCode});
        pedido.setStatusAtual(this.getStatusAtual(pedido.getId()));
        pedido.setItens(this.getItens(pedido.getId()));
        return pedido;
    }

    @Override
    public PedidoEntity findPedidoByCodigo(String codigo) {
        PedidoEntity pedido = commonDao.findByPropertiesSingleResult(PedidoEntity.class,
                new String[]{"codigoPedido"},
                new Object[]{codigo});
        pedido.setStatusAtual(this.getStatusAtual(pedido.getId()));
        pedido.setItens(this.getItens(pedido.getId()));
        return pedido;
    }

    @Override
    public List<PedidoEntity> findPedidoByStatus(SysCodeType sysCode) {

        return commonDao.findByNativeQuery(QUERY_FIND_PEDIDO + " " + QUERY_FIND_PARAM_STATUS,
                PedidoEntity.class,
                new String[]{"sysCode"},
                new Object[]{sysCode.toString()},false);
    }

    @Override
    public List<PedidoEntity> findPedidoByIdCliente(Long idCliente) {
        List<PedidoEntity> list = commonDao.findByProperties(PedidoEntity.class,
                new String[]{"idCliente"},
                new Object[]{idCliente});

        for (PedidoEntity p : list){
            p.setStatusAtual(this.getStatusAtual(p.getId()));
            p.setItens(this.getItens(p.getId()));
        }

        return list;
    }

    @Override
    public Collection<PedidoEntity> findPedidoByForm(PedidoFindForm pedidoFindForm) {
        List<PedidoEntity> list = commonDao.findByNativeQuery(this.getQuery(pedidoFindForm),
                PedidoEntity.class,
                this.getParamNames(pedidoFindForm),
                this.getParamValues(pedidoFindForm));

        for (PedidoEntity p : list){
            p.setStatusAtual(this.getStatusAtual(p.getId()));
            p.setItens(this.getItens(p.getId()));
            p.setCliente(this.clienteService.getClienteById(p.getIdCliente()));
        }

        return list;
    }

    private String getQuery(PedidoFindForm pedidoFindForm) {
        String query = QUERY_FIND_PEDIDO;
        if (pedidoFindForm.getSysCode() != null){
            query += " " + QUERY_FIND_PARAM_STATUS;
        }
        if (pedidoFindForm.getIdCliente() != null){
            query += " " + QUERY_FIND_PARAM_CLIENTE;
        }
        if (!org.springframework.util.StringUtils.isEmpty(pedidoFindForm.getCodigo())){
            query += " " + QUERY_FIND_PARAM_CODIGO;
        }
        return query;
    }

    private Object[] getParamValues(PedidoFindForm pedidoFindForm) {
        List<Object> paramValues = new ArrayList<>();
        if (pedidoFindForm.getSysCode() != null){
            paramValues.add(pedidoFindForm.getSysCode().toString());
        }
        if (pedidoFindForm.getIdCliente() != null){
            paramValues.add(pedidoFindForm.getIdCliente());
        }
        if (!org.springframework.util.StringUtils.isEmpty(pedidoFindForm.getCodigo())){
            paramValues.add(pedidoFindForm.getCodigo());
        }
        return paramValues.toArray(new Object[]{});
    }

    private String[] getParamNames(PedidoFindForm pedidoFindForm) {
        List<String> paramNames = new ArrayList<>();
        if (pedidoFindForm.getSysCode() != null){
            paramNames.add("sysCode");
        }
        if (pedidoFindForm.getIdCliente() != null){
            paramNames.add("idCliente");
        }
        if (!org.springframework.util.StringUtils.isEmpty(pedidoFindForm.getCodigo())){
            paramNames.add("codigo");
        }
        return paramNames.toArray(new String[]{});
    }

    private List<ItemPedidoEntity> getItens(Long id) {
        List<ItemPedidoEntity> items = commonDao.findByProperties(ItemPedidoEntity.class,
                new String[]{"idPedido"},
                new Object[]{id});

        for (ItemPedidoEntity item : items){
            item.setAnexos(this.getAnexos(item.getId()));
        }
        return items;
    }

    private List<ItemPedidoAnexoEntity> getAnexos(Long idItemPedido) {
        return commonDao.findByProperties(ItemPedidoAnexoEntity.class,
                new String[]{"idItemPedido"},
                new Object[]{idItemPedido});
    }

    private StatusPedidoEntity getStatusAtual(Long idPedido) {
        List<HistoricoPedidoEntity> listHistorico = commonDao.findByProperties(HistoricoPedidoEntity.class,
                new String[]{"idPedido"},
                new Object[]{idPedido});

        Date maxDate = null;
        HistoricoPedidoEntity atualHist = null;
        for (HistoricoPedidoEntity hist : listHistorico){
            if ( maxDate == null || DateUtils.isLesser(maxDate, hist.getData()) ){
                maxDate = hist.getData();
                atualHist = hist;
            }
        }
        return atualHist != null ? atualHist.getStatusPedido() : null;
    }

    private void deleteDadosCheckout(CheckoutVo checkout) {
        LOG.info("m=deleteDadosCheckout");
        //REMOVENDO OS DADOS DO CHECKOUT.
        LOG.debug("m=deleteDadosCheckout, removendo dados do checkout");
        commonDao.remove(CheckoutEntity.class, checkout.getId());

        //REMOVENDO OS ITENS DO CARRINHO
        LOG.debug("m=deleteDadosCheckout, removendo os itens do carrinho e anexos");
        for (ItemCarrinhoVo itemCarrinho : checkout.getCarrinho().getItems()){
            //REMOVENDO OS ANEXOS
            for (AnexoVo a : itemCarrinho.getAnexos()){
                commonDao.remove(ItemCarrinhoAnexoEntity.class, a.getId());
            }
            commonDao.remove(ItemCarrinhoEntity.class, itemCarrinho.getId());
        }
        //REMOVENDO O CARRINHO.
        LOG.debug("m=deleteDadosCheckout, removendo o carrinho do cliente");
        commonDao.remove(CarrinhoEntity.class, checkout.getCarrinho().getIdCarrinho());
    }

    private PedidoEntity createPedido(String code, CheckoutVo checkout, FormaPagamentoVo formaPagamento) {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigoTransacao(code);
        pedido.setIdEndereco(checkout.getEndereco().getId());
        pedido.setIdCliente(checkout.getCliente().getId());
        pedido.setValorFrete(checkout.getValorFrete());
        pedido.setValorTotal(checkout.getValorTotal());
        pedido.setValorProdutos(checkout.getValorProdutos());
        pedido.setValorDesconto(checkout.getValorDesconto());
        pedido.setData(new Date());

        //passou no teste de mais de 500mil sorteios sem repetir
        pedido.setCodigoPedido(StringUtils.createRandomHash() + StringUtils.createRandomHash());
        pedido.setIdCupom(checkout.getCupom() != null ? checkout.getCupom().getId() : null);
        pedido.setDataEntrega(checkout.getFreteSelecionado().getPrevisaoEntrega());
        pedido.setTipoFrete(checkout.getFreteSelecionado().getFreteType());
        pedido.setPagamentoType(formaPagamento.getPagamentoType());

        return pedido;
    }

    private List<ItemPedidoEntity> createItens(CarrinhoVo carrinho, Long idPedido) {
        List<ItemPedidoEntity> itens = new ArrayList<>();

        for (ItemCarrinhoVo item : carrinho.getItems()){
            ItemPedidoEntity itemPedido = new ItemPedidoEntity();
            itemPedido.setValor(item.getProduto().getPreco());
            itemPedido.setIdProduto(item.getProduto().getId());
            itemPedido.setIdPedido(idPedido);
            itemPedido.setIdEstoque(item.getIdEstoque());
            itemPedido = commonDao.save(itemPedido);
            itemPedido.setAnexos(this.createAnexos(item.getAnexos(), itemPedido.getId()));
            itens.add(itemPedido);
        }
        return itens;


    }

    private List<ItemPedidoAnexoEntity> createAnexos(List<AnexoVo> anexos, Long idItemPedido) {
        List<ItemPedidoAnexoEntity> itemAnexos = new ArrayList<>();
        for (AnexoVo a : anexos){
            ItemPedidoAnexoEntity itemAnexo = new ItemPedidoAnexoEntity();
            itemAnexo.setFoto(a.getUrlFoto());
            itemAnexo.setIdCatalogo(a.getIdCatalogo());
            itemAnexo.setIdItemPedido(idItemPedido);
            itemAnexo = commonDao.save(itemAnexo);

            itemAnexos.add(itemAnexo);
        }
        return itemAnexos;
    }


    private void sendTransactionCriadaCartao(ClienteEntity cliente, PedidoEntity pedido) {
        notificationService.sendTransactionCriadaCartao(cliente,pedido);
    }

    private void sendTransactionCriadaBoleto(ClienteEntity cliente, PedidoEntity pedido) {
        notificationService.sendTransactionCriadaBoleto(cliente, pedido, pedido.getUrlBoleto());
    }

    private void sendPagamentoConfirmado(ClienteEntity cliente, PedidoEntity pedido) {
        notificationService.sendPagamentoConfirmado(cliente, pedido);
    }

}
