package br.com.mpr.ws.rest;


import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import br.com.mpr.ws.vo.ProdutoVo;
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
public class CarrinhoRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(CarrinhoRest.class);


    @Autowired
    private CarrinhoService carrinhoService;


    @RequestMapping(value = "/carrinho/add",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.PUT)
    public CarrinhoVo addCarrinho(@RequestBody @Valid ItemCarrinhoForm form) throws CarrinhoServiceException {
        return this.carrinhoService.addCarrinho(form);
    }


    @RequestMapping(value = "/carrinho/byIdCliente/{idCliente}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public CarrinhoVo getCarrinhoClienteById(@PathVariable Long idCliente){
        return this.carrinhoService.getCarrinhoByIdCliente(idCliente);
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

}
