package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.service.PagamentoService;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.ResultadoPagamentoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by wagner on 30/01/19.
 */
@RestController
@RequestMapping("/api/v1/core")
public class PagamentoRest extends BaseRest {

    @Autowired
    @Qualifier("PagamentoServicePagseguroImpl")
    private PagamentoService pagamentoService;



    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/pagamento",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ResultadoPagamentoVo pagamento(@RequestBody @Valid CheckoutForm checkoutForm,
                                          OAuth2Authentication user) throws PagamentoServiceException {
        ClienteEntity clienteEntity = validateUser(user);
        checkoutForm.setIdCliente(clienteEntity.getId());
        return pagamentoService.pagamento(checkoutForm);
    }

}
