package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.EmbalagemEntity;
import br.com.mpr.ws.entity.ProdutoEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wagner on 13/01/19.
 */
public class EmbalagemServiceImplTest extends BaseDBTest{

    @Autowired
    private EmbalagemService embalagemService;


    @Test
    public void getEmbalagem1() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setComp(25.0);
        produtoEntity.setLarg(20.0);
        produtoEntity.setAlt(1.0);
        produtos.add(produtoEntity);
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);
        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem1");
    }


    @Test
    public void getEmbalagem2() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(25.0);
            produtoEntity.setLarg(20.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem2");
    }


    @Test
    public void getEmbalagem3() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(25.0);
            produtoEntity.setLarg(20.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem3");
    }


    @Test
    public void getEmbalagem4() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setComp(10.0);
        produtoEntity.setLarg(5.0);
        produtoEntity.setAlt(1.0);
        produtos.add(produtoEntity);

        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem4");
    }


    @Test
    public void getEmbalagem5() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(10.0);
            produtoEntity.setLarg(5.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem5");
    }

    @Test
    public void getEmbalagem6() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(10.0);
            produtoEntity.setLarg(5.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem6");
    }

    @Test
    public void getEmbalagem7() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 1; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(20.0);
            produtoEntity.setLarg(15.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem7");
    }

    @Test
    public void getEmbalagem8() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(20.0);
            produtoEntity.setLarg(15.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem8");
    }

    @Test
    public void getEmbalagem9() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(20.0);
            produtoEntity.setLarg(15.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem9");
    }

    @Test
    public void getEmbalagem10() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 1; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(15.0);
            produtoEntity.setLarg(10.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem10");
    }

    @Test
    public void getEmbalagem11() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(15.0);
            produtoEntity.setLarg(10.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem11");
    }


    @Test
    public void getEmbalagem12() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(15.0);
            produtoEntity.setLarg(10.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem12");
    }

    @Test
    public void getEmbalagem3_1() throws Exception {
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setComp(25.0);
            produtoEntity.setLarg(10.0);
            produtoEntity.setAlt(1.0);
            produtos.add(produtoEntity);
        }
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtos);

        Assert.assertEquals(embalagemEntity.getDescricao(),"embalagem3");
    }
}