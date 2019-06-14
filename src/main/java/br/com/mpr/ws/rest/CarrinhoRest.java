package br.com.mpr.ws.rest;


import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
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


    @RequestMapping(value = "/carrinho/add",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.PUT)
    public CarrinhoVo addCarrinho(@RequestBody @Valid ItemCarrinhoForm form,
                                  OAuth2Authentication principal) throws CarrinhoServiceException {
        ClienteEntity cliente = validateUser(principal);
        return this.carrinhoService.addCarrinho(form);
    }


    @RequestMapping(value = "/carrinho/cliente",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('USER')")
    public CarrinhoVo getCarrinhoClienteById(OAuth2Authentication principal){
        ClienteEntity cliente = validateUser(principal);

        return this.carrinhoService.getCarrinhoByIdCliente(cliente.getId());
    }


    @RequestMapping(value = "/carrinho/byKeyDevice/{keyDevice}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public CarrinhoVo getCarrinhoClienteByKeyDevice(@PathVariable String keyDevice){
        return this.carrinhoService.getCarrinhoByKeyDevice(keyDevice);
    }


    @RequestMapping(value = "/carrinho/removeItem/{idItem}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.DELETE)
    public CarrinhoVo removeItem(@PathVariable Long idItem) throws CarrinhoServiceException {
        return this.carrinhoService.removeItem(idItem);
    }


    @RequestMapping(value = "/carrinho/alterarQtdeItem/{idItem}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public CarrinhoVo alterarQtdeItem(@PathVariable Long idItem) throws CarrinhoServiceException {
        //TODO CONTINUAR AQUI...
        return null;
    }

    private ClienteEntity validateUser(OAuth2Authentication principal) {
        Authentication user = principal.getUserAuthentication();
        if (user == null){
            throw new AccessDeniedException("Usuário não autenticado");
        }
        return (ClienteEntity) user.getPrincipal();
    }

}
