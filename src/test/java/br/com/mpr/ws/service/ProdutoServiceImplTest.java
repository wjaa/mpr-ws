package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        Assert.assertEquals(6,listProduto.size());

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
        Assert.assertNotNull(produto.getQuantidade());
        Assert.assertNotNull(produto.getHexaCor());
        Assert.assertNotNull(produto.getNomeCor());
        Assert.assertNotNull(produto.getProdutosRelacionados());
        Assert.assertTrue(produto.getProdutosRelacionados().size() > 0);
        Assert.assertTrue(produto.getImgSemFoto().contains("stc.meuportaretrato.com/images"));
        Assert.assertNotNull(produto.getImagensDestaque());
        Assert.assertTrue(produto.getImagensDestaque().size() > 0);
        for (String foto : produto.getImagensDestaque()){
            Assert.assertTrue(foto.contains("stc.meuportaretrato.com/images/d"));
        }
    }


    @Test
    public void getProdutoEmEstoque(){
        EstoqueItemEntity item = produtoService.getProdutoEmEstoque(4l);
        Assert.assertNotNull(item);
        Assert.assertEquals(new Long(4l),item.getIdProduto());
    }


    @Test
    public void findProdutoLancamentos(){
        int limite = 2;
        List<ProdutoVo> produtos = produtoService.listLancamentos(limite);
        Assert.assertNotNull(produtos);
        Assert.assertEquals(limite, produtos.size());
        this.validarInfoProduto(produtos);

    }


    @Test
    public void findProdutoPopulares(){
        int limite = 4;
        List<ProdutoVo> produtos = produtoService.listPopulares(limite);
        Assert.assertNotNull(produtos);
        Assert.assertEquals(limite, produtos.size());
        this.validarInfoProduto(produtos);

    }

    @Test
    public void findProdutoMenorPreco(){
        int limite = 4;
        List<ProdutoVo> produtos = produtoService.listProdutos(ProdutoService.OrderBy.MENOR_PRECO,limite);
        Assert.assertNotNull(produtos);
        Assert.assertEquals(limite, produtos.size());
        this.validarInfoProduto(produtos);
    }

    @Test
    public void findProdutoMaiorPreco(){
        int limite = 4;
        List<ProdutoVo> produtos = produtoService.listProdutos(ProdutoService.OrderBy.MAIOR_PRECO,limite);
        Assert.assertNotNull(produtos);
        Assert.assertEquals(limite, produtos.size());
        this.validarInfoProduto(produtos);

    }

    private void validarInfoProduto(List<ProdutoVo> produtos) {
        for (ProdutoVo p : produtos){
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());
            Assert.assertNotNull(p.getImgDestaque());
            Assert.assertNotNull(p.getDescricaoDetalhada());
            Assert.assertNotNull(p.getImagensDestaque());
            Assert.assertTrue(p.getImagensDestaque().size() > 0);
        }
    }

}