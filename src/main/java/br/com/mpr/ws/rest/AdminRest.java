package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.ProdutoEntity;
import br.com.mpr.ws.entity.TipoProdutoEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRest extends BaseRest{

    private static final Log LOG = LogFactory.getLog(AdminRest.class);


    @Autowired
    private AdminService adminService;


    /** FORNECEDOR */
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
    public FornecedorEntity saveFornecedor(@RequestBody @Valid FornecedorEntity fornecedorEntity) throws AdminServiceException {
        return this.adminService.saveFornecedor(fornecedorEntity);
    }

    /***************/


    /** TIPO DE PRODUTO */
    @RequestMapping(value = "/tipoProduto/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<TipoProdutoEntity> getAllTipoProduto(){
        return this.adminService.listAllTipoProduto();
    }

    @RequestMapping(value = "/tipoProduto/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public TipoProdutoEntity getTipoProdutoById(@PathVariable Long id){
        return this.adminService.getTipoProdutoById(id);
    }

    @RequestMapping(value = "/tipoProduto/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public TipoProdutoEntity saveTipProduto(@RequestBody @Valid TipoProdutoEntity tipoProduto) throws AdminServiceException {
        return this.adminService.saveTipoProduto(tipoProduto);
    }

    /***************/


    /** PRODUTO */
    @RequestMapping(value = "/produto/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoEntity> getAllProduto(){
        return this.adminService.listAllProduto();
    }

    @RequestMapping(value = "/tipoProduto/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ProdutoEntity getProdutoById(@PathVariable Long id){
        return this.adminService.getProdutoById(id);
    }

    @RequestMapping(value = "/tipoProduto/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ProdutoEntity saveProduto(@RequestBody @Valid ProdutoEntity produto) throws AdminServiceException {
        return this.adminService.saveProduto(produto);
    }

    /***************/


}
