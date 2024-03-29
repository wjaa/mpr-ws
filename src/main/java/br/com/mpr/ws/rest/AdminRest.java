package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.vo.PedidoFindForm;
import br.com.mpr.ws.vo.ProdutoFindForm;
import br.com.mpr.ws.vo.SysCodeVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRest extends BaseRest{

    private static final Log LOG = LogFactory.getLog(AdminRest.class);


    @Autowired
    private AdminService adminService;


    //@PostAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{entity}/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<? extends Serializable> getAll(@PathVariable String entity, Principal principal) throws AdminServiceException {
        for (GrantedAuthority u : ((OAuth2Authentication)principal).getAuthorities() ){
            System.out.println(u.getAuthority());
        }
        return this.adminService.listAllEntity(entity);
    }

    @RequestMapping(value = "/EstoqueEntity/byIdProduto/{idProduto}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<? extends Serializable> listEstoqueByIdProduto(@PathVariable Long idProduto) throws AdminServiceException {
        return this.adminService.listEstoqueByIdProduto(idProduto);
    }

    @RequestMapping(value = "/{entity}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public Serializable getEntityById(@PathVariable String entity, @PathVariable Long id) throws AdminServiceException {
        Serializable obj = this.adminService.getEntityById(entity, id);
        return obj == null ? "{}" : obj;
    }


    @RequestMapping(value = "/{entity}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.DELETE)
    public void removeEntityById(@PathVariable String entity, @PathVariable Long id) throws AdminServiceException {
        this.adminService.removeEntityById(entity, id);
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

    @RequestMapping(value = "/CatalogoGrupoEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveCatalogo(@RequestBody @Valid CatalogoGrupoEntity entity) throws AdminServiceException {
        return this.adminService.saveCatalogoGrupo(entity);
    }

    @RequestMapping(value = "/CatalogoEntity/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Serializable saveImagemExclusiva(@RequestBody @Valid CatalogoEntity entity) throws AdminServiceException {
        return this.adminService.saveCatalogo(entity);
    }

    @RequestMapping(value = "/PedidoEntity/find",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Collection<PedidoEntity> findPedido(@RequestBody PedidoFindForm pedidoFindForm){
        return this.adminService.findPedido(pedidoFindForm);
    }

    @RequestMapping(value = "/ProdutoEntity/find",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public Collection<ProdutoEntity> findProduto(@RequestBody ProdutoFindForm produtoFindForm){
        return this.adminService.findProduto(produtoFindForm);
    }

    @RequestMapping(value = "/SysCode/list",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public Collection<SysCodeVo> listAllSysCode(){
        Collection<SysCodeVo> list = new ArrayList<>();
        for ( SysCodeType s : SysCodeType.values() ){
            list.add(new SysCodeVo(s.toString(), s.getDesc()));
        }
        return list;
    }

}
