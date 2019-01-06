package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.CheckoutCieloServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.CheckoutVo;


/**
 *
 */
public interface CheckoutService {


    PedidoEntity checkout(CheckoutForm form) throws CheckoutCieloServiceException;

    String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws CheckoutCieloServiceException;

    CheckoutVo detailCheckout();
}
