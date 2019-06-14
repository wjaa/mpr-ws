package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.utils.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class AdminRestTest extends BaseMvcTest {


    @Autowired
    private AdminService adminService;




    @Test
    public void getAllFornecedor() {

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/admin/FornecedorEntity/all");
            ra.andExpect(content().json(ObjectUtils.toJson(adminService.listAllFornecedor())));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    @Test
    public void getFornecedorById() {

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/admin/FornecedorEntity/1");
            ra.andExpect(content().json(ObjectUtils.toJson(adminService.getFornecedorById(1l))));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void saveFornecedor() {

        FornecedorEntity fornecedorEntity = new FornecedorEntity();
        fornecedorEntity.setEndereco("Rua do espaco vida sudavel, 252 ");
        fornecedorEntity.setNome("VIDA SAUDAVEL");
        fornecedorEntity.setEmail("jose@vidasaudavel.com.br");
        fornecedorEntity.setCnpj("50800040000196");
        fornecedorEntity.setTelefonePrincipal("11 5555555");
        try{
            String content = ObjectUtils.toJson(fornecedorEntity);
            ResultActions ra = getMvcPostResultAction("/api/v1/admin/FornecedorEntity/save", content);
            //ra.andExpect(content().json(ObjectUtils.toJson(adminService.listAllFornecedor())));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void saveFornecedorJaCadastrado() {

        FornecedorEntity fornecedorEntity = new FornecedorEntity();
        fornecedorEntity.setEndereco("Rua do espaco vida sudavel, 252 ");
        fornecedorEntity.setNome("VIDA SAUDAVEL");
        fornecedorEntity.setEmail("jose@vidasaudavel.com.br");
        fornecedorEntity.setCnpj("11111111111111");
        fornecedorEntity.setTelefonePrincipal("11 5555555");
        try{
            String content = ObjectUtils.toJson(fornecedorEntity);
            ResultActions ra = getMvcPostErrorResultAction("/api/v1/admin/FornecedorEntity/save", content);
            //TODO validar o errorMessage
            //ErrorMessageVo error = new ErrorMessageVo(400, );

            //ra.andExpect(content().json(ObjectUtils.toJson(adminService.listAllFornecedor())));


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    protected AppUser getAppUser(){
        return getAppUserAdmin();
    }

}