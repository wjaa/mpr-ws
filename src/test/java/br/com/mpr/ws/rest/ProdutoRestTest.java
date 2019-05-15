package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.PageVo;
import br.com.mpr.ws.vo.ProdutoVo;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Type;

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
    public void listAllPaged2in2() {
        try{
            int size = 2;
            int page = 1;

            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/all/" + page +"/" + size);
            Type pageType = new TypeToken<PageVo<ProdutoVo>>(){}.getType();
            PageVo<ProdutoVo> pageVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    pageType);

            validatePage(page,size ,3, pageVo);

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    @Test
    public void findProdutoByNameOrDesc() {
        try{
            int size = 6;
            int page = 1;

            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/find/retrato/" +
                    page +"/" + size + "/0");
            Type pageType = new TypeToken<PageVo<ProdutoVo>>(){}.getType();
            PageVo<ProdutoVo> pageVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    pageType);

            Assert.assertNotNull(pageVo);
            Assert.assertTrue(pageVo.getResult().size() > 0);
            for (ProdutoVo p : pageVo.getResult()){
                Assert.assertNotNull(p.getId());
                Assert.assertNotNull(p.getDescricao());
                Assert.assertNotNull(p.getPreco());

                //o produto 1 não tem itens no estoque.
                if (p.getId() == 1){
                    Assert.assertEquals(new Integer(0),p.getQuantidade());
                }

            }

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    private void validatePage(int actualPage, int size, int totalPage, PageVo<ProdutoVo> pageVo) {
        Assert.assertNotNull(pageVo);
        Assert.assertTrue(pageVo.getResult().size() == size);
        Assert.assertEquals(actualPage,pageVo.getPage());
        Assert.assertEquals(size,pageVo.getPageSize());
        Assert.assertEquals(totalPage,pageVo.getPageTotal());

        for (ProdutoVo p : pageVo.getResult()){
            Assert.assertNotNull(p.getId());
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());

            //o produto 1 não tem itens no estoque.
            if (p.getId() == 1){
                Assert.assertEquals(new Integer(0),p.getQuantidade());
            }

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
    public void getProdutoDestaque(){
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/produto/destaque");
            ProdutoVo produto = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ProdutoVo.class);
            Assert.assertNotNull(produto);
            this.validarInfoProduto(produto);
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
            validarInfoProduto(p);
        }
    }

    private void validarInfoProduto(ProdutoVo p) {
        Assert.assertNotNull(p.getDescricao());
        Assert.assertNotNull(p.getPreco());
        Assert.assertNotNull(p.getImgDestaque());
        Assert.assertNotNull(p.getDescricaoDetalhada());
        Assert.assertNotNull(p.getImagensDestaque());
        Assert.assertTrue(p.getImagensDestaque().size() > 0);
    }


}