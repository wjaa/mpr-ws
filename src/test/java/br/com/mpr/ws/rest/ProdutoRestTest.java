package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

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


    @Test
    public void findProdutoLancamentos(){
        int limite = 2;

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/lancamento/" + limite);
            ProdutoVo [] produtos = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ProdutoVo[].class);
            Assert.assertNotNull(produtos);
            Assert.assertEquals(limite, produtos.length);
            this.validarInfoProduto(produtos);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }



    }


    @Test
    public void findProdutoPopulares(){
        int limite = 4;
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/popular/" + limite);
            ProdutoVo [] produtos = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ProdutoVo[].class);
            Assert.assertNotNull(produtos);
            Assert.assertEquals(limite, produtos.length);
            this.validarInfoProduto(produtos);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void findProdutoMenorPreco(){
        int limite = 4;
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/menorPreco/" + limite);
            ProdutoVo [] produtos = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ProdutoVo[].class);
            Assert.assertNotNull(produtos);
            Assert.assertEquals(limite, produtos.length);
            this.validarInfoProduto(produtos);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    @Test
    public void findProdutoMaiorPreco(){
        int limite = 4;
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/maiorPreco/" + limite);
            ProdutoVo [] produtos = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ProdutoVo[].class);
            Assert.assertNotNull(produtos);
            Assert.assertEquals(limite, produtos.length);
            this.validarInfoProduto(produtos);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    private void validarInfoProduto(ProdutoVo [] produtos) {
        for (ProdutoVo p : produtos){
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());
            Assert.assertNotNull(p.getImgDestaque());
            Assert.assertNotNull(p.getDescricaoDetalhada());
            Assert.assertNotNull(p.getListImgDestaque());
            Assert.assertTrue(p.getListImgDestaque().size() > 0);
        }
    }


}