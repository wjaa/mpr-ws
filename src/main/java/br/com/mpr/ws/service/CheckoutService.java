package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.vo.CheckoutVo;

/**
 * Created by wagner on 11/01/19.
 */
public interface CheckoutService {

    /**
     * @param idCarrinho
     * @return
     */
    CheckoutVo checkout(Long idCarrinho) throws CheckoutServiceException;

    CheckoutVo alterarEndereco(Long idCheckout, Long idEndereco) throws CheckoutServiceException;

    CheckoutVo adicionarCupom(Long idCheckout, String codigoCupom) throws CheckoutServiceException;

    CheckoutVo alterarFrete(Long idCheckout, FreteType freteType) throws CheckoutServiceException;

    CheckoutVo getCheckout(Long idCheckout);

    String getCheckoutToken() throws CheckoutServiceException;
}
