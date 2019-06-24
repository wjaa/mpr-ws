package br.com.mpr.ws.rest;


import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.rest.aop.SessionController;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.service.CheckoutService;
import br.com.mpr.ws.vo.CheckoutVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/core")
public class CheckoutRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(CheckoutRest.class);


    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CarrinhoService carrinhoService;

    /**
     *
     * @param user
     * @return
     * @throws CheckoutServiceException
     */
    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/checkout",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public CheckoutVo checkout(OAuth2Authentication user) throws CheckoutServiceException {
        ClienteEntity cliente = validateUser(user);
        return this.checkoutService.checkout(cliente.getId());
    }


    /**
     * ESSE ENDPOINT É UTILIZADO PARA CLIENTES QUE INICIARAM UM CARRINHO SEM ESTAR LOGADO NA APLICACAO.
     * QUANDO É SOLICITADO O CHECKOUT, PRECISAMOS MIGRAR O CARRINHO QUE ESTÁ NA SESSION PARA O CARRINHO DO CLIENTE
     * LOGADO.
     * @param sessionToken
     * @param user
     * @return
     * @throws CheckoutServiceException
     */
    @RequestMapping(value = "/checkout/{sessionToken}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    @SessionController
    public CheckoutVo checkout(@PathVariable String sessionToken, OAuth2Authentication user)
            throws CheckoutServiceException, CarrinhoServiceException {
        ClienteEntity cliente = validateUser(user);
        carrinhoService.moveCarSessionParaCarLogado(sessionToken,cliente.getId());
        return this.checkoutService.checkout(cliente.getId());
    }

    @RequestMapping(value = "/checkout/addCupom/{cupom}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CheckoutVo adicionarCupom(@PathVariable String cupom, OAuth2Authentication user) throws CheckoutServiceException {
        ClienteEntity cliente = validateUser(user);
        return this.checkoutService.adicionarCupom(cliente.getId(),cupom);
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

    @RequestMapping(value = "/checkout/token",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public String checkoutToken() throws CheckoutServiceException {
        return this.checkoutService.getCheckoutToken();
    }

}
