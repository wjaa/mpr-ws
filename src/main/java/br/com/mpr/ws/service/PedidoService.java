package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.CarrinhoEntity;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.vo.FormaPagamentoVo;

/**
 *
 */
public interface PedidoService {


    PedidoEntity createPedido(CarrinhoEntity carrinho, FormaPagamentoVo formaPagamento);

}
