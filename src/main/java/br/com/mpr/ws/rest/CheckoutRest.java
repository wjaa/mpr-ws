package br.com.mpr.ws.rest;


import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.service.CheckoutService;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.CheckoutVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/core")
public class CheckoutRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(CheckoutRest.class);


    @Autowired
    private CheckoutService checkoutService;


    @RequestMapping(value = "/checkout/{idCarrinho}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public CheckoutVo checkout(@PathVariable Long idCarrinho) throws CheckoutServiceException {
        return this.checkoutService.checkout(idCarrinho);
    }

    @RequestMapping(value = "/checkout/addCupom/{idCheckout}/{cupom}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CheckoutVo adicionarCupom(@PathVariable Long idCheckout,
                               @PathVariable String cupom) throws CheckoutServiceException {
        return this.checkoutService.adicionarCupom(idCheckout,cupom);
    }

    @RequestMapping(value = "/checkout/alterarEndereco/{idCheckout}/{idEndereco}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CheckoutVo alterarEndereco(@PathVariable Long idCheckout,
                                      @PathVariable Long idEndereco) throws CheckoutServiceException {
        return this.checkoutService.alterarEndereco(idCheckout, idEndereco);
    }

    @RequestMapping(value = "/checkout/alterarFrete/{idCheckout}/{freteType}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CheckoutVo alterarFrete(@PathVariable Long idCheckout,
                                      @PathVariable FreteType freteType) throws CheckoutServiceException {
        return this.checkoutService.alterarFrete(idCheckout, freteType);
    }

}
