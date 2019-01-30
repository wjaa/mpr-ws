package br.com.mpr.ws.rest;


import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.service.ClienteService;
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
public class ClienteRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(ClienteRest.class);


    @Autowired
    private ClienteService clienteService;


    @RequestMapping(value = "/cliente/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ClienteEntity saveCliente(@RequestBody @Valid ClienteEntity cliente) throws ClienteServiceException {
        return this.clienteService.saveCliente(cliente);
    }



    @RequestMapping(value = "/cliente/byId/{idCliente}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ClienteEntity getClienteById(@PathVariable Long idCliente){
        return this.clienteService.getClienteById(idCliente);
    }


    @RequestMapping(value = "/cliente/byKeyDevice/{keyDevice}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ClienteEntity getClienteByKeyDevice(@PathVariable String keyDevice){
        return this.clienteService.getClienteByKeyDevice(keyDevice);
    }


}
