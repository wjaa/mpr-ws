package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CarrinhoEntity;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.ItemCarrinhoEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ProdutoService produtoService;


    @Override
    public CarrinhoVo addCarrinho(ItemCarrinhoForm item) throws CarrinhoServiceException {
        //tentando procurar um carrinho do cliente.
        CarrinhoEntity carrinhoEntity = getCarrinho(item);

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
        itemCarrinhoEntity.setIdEstoqueItem(estoqueItemEntity.getIdEstoque());
        itemCarrinhoEntity = commonDao.save(itemCarrinhoEntity);


        return null;
    }

    private CarrinhoEntity getCarrinho(ItemCarrinhoForm item) {

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
                    new String[]{"deviceKey"}, new Object[]{item.getKeyDevice()});

            if (listCarrinho.size() > 0){
                return listCarrinho.get(0);
            }
        }

        return null;
    }

    @Override
    public CarrinhoVo getCarrinho(Long idCliente, String keyDevice) {
        return null;
    }

    @Override
    public CarrinhoVo removeItem(Long idItem) throws CarrinhoServiceException {
        return null;
    }
}
