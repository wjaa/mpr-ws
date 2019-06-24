package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.vo.CheckoutVo;

/**
 * Created by wagner on 11/01/19.
 */
public interface CheckoutService {

    /**
     * @param idCliente
     * @return
     */
    CheckoutVo checkout(Long idCliente) throws CheckoutServiceException;

    CheckoutVo alterarEndereco(Long idCliente, Long idEndereco) throws CheckoutServiceException;

    CheckoutVo adicionarCupom(Long idCliente, String codigoCupom) throws CheckoutServiceException;

    CheckoutVo alterarFrete(Long idCliente, FreteType freteType) throws CheckoutServiceException;

    CheckoutVo getCheckout(Long idCheckout);

    CheckoutVo getCheckoutByIdCliente(Long idCliente);

    String getCheckoutToken() throws CheckoutServiceException;
}
