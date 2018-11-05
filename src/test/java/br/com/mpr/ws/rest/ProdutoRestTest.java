package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.utils.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProdutoRestTest extends BaseDBTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProdutoService produtoService;

    @Test
    public void listAll() {

        try{
            ResultActions ra = mvc.perform(get("/api/v1/core/produto/all")
                    .with(httpBasic("user","password"))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(produtoService.listAll())));

            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    @Test
    public void getProdutoById() {

        Long id = 1l;
        try{
            ResultActions ra = mvc.perform(get("/api/v1/core/produto/" + id)
                    .with(httpBasic("user","password"))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(produtoService.getProdutoById(id))));


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


}