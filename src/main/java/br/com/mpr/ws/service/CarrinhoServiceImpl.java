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

    @Override
    public CarrinhoVo addCarrinho(ItemCarrinhoForm item) throws CarrinhoServiceException {
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

    public CarrinhoVo addCarrinho(ItemCarrinhoForm item, String threadName) throws CarrinhoServiceException {
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
                //TODO DISPARAR UM EMAIL AQUI, PORQUE ISSO NAO PODE ACONTECER.
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
