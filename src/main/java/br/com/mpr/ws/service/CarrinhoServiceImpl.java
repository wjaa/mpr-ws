package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CarrinhoEntity;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.ItemCarrinhoEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private CarrinhoService carrinhoService;


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
        //chave da thread em questao.
        String key = br.com.mpr.ws.utils.StringUtils.createRandomHash();
        CarrinhoThreadExecutor.putExecute(key,item);
        this.startThreadAddCarrinho();

        Date start = new Date();
        while (CarrinhoThreadExecutor.containsExecute(key)){
            try {
                LOG.debug("Aguardando para adicionar o carrinho [" + key + "]");
                this.startThreadAddCarrinho();
                Thread.sleep(100);

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
    public void startThreadAddCarrinho(){

        if (!CarrinhoThreadExecutor.isAlive()){

            CarrinhoThreadExecutor.setAlive(true);
            Iterator<String> it = CarrinhoThreadExecutor.getIterator();

            if (!CarrinhoThreadExecutor.isEmpty()){
                while (it.hasNext()){
                    String key = it.next();
                    try {
                        CarrinhoVo vo = carrinhoService.addCarrinho(CarrinhoThreadExecutor.getExecute(key),key);
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
     * @param threadName nome da Thread em execucao.
     * @return
     * @throws CarrinhoServiceException
     */
    @Override
    public CarrinhoVo addCarrinho(ItemCarrinhoForm item, String threadName) throws CarrinhoServiceException {
        try{
            LOG.debug("m=addCarrinho, item="+item + "threadName="+threadName);
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

            //TODO: GRAVAR IMAGEM NO ITEM DO CARRINHO.

            commonDao.save(itemCarrinhoEntity);

            return this.getCarrinho(item.getIdCliente(),item.getKeyDevice());


        }catch (ConstraintViolationException ex){
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", ex);
            throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
        }catch (DataIntegrityViolationException ex){
            LOG.error("m=addCarrinho, Erro ao adicionar um item no carrinho", ex);
            throw new CarrinhoServiceException("Infelizmente não temos mais esse produto em estoque.");
        }
    }

    private CarrinhoEntity findCarrinho(ItemCarrinhoForm item) {

        if ( item.getIdCarrinho() != null ){
            return commonDao.get(CarrinhoEntity.class,item.getIdCarrinho());
        }else if (item.getIdCliente() != null){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"idCliente"}, new Object[]{item.getIdCliente()});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }else if (!StringUtils.isEmpty(item.getKeyDevice())){
            List<CarrinhoEntity> listCarrinho = commonDao.findByProperties(CarrinhoEntity.class,
                    new String[]{"keyDevice"}, new Object[]{item.getKeyDevice()});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }

        return null;
    }

    @Override
    public CarrinhoVo getCarrinho(Long idCliente, String keyDevice) {
        return new CarrinhoVo();
    }

    @Override
    public CarrinhoVo removeItem(Long idItem) throws CarrinhoServiceException {
        return null;
    }
}
