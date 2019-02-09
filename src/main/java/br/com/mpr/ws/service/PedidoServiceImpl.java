package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.PedidoServiceException;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 12/21/18.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PedidoServiceImpl implements PedidoService{

    private static final Log LOG = LogFactory.getLog(PedidoServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private NotificationService notificationService;


    @Override
    public PedidoEntity createPedido(String code, CheckoutForm checkoutForm) throws PedidoServiceException {
        LOG.info("m=createPedido, code=" + code + ", idCheckout=" + checkoutForm.getIdCheckout());
        CheckoutVo checkout = checkoutService.getCheckout(checkoutForm.getIdCheckout());
        PedidoEntity pedido = createPedido(code, checkout);
        pedido = commonDao.save(pedido);
        pedido.setItens(this.getItens(checkout.getCarrinho(), pedido.getId()));

        if (pedido.getId() != null){
            LOG.info("m=createPedido, pedido criado id=" + pedido.getId());
            LOG.debug("m=createPedido, removendo dados de checkout");
            this.deleteDadosCheckout(checkout);
            LOG.debug("m=createPedido, dados removidos com sucesso.");
        }else{
            throw new PedidoServiceException("Erro ao gerar o pedido");
        }

        //enviado notificacao de criado.
        notificationService.sendPedidoCriado(checkout.getCliente(), pedido);

        return pedido;
    }

    private void deleteDadosCheckout(CheckoutVo checkout) {
        //REMOVENDO OS DADOS DO CHECKOUT.
        commonDao.remove(CheckoutEntity.class, checkout.getId());

        //REMOVENDO OS ITENS DO CARRINHO
        for (ItemCarrinhoVo itemCarrinho : checkout.getCarrinho().getItems()){
            //REMOVENDO OS ANEXOS
            for (AnexoVo a : itemCarrinho.getAnexos()){
                commonDao.remove(ItemCarrinhoAnexoEntity.class, a.getId());
            }
            commonDao.remove(ItemCarrinhoEntity.class, itemCarrinho.getId());
        }
        //REMOVENDO O CARRINHO.
        commonDao.remove(CarrinhoEntity.class, checkout.getCarrinho().getIdCarrinho());
    }

    private PedidoEntity createPedido(String code, CheckoutVo checkout) {
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

        return pedido;
    }

    private List<ItemPedidoEntity> getItens(CarrinhoVo carrinho, Long idPedido) {
        List<ItemPedidoEntity> itens = new ArrayList<>();

        for (ItemCarrinhoVo item : carrinho.getItems()){
            ItemPedidoEntity itemPedido = new ItemPedidoEntity();
            itemPedido.setValor(item.getProduto().getPreco());
            itemPedido.setIdProduto(item.getProduto().getId());
            itemPedido.setIdPedido(idPedido);

            itemPedido = commonDao.save(itemPedido);

            itemPedido.setAnexos(this.getAnexos(item.getAnexos(), itemPedido.getId()));
            itens.add(itemPedido);
        }
        return itens;


    }

    private List<ItemPedidoAnexoEntity> getAnexos(List<AnexoVo> anexos, Long idItemPedido) {
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


}
