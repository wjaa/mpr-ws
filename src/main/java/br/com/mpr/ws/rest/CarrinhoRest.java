package br.com.mpr.ws.rest;


import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.rest.aop.SessionController;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import br.com.mpr.ws.vo.ProdutoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/core")
public class CarrinhoRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(CarrinhoRest.class);


    @Autowired
    private CarrinhoService carrinhoService;


    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/carrinho/add",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.PUT)
    public CarrinhoVo addCarrinho(OAuth2Authentication principal) throws CarrinhoServiceException {
        ClienteEntity cliente = validateUser(principal);

        return this.carrinhoService.addCarrinho(cliente.getId());
    }

    @RequestMapping(value = "/carrinho/add/{sessionToken}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.PUT)
    public CarrinhoVo addCarrinho(@PathVariable String sessionToken) throws CarrinhoServiceException {
        return this.carrinhoService.addCarrinho(sessionToken);
    }


    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/carrinho",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public CarrinhoVo getCarrinhoCliente(OAuth2Authentication principal){
        ClienteEntity cliente = validateUser(principal);
        return this.carrinhoService.getCarrinhoByIdCliente(cliente.getId());
    }


    @RequestMapping(value = "/carrinho/{sessionToken}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    @SessionController
    public CarrinhoVo getCarrinhoClienteBySessionToken(@PathVariable String sessionToken) throws CarrinhoServiceException {
        return this.carrinhoService.getCarrinhoBySessionToken(sessionToken);
    }


    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/carrinho/removeItem/{idItem}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.DELETE)
    public CarrinhoVo removeItem(@PathVariable Long idItem, OAuth2Authentication principal) throws CarrinhoServiceException {
        ClienteEntity cliente = validateUser(principal);
        return this.carrinhoService.removeItem(idItem, cliente.getId());
    }


    @RequestMapping(value = "/carrinho/removeItem/{idItem}/{sessionToken}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.DELETE)
    public CarrinhoVo removeItem(@PathVariable Long idItem, @PathVariable String sessionToken) throws CarrinhoServiceException {
        return this.carrinhoService.removeItem(idItem, sessionToken);
    }


    @RequestMapping(value = "/carrinho/alterarQtdeItem/{idItem}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CarrinhoVo alterarQtdeItem(@PathVariable Long idItem) throws CarrinhoServiceException {
        //TODO CONTINUAR AQUI...
        return null;
    }

    @RequestMapping(value = "/carrinho/alterarQtdeItem/{idItem}/{sessionToken",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CarrinhoVo alterarQtdeItem(@PathVariable Long idItem, @PathVariable String sessionToken) throws CarrinhoServiceException {
        //TODO CONTINUAR AQUI...
        return null;
    }


}
