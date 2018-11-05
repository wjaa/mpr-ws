package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.ProdutoEntity;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wagner on 11/2/18.
 */
public class ProdutoServiceImplTest extends BaseDBTest {
    @Autowired
    private ProdutoService produtoService;

    @Test
    public void listAll() throws Exception {
        List<ProdutoVo> listProduto = produtoService.listAll();

        Assert.assertNotNull(listProduto);
        Assert.assertTrue(listProduto.size() > 0);
        Assert.assertEquals(3,listProduto.size());

        for (ProdutoVo p : listProduto){
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
    public void getProdutoById() throws Exception {
        ProdutoVo produto = produtoService.getProdutoById(1l);

        Assert.assertNotNull(produto);
        Assert.assertEquals(new Long(1l),produto.getId());
        Assert.assertNotNull(produto.getDescricao());
        Assert.assertNotNull(produto.getPreco());
        Assert.assertNotNull(produto.getDescricaoDetalhada());
        Assert.assertNotNull(produto.getImgPreview());
        Assert.assertNotNull(produto.getImgDestaque());
        Assert.assertNotNull(produto.getImgSemFoto());
        Assert.assertTrue(produto.getImgSemFoto().contains("static.meuportaretrato.com/images"));
        Assert.assertNotNull(produto.getListUrlFotoDestaque());
        Assert.assertTrue(produto.getListUrlFotoDestaque().size() > 0);
        for (String foto : produto.getListUrlFotoDestaque()){
            Assert.assertTrue(foto.contains("static.meuportaretrato.com/images/d"));
        }


    }

}