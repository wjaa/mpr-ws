package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.service.AdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRest extends BaseRest{

    private static final Log LOG = LogFactory.getLog(AdminRest.class);


    @Autowired
    private AdminService adminService;



    @RequestMapping(value = "/{entity}/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<? extends Serializable> getAll(@PathVariable String entity) throws AdminServiceException {
        return this.adminService.listAllEntity(entity);
    }

    @RequestMapping(value = "/{entity}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public Serializable geEntityById(@PathVariable String entity, @PathVariable Long id) throws AdminServiceException {
        Serializable obj = this.adminService.getEntityById(entity, id);
        return obj == null ? "{}" : obj;
    }

    @RequestMapping(value = "/FornecedorEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public Serializable saveFornecedor(@RequestBody @Valid FornecedorEntity entity) throws AdminServiceException {
        return this.adminService.saveFornecedor(entity);
    }

    @RequestMapping(value = "/TipoProdutoEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveTipoProduto(@RequestBody @Valid TipoProdutoEntity entity) throws AdminServiceException {
        return this.adminService.saveTipoProduto(entity);
    }

    @RequestMapping(value = "/ProdutoEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveProduto(@RequestBody @Valid ProdutoEntity entity) throws AdminServiceException {
        return this.adminService.saveProduto(entity);
    }

    @RequestMapping(value = "/TabelaPrecoEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveTabelaPreco(@RequestBody @Valid TabelaPrecoEntity entity) throws AdminServiceException {
        return this.adminService.saveTabelaPreco(entity);
    }

    @RequestMapping(value = "/EstoqueEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveEstoque(@RequestBody @Valid EstoqueEntity entity) throws AdminServiceException {
        return this.adminService.saveEstoque(entity);
    }

    @RequestMapping(value = "/CupomEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveCupom(@RequestBody @Valid CupomEntity entity) throws AdminServiceException {
        return this.adminService.saveCupom(entity);
    }

    @RequestMapping(value = "/ClienteEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveCliente(@RequestBody @Valid ClienteEntity entity) throws AdminServiceException {
        return this.adminService.saveCliente(entity);
    }
}
