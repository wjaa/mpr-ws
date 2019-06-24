package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.helper.ProdutoHelper;
import br.com.mpr.ws.vo.PageVo;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by wagner on 11/2/18.
 */
public class ProdutoServiceImplTest extends BaseDBTest {
    @Autowired
    private ProdutoService produtoService;

    @Test
    public void listAll() throws Exception {
        List<ProdutoVo> listProduto = produtoService.listAll(10);

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


    /**
     *
     * TESTES DE PAGINACAO FOI ESCRITO CONTANDO QUE TEM 6 PRODUTOS EM ESTOQUE
     * ESSES TESTES PRECISAM SER MELHORADOS PARA VALIDAR A PAGINACAO SEM ESPERAR
     * UMA QUANTIDADE ESPECIFICA DE PRODUTOS.
     *
     */

    @Test
    public void listAllPaged2In2(){

        int size = 2;
        PageVo<ProdutoVo> page = produtoService.listAllPaged(PageRequest.of(0,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(1,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(3,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
            Assert.assertNotNull(p.getId());
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());

            //o produto 1 não tem itens no estoque.
            if (p.getId() == 1){
                Assert.assertEquals(new Integer(0),p.getQuantidade());
            }

        }

        page = produtoService.listAllPaged(PageRequest.of(1,2));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(2,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(3,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
            Assert.assertNotNull(p.getId());
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());

            //o produto 1 não tem itens no estoque.
            if (p.getId() == 1){
                Assert.assertEquals(new Integer(0),p.getQuantidade());
            }

        }

        page = produtoService.listAllPaged(PageRequest.of(2,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(3,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(3,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
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
    public void listAllPaged3In3(){
        int size = 3;
        PageVo<ProdutoVo> page = produtoService.listAllPaged(PageRequest.of(0,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(1,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(2,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
            Assert.assertNotNull(p.getId());
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());

            //o produto 1 não tem itens no estoque.
            if (p.getId() == 1){
                Assert.assertEquals(new Integer(0),p.getQuantidade());
            }

        }

        page = produtoService.listAllPaged(PageRequest.of(1,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(2,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(2,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
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
    public void listAllPaged4In4(){
        int size = 4;
        PageVo<ProdutoVo> page = produtoService.listAllPaged(PageRequest.of(0,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(1,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(2,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
            Assert.assertNotNull(p.getId());
            Assert.assertNotNull(p.getDescricao());
            Assert.assertNotNull(p.getPreco());

            //o produto 1 não tem itens no estoque.
            if (p.getId() == 1){
                Assert.assertEquals(new Integer(0),p.getQuantidade());
            }

        }

        page = produtoService.listAllPaged(PageRequest.of(1,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == 2);
        Assert.assertEquals(2,page.getPage());
        Assert.assertEquals(2,page.getPageSize());
        Assert.assertEquals(2,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
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
    public void listAllPaged6In6(){
        int size = 6;
        PageVo<ProdutoVo> page = produtoService.listAllPaged(PageRequest.of(0,size));

        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() == size);
        Assert.assertEquals(1,page.getPage());
        Assert.assertEquals(size,page.getPageSize());
        Assert.assertEquals(1,page.getPageTotal());

        for (ProdutoVo p : page.getResult()){
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
    public void findProdutoByNameOrDesc(){
        int size = 2;
        PageVo<ProdutoVo> page = produtoService.findProdutoByNameOrDesc("retrato",PageRequest.of(0,size),
                ProdutoService.OrderBy.MAIOR_PRECO);
        Assert.assertNotNull(page);
        Assert.assertTrue(page.getResult().size() > 0);
        for (ProdutoVo p : page.getResult()){
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
        Assert.assertNotNull(produto.getImgPreviewCliente());
        Assert.assertNotNull(produto.getQuantidade());
        Assert.assertNotNull(produto.getHexaCor());
        Assert.assertNotNull(produto.getNomeCor());
        Assert.assertNotNull(produto.getListVariacaoCorProduto());
        Assert.assertTrue(produto.getListVariacaoCorProduto().size() > 0);
        Assert.assertTrue(produto.getImgPreviewCliente().contains("stc.meuportaretrato.com/images"));
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
    public void getProdutoDestaque(){
        ProdutoVo produtoVo = produtoService.getProdutoDestaque();
        Assert.assertNotNull(produtoVo);
        this.validarInfoProduto(produtoVo);

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

    @Test
    public void getImagemPreviewProdutoById() {
        String imagemPreview = produtoService.getImagemPreviewProdutoById(5l);
        Assert.assertNotNull(imagemPreview);
        Assert.assertFalse(StringUtils.isEmpty(imagemPreview));
    }

    private void validarInfoProduto(List<ProdutoVo> produtos) {
        for (ProdutoVo p : produtos){
            validarInfoProduto(p);
        }
    }

    private void validarInfoProduto(ProdutoVo p) {
        ProdutoHelper.validarInfoProduto(p);
    }

}