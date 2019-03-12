package br.com.mpr.ws.rest;


import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.service.PedidoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/core")
public class PedidoRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(PedidoRest.class);


    @Autowired
    private PedidoService pedidoService;


    @RequestMapping(value = "/pedido/findById/{idPedido}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public PedidoEntity findById(@PathVariable Long idPedido){
        return this.pedidoService.getPedido(idPedido);
    }

    @RequestMapping(value = "/pedido/findByIdCliente/{idCliente}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<PedidoEntity> findByIdCliente(@PathVariable Long idCliente){
        return this.pedidoService.findPedidoByIdCliente(idCliente);
    }

    @RequestMapping(value = "/pedido/findByCodigo/{codigo}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public PedidoEntity findByCodigo(@PathVariable String codigo){
        return this.pedidoService.findPedidoByCodigo(codigo);
    }

}
