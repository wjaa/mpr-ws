package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PagamentoServiceCieloException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.CheckoutVo;


/**
 *
 */
public interface PagamentoService {

    PedidoEntity pagamento(CheckoutForm form) throws PagamentoServiceCieloException;

    String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws PagamentoServiceCieloException;

}
