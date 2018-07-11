package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.utils.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class AdminRestTest extends BaseDBTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private AdminService adminService;

    @Test
    public void getAllFornecedor() {
        try{
            ResultActions ra = mvc.perform(get("/api/v1/admin/fornecedor/all")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(adminService.listAllFornecedor())));


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }



    @Test
    public void getFornecedorById() {

        try{
            ResultActions ra = mvc.perform(get("/api/v1/admin/fornecedor/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(adminService.getFornecedorById(1l))));


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void saveFornecedor() {
    }

    @Test
    public void removeFornecedor() {
    }
}