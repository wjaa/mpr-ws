package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CarrinhoEntity;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.ItemCarrinhoEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.ImagemServiceException;
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

        if (StringUtils.isEmpty(item.getKeyDevice()) && item.getIdCliente() == null){
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
            CarrinhoEntity carrinhoEntity = this.findCarrinho(item);

            if (carrinhoEntity == null){
                carrinhoEntity = new CarrinhoEntity();
                carrinhoEntity.setIdCliente(item.getIdCliente());
                carrinhoEntity.setKeyDevice(item.getKeyDevice());
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

            itemCarrinhoEntity.setIdEstoqueItem(estoqueItemEntity.getId());


            //CLIENTES PODEM ADICIONAR ACESSORIOS, SE NAO FOR UM ACESSORIO ENTÃO TEM FOTO.
            if ( !produtoService.isAcessorio(item.getIdProduto()) ){

                if ( (item.getFoto() == null || item.getFoto().length == 0) && item.getIdCatalogo() == null){
                    throw new CarrinhoServiceException("Para adicionar esse produto no carrinho, uma imagem é obrigatória!");
                }

                itemCarrinhoEntity.setFoto(imagemService.uploadFotoCliente(item.getFoto(),item.getNomeArquivo()));
                itemCarrinhoEntity.setIdCatalogo(item.getIdCatalogo());
            }


            commonDao.save(itemCarrinhoEntity);

            return this.getCarrinho(item.getIdCliente(),item.getKeyDevice());


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

    private CarrinhoEntity findCarrinho(ItemCarrinhoForm item) {

        if ( item.getIdCarrinho() != null ){
            return commonDao.get(CarrinhoEntity.class,item.getIdCarrinho());
        }else{

            return findCarrinho(item.getIdCliente(),item.getKeyDevice());

        }
    }

    private CarrinhoEntity findCarrinho(Long idCliente, String keyDevice) {

        if (idCliente != null && idCliente > 0){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"idCliente"}, new Object[]{idCliente});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }else if (!StringUtils.isEmpty(keyDevice)){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"keyDevice"}, new Object[]{keyDevice});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }

        return null;
    }

    @Override
    public CarrinhoVo getCarrinhoByIdCliente(Long idCliente){
        return getCarrinho(idCliente,null);
    }

    @Override
    public CarrinhoVo getCarrinhoByKeyDevice(String keyDevice){
        return getCarrinho(null, keyDevice);
    }

    private CarrinhoVo getCarrinho(Long idCliente, String keyDevice) {
        CarrinhoEntity carrinhoEntity = this.findCarrinho(idCliente,keyDevice);

        if (carrinhoEntity == null){
            CarrinhoVo vo = new CarrinhoVo();
            vo.setIdCliente(idCliente);
            vo.setKeyDevice(keyDevice);
            return vo;
        }

        return this.getCarrinhoVo(carrinhoEntity);
    }

    private CarrinhoVo getCarrinhoVo(CarrinhoEntity carrinhoEntity) {
        CarrinhoVo vo = CarrinhoVo.toVo(carrinhoEntity);
        List<ItemCarrinhoEntity> items =
                commonDao.findByProperties(
                        ItemCarrinhoEntity.class,
                        new String[]{"idCarrinho"},
                        new Object[]{carrinhoEntity.getId()});


        List<ItemCarrinhoVo> listVos = new ArrayList<>();

        for (ItemCarrinhoEntity i : items){
            ItemCarrinhoVo ivo = new ItemCarrinhoVo();
            BeanUtils.copyProperties(i, ivo);
            EstoqueItemEntity itemEstoque = this.commonDao.get(EstoqueItemEntity.class, i.getIdEstoqueItem());
            ivo.setProduto(this.produtoService.getProdutoById(itemEstoque.getIdProduto()));

            if (i.getIdCatalogo() != null){
                ivo.setUrlFoto(this.imagemService.getUrlFotoCatalogo(i.getFoto()));
            }else{
                ivo.setUrlFoto(this.imagemService.getUrlFotoCliente(i.getFoto()));
            }
            listVos.add(ivo);
        }

        vo.setItems(listVos);
        return vo;
    }

    @Override
    public CarrinhoVo removeItem(Long idItem) throws CarrinhoServiceException {
        try{
            ItemCarrinhoEntity item = this.commonDao.get(ItemCarrinhoEntity.class, idItem);
            if (item != null){
                this.commonDao.remove(ItemCarrinhoEntity.class, idItem);
                CarrinhoEntity carrinhoEntity = this.commonDao.get(CarrinhoEntity.class,item.getIdCarrinho());
                return this.getCarrinho(carrinhoEntity.getIdCliente(),carrinhoEntity.getKeyDevice());
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

        return this.getCarrinhoVo(carrinhoEntity);
    }
}
