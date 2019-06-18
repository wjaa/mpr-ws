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
    @Override
    public CarrinhoVo addCarrinho(ItemCarrinhoForm item) throws CarrinhoServiceException {

        if (StringUtils.isEmpty(item.getSessionToken()) && item.getIdCliente() == null){
            throw new CarrinhoServiceException("Id do cliente ou keyDevice é obrigatório.");
        }

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
     * O metodo addCarrinho, vai empinhando os parametros das Thread, e quando esse metodo for liberado,
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
                        ItemCarrinhoForm itemCarrinho = CarrinhoThreadExecutor.getExecute(key);
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
     * @param item Form do item do carrinho
     * @return
     * @throws CarrinhoServiceException
     */
    private CarrinhoVo addTransactionCarrinho(ItemCarrinhoForm item) throws CarrinhoServiceException {
        try{
            //tentando procurar um carrinho do cliente.
            CarrinhoEntity carrinhoEntity = this.findCarrinho(item.getIdCliente(), item.getSessionToken());

            if (carrinhoEntity == null){
                carrinhoEntity = new CarrinhoEntity();
                carrinhoEntity.setIdCliente(item.getIdCliente());
                carrinhoEntity.setSessionToken(item.getSessionToken());
                carrinhoEntity.setDataCriacao(new Date());
                carrinhoEntity = commonDao.save(carrinhoEntity);
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

            for (AnexoVo anexoVo : item.getAnexos()) {
                if (!anexoVo.temFoto() && !anexoVo.temCatalogo()) {
                    throw new CarrinhoServiceException("Para adicionar esse produto no carrinho, uma imagem é obrigatória!");
                }
            }

            itemCarrinhoEntity.setIdEstoqueItem(estoqueItemEntity.getId());
            itemCarrinhoEntity = commonDao.save(itemCarrinhoEntity);

            //CLIENTES PODEM ADICIONAR ACESSORIOS, SE NAO FOR UM ACESSORIO ENTÃO TEM FOTO.
            if ( !produtoService.isAcessorio(item.getIdProduto()) ){
                for (AnexoVo anexoVo : item.getAnexos()){
                    ItemCarrinhoAnexoEntity anexo = new ItemCarrinhoAnexoEntity();

                    if (anexoVo.temFoto()){
                        anexo.setFoto(imagemService.uploadFotoCliente(anexoVo.getFoto(),anexoVo.getNomeArquivo()));
                    }

                    if (anexoVo.temCatalogo()){
                        CatalogoEntity catalogo = commonDao.get(CatalogoEntity.class,anexoVo.getIdCatalogo());
                        anexo.setFoto(catalogo.getImg());
                    }
                    anexo.setIdCatalogo(anexoVo.getIdCatalogo());
                    anexo.setIdItemCarrinho(itemCarrinhoEntity.getId());
                    commonDao.save(anexo);
                }
            }

            return this.getCarrinho(item.getIdCliente(),item.getSessionToken(), true);


        }catch (ConstraintViolationException ex){
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", ex);
            throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
        }catch (DataIntegrityViolationException ex){
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", ex);
            throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
        } catch (ImagemServiceException e) {
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", e);
            throw new CarrinhoServiceException(e.getMessage());
        }
    }

    /*private CarrinhoEntity findCarrinho(ItemCarrinhoForm item) {

        if ( item.getIdCarrinho() != null ){
            return commonDao.get(CarrinhoEntity.class,item.getIdCarrinho());
        }else{

            return findCarrinho(item.getIdCliente(),item.getSessionToken());

        }
    }*/

    private CarrinhoEntity findCarrinho(Long idCliente, String sessionToken) {

        if (idCliente != null && idCliente > 0){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"idCliente"}, new Object[]{idCliente});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }else if (!StringUtils.isEmpty(sessionToken)){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"sessionToken"}, new Object[]{sessionToken});

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
    public CarrinhoVo getCarrinhoBySessionToken(String sessionToken){
        return getCarrinho(null, sessionToken, true);
    }

    private CarrinhoVo getCarrinho(Long idCliente, String sessionToken, Boolean calculateFrete) {
        CarrinhoEntity carrinhoEntity = this.findCarrinho(idCliente,sessionToken);

        if (carrinhoEntity == null){
            CarrinhoVo vo = new CarrinhoVo();
            vo.setIdCliente(idCliente);
            vo.setSessionToken(sessionToken);
            return vo;
        }

        return this.getCarrinhoVo(carrinhoEntity, calculateFrete);
    }

    private CarrinhoVo getCarrinhoVo(CarrinhoEntity carrinhoEntity, Boolean calculaFrete) {
        CarrinhoVo vo = CarrinhoVo.toVo(carrinhoEntity);
        List<ItemCarrinhoEntity> items =
                commonDao.findByProperties(
                        ItemCarrinhoEntity.class,
                        new String[]{"idCarrinho"},
                        new Object[]{carrinhoEntity.getId()});


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
                return this.getCarrinho(carrinhoEntity.getIdCliente(),carrinhoEntity.getSessionToken(), false);
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

                if (!carrinhoEntity.getSessionToken().equals(sessionToken)){
                    LOG.error("m=removeItem, ERRO BIZARRO!!!, UM CLIENTE COM SESSION_TOKEN TENTOU REMOVER UM ITEM QUE NAO É DELE.");
                    throw new CarrinhoServiceException("Erro ao remover um item do carrinho.");
                }
                this.commonDao.remove(ItemCarrinhoEntity.class, idItem);
                return this.getCarrinho(carrinhoEntity.getIdCliente(),carrinhoEntity.getSessionToken(), false);
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
}
