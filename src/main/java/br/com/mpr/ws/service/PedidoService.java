package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PedidoServiceException;
import br.com.mpr.ws.vo.CheckoutForm;

/**
 *
 */
public interface PedidoService {


    PedidoEntity createPedido(String code, CheckoutForm checkoutForm) throws PedidoServiceException;

}
