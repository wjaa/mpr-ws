package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.utils.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ProdutoRestTest extends BaseMvcTest {




    @Autowired
    private ProdutoService produtoService;

    @Test
    public void listAll() {
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/all");
            ra.andExpect(content().json(ObjectUtils.toJson(produtoService.listAll())));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    @Test
    public void getProdutoById() {

        Long id = 1l;
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/" + id);
            ra.andExpect(content().json(ObjectUtils.toJson(produtoService.getProdutoById(id))));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


}