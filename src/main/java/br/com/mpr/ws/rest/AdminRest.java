package br.com.mpr.ws.rest;

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
    public List<? extends Serializable> getAllFornecedor(@PathVariable String entity) throws AdminServiceException {
        return this.adminService.listAllEntity(entity);
    }

    @RequestMapping(value = "/{entity}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public Serializable getFornecedorById(@PathVariable String entity, @PathVariable Long id) throws AdminServiceException {
        return this.adminService.getEntityById(entity, id);
    }

    @RequestMapping(value = "/{entity}/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveEntity(@PathVariable String entity,
                                       @RequestBody @Valid String jsonEntity) throws AdminServiceException {
        return this.adminService.saveEntity(entity, jsonEntity);
    }

}
