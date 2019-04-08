package br.com.mpr.ws.service;

import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.PsNotificationVo;
import br.com.mpr.ws.vo.ResultadoPagamentoVo;


/**
 *
 */
public interface PagamentoService {

    ResultadoPagamentoVo pagamento(CheckoutForm form) throws PagamentoServiceException;

    String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws PagamentoServiceException;

}
