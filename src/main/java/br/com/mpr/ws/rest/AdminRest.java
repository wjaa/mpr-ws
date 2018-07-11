package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRest extends BaseRest{

    private static final Log LOG = LogFactory.getLog(AdminRest.class);


    @Autowired
    private AdminService adminService;


    @RequestMapping(value = "/fornecedor/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<FornecedorEntity> getAllFornecedor(){
        return this.adminService.listAllFornecedor();
    }

    @RequestMapping(value = "/fornecedor/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public FornecedorEntity getFornecedorById(@PathVariable Long id){
        return this.adminService.getFornecedorById(id);
    }

    @RequestMapping(value = "/fornecedor/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public FornecedorEntity saveFornecedor(@RequestBody FornecedorEntity fornecedorEntity) throws AdminServiceException {
        return this.adminService.saveFornecedor(fornecedorEntity);
    }

    @RequestMapping(value = "/fornecedor/delete",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.DELETE)
    public FornecedorEntity removeFornecedor(@RequestBody FornecedorEntity fornecedorEntity) throws AdminServiceException {
        return this.adminService.saveFornecedor(fornecedorEntity);
    }



    @ExceptionHandler(AdminServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorMessageVo handleException(AdminServiceException e, HttpServletResponse response) {
        LOG.error("handleException, error=" + e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        return new ErrorMessageVo(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
    }

}
