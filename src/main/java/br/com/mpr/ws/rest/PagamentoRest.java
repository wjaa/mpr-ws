package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.service.PagamentoService;
import br.com.mpr.ws.vo.CheckoutForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wagner on 30/01/19.
 */
@RestController
@RequestMapping("/api/v1/core")
public class PagamentoRest extends BaseRest {

    @Autowired
    @Qualifier("PagamentoServicePagseguroImpl")
    private PagamentoService pagamentoService;


    @RequestMapping(value = "/pagamento",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public PedidoEntity pagamento(@RequestBody CheckoutForm checkoutForm) throws PagamentoServiceException {
        return pagamentoService.pagamento(checkoutForm);
    }

}
