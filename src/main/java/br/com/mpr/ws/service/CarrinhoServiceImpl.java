package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.vo.AnexoVo;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import br.com.mpr.ws.vo.ItemCarrinhoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 *
 */
@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    private static final Log LOG = LogFactory.getLog(CarrinhoServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private EmbalagemService embalagemService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ProdutoPreviewService produtoPreviewService;


    @Override
    public CarrinhoVo addCarrinho(String sessionToken) throws CarrinhoServiceException {
        if (StringUtils.isEmpty(sessionToken)){
            throw new CarrinhoServiceException("SessionToken é obrigatório.");
        }

        ProdutoPreviewEntity produtoPreviewEntity = produtoPreviewService
                .getProdutoPreviewBySessionToken(sessionToken);

        if (produtoPreviewEntity == null){
            throw new CarrinhoServiceException("Preview do cliente não encontrado, ou a sessão está expirada.");
        }

        return addCarrinho(produtoPreviewEntity);
    }

    @Override
    public CarrinhoVo addCarrinho(Long idCliente) throws CarrinhoServiceException {

        if (idCliente == null){
            throw new CarrinhoServiceException("Id do cliente é obrigatório.");
        }
        ProdutoPreviewEntity produtoPreviewEntity = produtoPreviewService
                .getProdutoPreviewByIdCliente(idCliente);

        if (produtoPreviewEntity == null){
            throw new CarrinhoServiceException("Preview do cliente não encontrado!");
        }

        return addCarrinho(produtoPreviewEntity);
    }

    /**
     * Esse metodo cria uma fila de threads e executa 1 a 1 em startThreadAddCarrinho
     *
     * Esse mecanismo foi criado para evitar concorrencias ao adicionar um item no carrinho.
     *
     * Exemplo1: Sabemos que no estoque temos 18 produtos do tipo 'A', se 20 clientes simultaneamente tentassem adicionar
     * o produto 'A' no carrinho, teriamos um problema na consulta que valida se temos produto em estoque, porque para
     * as 20 consultas o resultado seria positivo, devido aos milisecundos entre o commit de uma Thread-X até o select
     * de uma Thread-Y.
     *
     * Exemplo2: A query que retorna um item do estoque, poderia retornar o mesmo item para Threads concorrentes,
     * gerando um ConstraintViolationException porque ItemEstoque no carrinho é unique. Um cliente nao ficaria feliz.
     *
     * @param item Formulario com dados do item do carrinho
     * @return Vo com dados do carrinho do cliente.
     * @throws CarrinhoServiceException
     */
    private CarrinhoVo addCarrinho(ProdutoPreviewEntity item) throws CarrinhoServiceException {

        //chave da thread em questao.
        String key = br.com.mpr.ws.utils.StringUtils.createRandomHash();
        CarrinhoThreadExecutor.putExecute(key,item);
        this.startThreadAddCarrinho();

        Date start = new Date();
        while (CarrinhoThreadExecutor.containsExecute(key)){
            try {
                LOG.debug("Aguardando para adicionar o carrinho [" + key + "]");
                Thread.sleep(100);
                this.startThreadAddCarrinho();


                if (start.getTime() + CarrinhoThreadExecutor.TIMEOUT <  new Date().getTime()){
                    CarrinhoThreadExecutor.removeExecute(key);
                }

            } catch (InterruptedException e) {
                LOG.error("Error no sleep da Thread");
                new CarrinhoServiceException("Erro ao adicionar o produto no carrinho.");
            }
        }

        if (CarrinhoThreadExecutor.containsResult(key)){
            CarrinhoVo vo = CarrinhoThreadExecutor.getResult(key);
            CarrinhoThreadExecutor.removeResult(key);
            return vo;
        }

        if (CarrinhoThreadExecutor.containsException(key)){
            CarrinhoServiceException e = CarrinhoThreadExecutor.getException(key);
            CarrinhoThreadExecutor.removeException(key);
            throw e;
        }

        throw new CarrinhoServiceException("Ocorreu um erro ao adicionar um item no carrinho [TIMEOUT]");

    }

    /**
     * Metodo para controlar a executacao das Threads.
     *
     * Esse metodo é executado apenas por uma Thread.
     * O metodo addCarrinho, vai empilhando os parametros das Thread, e quando esse metodo for liberado,
     * a Thread que conseguir chama-la executa a pilha inteira.
     *
     * A pilha fica armazenada em CarrinhoThreadExecutor.getIterator();
     *
     */
    private synchronized void startThreadAddCarrinho(){

        if (!CarrinhoThreadExecutor.isAlive()){
            CarrinhoThreadExecutor.setAlive(true);
            Iterator<String> it = CarrinhoThreadExecutor.getIterator();

            if (!CarrinhoThreadExecutor.isEmpty()){
                while (it.hasNext()){
                    String key = it.next();
                    try {
                        ProdutoPreviewEntity itemCarrinho = CarrinhoThreadExecutor.getExecute(key);
                        if (itemCarrinho == null){
                            continue;
                        }
                        CarrinhoVo vo = this.addTransactionCarrinho(itemCarrinho);
                        CarrinhoThreadExecutor.putResult(key,vo);
                        CarrinhoThreadExecutor.removeExecute(key);
                    } catch (CarrinhoServiceException e) {
                        CarrinhoThreadExecutor.putException(key,e);
                        CarrinhoThreadExecutor.removeExecute(key);
                    }
                }

            }
            CarrinhoThreadExecutor.setAlive(false);
        }


    }

    /**
     * Metodo que adiciona um item no carrinho do cliente.
     * @param item Produto preview
     * @return
     * @throws CarrinhoServiceException
     */
    private CarrinhoVo addTransactionCarrinho(ProdutoPreviewEntity item) throws CarrinhoServiceException {
        try{
            //tentando procurar um carrinho do cliente.
            CarrinhoEntity carrinhoEntity = this.findCarrinho(item.getIdCliente(), item.getIdSession());

            if (carrinhoEntity == null){
                carrinhoEntity = this.createCarrinho(item.getIdCliente(),item.getIdSession());
            }

            ItemCarrinhoEntity itemCarrinhoEntity = new ItemCarrinhoEntity();
            itemCarrinhoEntity.setIdCarrinho(carrinhoEntity.getId());
            EstoqueItemEntity estoqueItemEntity = produtoService.getProdutoEmEstoque(item.getIdProduto());

            if (estoqueItemEntity == null){
                //TODO: DISPARAR UM EMAIL AQUI, PORQUE ISSO NAO PODE ACONTECER.
                throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
            }
            if (CollectionUtils.isEmpty(item.getAnexos()) ){
                throw new CarrinhoServiceException("Para adicionar esse produto no carrinho, uma imagem é obrigatória!");
            }

            for (ProdutoPreviewAnexoEntity anexoVo : item.getAnexos()) {
                if (!anexoVo.temFoto() && !anexoVo.temCatalogo()) {
                    throw new CarrinhoServiceException("Imagem não encontrada no preview do carrinho.");
                }
            }

            itemCarrinhoEntity.setIdEstoqueItem(estoqueItemEntity.getId());
            itemCarrinhoEntity.setFotoPreview(item.getFotoPreview());
            itemCarrinhoEntity = commonDao.save(itemCarrinhoEntity);

            //CLIENTES PODEM ADICIONAR ACESSORIOS, SE NAO FOR UM ACESSORIO ENTÃO TEM FOTO.
            if ( !produtoService.isAcessorio(item.getIdProduto()) ){
                for (ProdutoPreviewAnexoEntity previewAnexo : item.getAnexos()){
                    ItemCarrinhoAnexoEntity carrinhoAnexo = new ItemCarrinhoAnexoEntity();

                    if (previewAnexo.temFoto()){
                        carrinhoAnexo.setFoto(previewAnexo.getFoto());
                    }

                    if (previewAnexo.temCatalogo()){
                        CatalogoEntity catalogo = commonDao.get(CatalogoEntity.class,previewAnexo.getIdCatalogo());
                        carrinhoAnexo.setFoto(catalogo.getImg());
                    }
                    carrinhoAnexo.setIdCatalogo(previewAnexo.getIdCatalogo());
                    carrinhoAnexo.setIdItemCarrinho(itemCarrinhoEntity.getId());
                    commonDao.save(carrinhoAnexo);
                }
            }




            return this.getCarrinho(item.getIdCliente(),item.getIdSession(),
                                        true);


        }catch (ConstraintViolationException ex){
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", ex);
            throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
        }catch (DataIntegrityViolationException ex){
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", ex);
            throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
        }
    }

    private CarrinhoEntity createCarrinho(Long idCliente, Long idSession){
        CarrinhoEntity carrinhoEntity;
        carrinhoEntity = new CarrinhoEntity();
        carrinhoEntity.setIdCliente(idCliente);
        carrinhoEntity.setIdSession(idSession);
        carrinhoEntity.setDataCriacao(new Date());
        carrinhoEntity = commonDao.save(carrinhoEntity);
        return carrinhoEntity;
    }

    private SessionEntity getSession(String sessionToken) {
        if (sessionToken == null){
            return null;
        }
        return sessionService.getSessionByToken(sessionToken);
    }

    private CarrinhoEntity findCarrinho(Long idCliente, Long idSession) {

        if (idCliente != null && idCliente > 0){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"idCliente"}, new Object[]{idCliente});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }else if (idSession != null && idSession > 0){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"idSession"}, new Object[]{idSession});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }

        return null;
    }

    @Override
    public CarrinhoVo getCarrinhoByIdCliente(Long idCliente){
        return getCarrinho(idCliente,null, true);
    }

    @Override
    public CarrinhoVo getCarrinhoBySessionToken(String sessionToken) throws CarrinhoServiceException {
        SessionEntity sessionEntity = getSession(sessionToken);
        if ( sessionEntity == null ){
            throw new CarrinhoServiceException("Sessão do cliente não encontrada.");
        }
        return getCarrinho(null, sessionEntity.getId(), true);
    }

    private CarrinhoVo getCarrinho(Long idCliente, Long idSession, Boolean calculateFrete) {
        CarrinhoEntity carrinhoEntity = this.findCarrinho(idCliente,idSession);

        if (carrinhoEntity == null){
            CarrinhoVo vo = new CarrinhoVo();
            vo.setIdCliente(idCliente);
            if (idSession != null){
                String sessionToken = sessionService.getSessionTokenById(idSession);
                vo.setSessionToken(sessionToken);
            }

            return vo;
        }

        CarrinhoVo vo = this.getCarrinhoVo(carrinhoEntity, calculateFrete);
        if (idSession != null){
            String sessionToken = sessionService.getSessionTokenById(idSession);
            vo.setSessionToken(sessionToken);
        }

        return vo;
    }

    private CarrinhoVo getCarrinhoVo(CarrinhoEntity carrinhoEntity, Boolean calculaFrete) {
        CarrinhoVo vo = CarrinhoVo.toVo(carrinhoEntity);
        List<ItemCarrinhoEntity> items = this.getItensCarrinho(carrinhoEntity.getId());


        List<ItemCarrinhoVo> listVos = new ArrayList<>();
        List<ProdutoEntity> produtos = new ArrayList<>();
        Double peso = 0.0;
        for (ItemCarrinhoEntity i : items){
            ItemCarrinhoVo ivo = getItemCarrinhoVo(i);
            ProdutoEntity produtoEntity = produtoService.getProdutoEntityById(i.getEstoqueItem().getIdProduto());
            produtos.add(produtoEntity);
            peso = produtoEntity.getPeso();
            listVos.add(ivo);
        }

        //NAO PRECISA CALCULAR O FRETE PARA TODOS QUE SOLICITAM O CARRINHO VO e se o cliente ainda não tem cadastro.
        if ( calculaFrete && carrinhoEntity.getIdCliente() != null){
            EnderecoEntity enderecoCliente = clienteService.getEnderecoPrincipalByIdCliente(carrinhoEntity.getIdCliente());

            //para calcular o frete precisa do endereco do cliente e produtos no carrinho
            if (enderecoCliente != null && produtos.size() > 0){
                EmbalagemEntity embalagem = embalagemService.getEmbalagem(produtos);
                vo.setResultFrete(freteService.calcFrete(
                        new FreteService.FreteParam(FreteType.ECONOMICO,
                                enderecoCliente.getCep(),
                                peso,
                                embalagem.getComp(),
                                embalagem.getLarg(),
                                embalagem.getAlt()
                        )) );
            }

        }

        vo.setItems(listVos);
        return vo;
    }

    private List<ItemCarrinhoEntity> getItensCarrinho(Long idCarrinho) {
        return commonDao.findByProperties(
                ItemCarrinhoEntity.class,
                new String[]{"idCarrinho"},
                new Object[]{idCarrinho});
    }

    private ItemCarrinhoVo getItemCarrinhoVo(ItemCarrinhoEntity i) {
        i.setAnexos(this.commonDao.findByProperties(ItemCarrinhoAnexoEntity.class,
                new String[]{"idItemCarrinho"},
                new Object[]{i.getId()}));
        ItemCarrinhoVo ivo = new ItemCarrinhoVo();
        BeanUtils.copyProperties(i, ivo);
        EstoqueItemEntity itemEstoque = this.commonDao.get(EstoqueItemEntity.class, i.getIdEstoqueItem());
        ivo.setProduto(this.produtoService.getProdutoById(itemEstoque.getIdProduto()));
        ivo.setIdEstoque(itemEstoque.getIdEstoque());
        ivo.setIdEstoqueItem(itemEstoque.getId());
        if (!CollectionUtils.isEmpty(i.getAnexos())){
            ivo.setAnexos(new ArrayList<>());
            for (ItemCarrinhoAnexoEntity anexo : i.getAnexos()){
                AnexoVo anexoVo = new AnexoVo();
                anexoVo.setId(anexo.getId());
                if (anexo.getIdCatalogo() != null){
                    anexoVo.setIdCatalogo(anexo.getIdCatalogo());
                    anexoVo.setUrlFoto(this.imagemService.getUrlFotoCatalogo(anexo.getFoto()));
                }else{
                    anexoVo.setUrlFoto(this.imagemService.getUrlFotoCliente(anexo.getFoto()));
                }
                ivo.getAnexos().add(anexoVo);
            }
        }
        return ivo;
    }

    @Override
    public CarrinhoVo removeItem(Long idItem, Long idCliente) throws CarrinhoServiceException {
        try{
            ItemCarrinhoEntity item = this.commonDao.get(ItemCarrinhoEntity.class, idItem);
            if (item != null){
                CarrinhoEntity carrinhoEntity = this.commonDao.get(CarrinhoEntity.class,item.getIdCarrinho());

                if (!carrinhoEntity.getIdCliente().equals(idCliente)){
                    LOG.error("m=removeItem, ERRO BIZARRO!!!, UM CLIENTE TENTOU REMOVER UM ITEM QUE NAO É DELE.");
                    throw new CarrinhoServiceException("Erro ao remover um item do carrinho.");
                }
                this.commonDao.remove(ItemCarrinhoEntity.class, idItem);
                return this.getCarrinho(carrinhoEntity.getIdCliente(),carrinhoEntity.getIdSession(), false);
            }
            return null;
        }catch(Exception ex){
            LOG.error("Erro ao remover um item do carrinho.", ex);
            throw new CarrinhoServiceException("Erro ao remover o item do carrinho");
        }
    }

    @Override
    public CarrinhoVo removeItem(Long idItem, String sessionToken) throws CarrinhoServiceException {
        try{
            ItemCarrinhoEntity item = this.commonDao.get(ItemCarrinhoEntity.class, idItem);
            if (item != null){
                CarrinhoEntity carrinhoEntity = this.commonDao.get(CarrinhoEntity.class,item.getIdCarrinho());
                SessionEntity sessionEntity = getSession(sessionToken);
                if (sessionEntity == null){
                    throw new CarrinhoServiceException("Sessão do cliente não encontrada!");
                }

                if (!carrinhoEntity.getIdSession().equals(sessionEntity.getId())){
                    LOG.error("m=removeItem, ERRO BIZARRO!!!, UM CLIENTE COM SESSION_TOKEN TENTOU REMOVER UM ITEM QUE NAO É DELE.");
                    throw new CarrinhoServiceException("Erro ao remover um item do carrinho.");
                }
                this.commonDao.remove(ItemCarrinhoEntity.class, idItem);
                return this.getCarrinho(carrinhoEntity.getIdCliente(),carrinhoEntity.getIdSession(), false);
            }
            return null;
        }catch(Exception ex){
            LOG.error("Erro ao remover um item do carrinho.", ex);
            throw new CarrinhoServiceException("Erro ao remover o item do carrinho");
        }
    }

    @Override
    public CarrinhoVo getCarrinhoById(Long idCarrinho) throws CarrinhoServiceException {
        CarrinhoEntity carrinhoEntity = commonDao.get(CarrinhoEntity.class, idCarrinho);

        if (carrinhoEntity == null){
            throw new CarrinhoServiceException("Carrinho não encontrado com o ID = " + idCarrinho);
        }

        return this.getCarrinhoVo(carrinhoEntity, true);
    }

    @Override
    public void moveCarSessionParaCarLogado(String sessionToken, Long idCliente) throws CarrinhoServiceException {
        CarrinhoVo carrinhoSession = getCarrinhoBySessionToken(sessionToken);
        if (carrinhoSession.getIdCarrinho() == null){
            throw new CarrinhoServiceException("Nenhum carrinho encontrado para a sessionToken = " + sessionToken);
        }

        if (CollectionUtils.isEmpty(carrinhoSession.getItems())){
            throw new CarrinhoServiceException("O carrinho da session do cliente está vazio.");
        }

        CarrinhoVo carrinhoCliente = getCarrinhoByIdCliente(idCliente);
        CarrinhoEntity carrinhoClienteEntity = null;

        if (carrinhoCliente.getIdCarrinho() == null){
            carrinhoClienteEntity = this.createCarrinho(idCliente, null);
        }

        final Long idCarrinhoCliente = carrinhoClienteEntity == null ? carrinhoCliente.getIdCarrinho() :
                carrinhoClienteEntity.getId();

        //movendo os itens do carrinho da sessao para carrinho logado.
        this.getItensCarrinho(carrinhoSession.getIdCarrinho()).stream().forEach( item -> {
            item.setIdCarrinho(idCarrinhoCliente);
            commonDao.update(item);
        });

        //removendo o carrinho de sessao.
        commonDao.remove(CarrinhoEntity.class,carrinhoSession.getIdCarrinho());

    }


}
