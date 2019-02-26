package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.ItemCarrinhoEntity;
import br.com.mpr.ws.entity.ItemPedidoEntity;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.EstoqueServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wagner on 11/02/19.
 */
@Service
public class EstoqueServiceImpl implements EstoqueService {

    private static final Log LOG = LogFactory.getLog(EstoqueServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public void baixarEstoque(CarrinhoVo carrinho) throws EstoqueServiceException {
        try{
            for (ItemCarrinhoVo item : carrinho.getItems()){
                commonDao.remove(EstoqueItemEntity.class, item.getIdEstoqueItem());
            }
        }catch (Exception ex){
            LOG.error("Erro ao dar baixa no estoque", ex);
            throw new EstoqueServiceException("Erro ao dar baixa no estoque");
        }

    }

    @Override
    public void retornarEstoque(PedidoEntity pedido) throws EstoqueServiceException {
        try{
            for (ItemPedidoEntity itemPedido : pedido.getItens() ){
                EstoqueItemEntity estoqueItem = new EstoqueItemEntity();
                estoqueItem.setIdEstoque(itemPedido.getIdEstoque());
                estoqueItem.setIdProduto(itemPedido.getIdProduto());
                estoqueItem.setInvalido(false);
                commonDao.save(estoqueItem);
            }
        }catch (Exception ex){
            LOG.error("Erro ao retornar o estoque", ex);
            throw new EstoqueServiceException("Erro ao retornar o estoque");
        }
    }
}
