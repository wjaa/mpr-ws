package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.EstoqueServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;

/**
 * Created by wagner on 11/02/19.
 */
public interface EstoqueService {

    void baixarEstoque(CarrinhoVo carrinho) throws EstoqueServiceException;

    void retornarEstoque(PedidoEntity pedido) throws EstoqueServiceException;

}
