package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;


/**
 *
 */
public interface PagamentoService {

    PedidoEntity pagamento(CheckoutForm form) throws PagamentoServiceException;

    String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws PagamentoServiceException;

}
