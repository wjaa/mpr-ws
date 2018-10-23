package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.dao.CommonDaoImplTest;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ProdutoEstoqueVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 6/25/18.
 */
public class AdminServiceImplTest extends BaseDBTest {

    private static final Log LOG = LogFactory.getLog(CommonDaoImplTest.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private CommonDao commonDao;


    /**
     * TESTES NO CADASTRO DE FORNECEDOR.
     */

    @Test
    public void getFornecedorById() {
        FornecedorEntity fornecedorEntity = adminService.getFornecedorById(1l);
        System.out.println(ObjectUtils.toJson(fornecedorEntity));
        Assert.assertNotNull(fornecedorEntity);
    }

    @Test
    public void listAllFornecedor() {
        List<FornecedorEntity> listAll = adminService.listAllFornecedor();
        Assert.assertNotNull(listAll);
        Assert.assertTrue(listAll.size() > 1);
    }


    @Test
    public void criarFornecedor() {
        FornecedorEntity fe = new FornecedorEntity();
        fe.setNome("Wagner Jeronimo");
        fe.setCnpj("11111111111");
        fe.setEndereco("Rua balbalba, 333, vila ahuahau, guarulhos sp");
        fe.setEmail("wag182@gmail.com");
        fe.setTelefonePrincipal("11983777633");
        fe.setTelefoneSecundario("11983777633");
        try {
            System.out.println(ObjectUtils.toJson(fe));
            fe = adminService.saveFornecedor(fe);
            Assert.assertNotNull(fe.getId());
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void atualizarFornecedor() {
        FornecedorEntity fe = commonDao.get(FornecedorEntity.class, 1l);
        fe.setNome("Claudio PR");
        try {
            fe = adminService.saveFornecedor(fe);
            Assert.assertTrue(fe.getId().equals(1l));
            Assert.assertNotNull(fe.getNome().equals("Claudio PR"));
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }


    @Test
    public void removerFornecedor() {
        try {
            adminService.removeFornecedorById(2);
            FornecedorEntity fornecedorRemovido = adminService.getFornecedorById(2l);
            Assert.assertFalse(fornecedorRemovido.getAtivo());
        } catch (AdminServiceException ex) {
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    /*REGRA1*/
    @Test
    public void criarFornecedorMesmoCnpjException() {
        FornecedorEntity fornecedorMesmoCnpj = adminService.getFornecedorById(1l);
        fornecedorMesmoCnpj.setId(null);
        fornecedorMesmoCnpj.setNome("FORNECEDOR MESMO CNPJ");
        try {
            adminService.saveFornecedor(fornecedorMesmoCnpj);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("ESSE CNPJ"));
        }
    }



    /**
     * TESTES NO CADASTRO DE PRECO.
     */
    @Test
    public void criarTabelaPreco() {
        TabelaPrecoEntity preco = new TabelaPrecoEntity();

        preco.setDataVigencia(DateUtils.addDays(new Date(), 2));
        preco.setIdProduto(1l);
        preco.setPreco(50.50);
        preco.setDescricao("valor inicial");
        try {
            preco = adminService.saveTabelaPreco(preco);
            Assert.assertNotNull(preco.getId());
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }

    }

    @Test
    public void atualizarTabelaPreco() {
        TabelaPrecoEntity tabPreco = adminService.getTabelaPrecoById(1l);
        tabPreco.setId(null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 30);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
        tabPreco.setDataVigencia(c.getTime());
        tabPreco.setPreco(555.40);
        try {
            tabPreco = adminService.saveTabelaPreco(tabPreco);
            Assert.assertNotNull(tabPreco.getId());
            Assert.assertTrue(tabPreco.getPreco().equals(555.40));

        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }

    }


    @Test
    public void removerTabelaPreco() {
        try {
            TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class, 1l);
            tabPreco.setId(null);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.MONTH, Calendar.JANUARY);
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
            tabPreco.setDataVigencia(c.getTime());
            tabPreco.setPreco(50.40);
            tabPreco = adminService.saveTabelaPreco(tabPreco);
            Assert.assertNotNull(tabPreco.getId());
            adminService.removeTabelaPrecoById(tabPreco.getId());
            Assert.assertTrue(true);

        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }

    }

    /*REGRA1: NAO PODE REMOVER UMA TABELA DE PRECO COM DATA <= DATA_HOJE*/
    @Test
    public void removerTabelaPrecoDataRetroativaException() {
        try {
            adminService.removeTabelaPrecoById(2);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("RETROATIVA"));
        }

    }

    /*REGRA2: NAO PODE ADICIONAR UMA TABELA DE PRECO COM MESMA DATA JÁ EXISTENTE */
    @Test
    public void criarTabelaPrecoMesmaDataException() {
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class, 1l);
        tabPreco.setId(null);
        tabPreco.setPreco(50.40);
        try {
            tabPreco = adminService.saveTabelaPreco(tabPreco);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            System.out.println(e.getMessage().toUpperCase());
            Assert.assertTrue(e.getMessage().toUpperCase().contains("VOCÊ NÃO PODE"));
        }
    }

    /*REGRA3: NAO PODE ADICIONAR UMA TABELA DE PRECO COM DATA <= DATA_HOJE */
    @Test
    public void criarTabelaPrecoDataRetroativaException() {
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class, 1l);
        tabPreco.setId(null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 27);
        c.set(Calendar.MONTH, Calendar.JUNE);
        c.set(Calendar.YEAR, 2018);
        tabPreco.setDataVigencia(c.getTime());
        tabPreco.setPreco(50.40);
        try {
            adminService.saveTabelaPreco(tabPreco);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("RETROATIVA"));
        }
    }

    /*REGRA4: NAO PODE ALTERAR UMA TABELA DE PRECO COM DATA <= DATA_HOJE */
    @Test
    public void alterarTabelaPrecoDataRetroativaException() {
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class, 1l);
        tabPreco.setDescricao("alterar");
        tabPreco.setPreco(50.40);
        try {
            tabPreco = adminService.saveTabelaPreco(tabPreco);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("RETROATIVA"));
        }
    }

    @Test
    public void criarEstoque(){

        EstoqueEntity estoqueEntity = new EstoqueEntity();
        estoqueEntity.setIdFornecedor(1l);
        estoqueEntity.setIdProduto(1l);
        estoqueEntity.setQuantidade(6);
        estoqueEntity.setObservacao("teste");
        estoqueEntity.setDataCompra(new Date());
        estoqueEntity.setPrecoCompra(34.4d);
        try {
            EstoqueEntity e = adminService.saveEstoque(estoqueEntity);
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getId());

            EstoqueEntity ee = adminService.getEstoqueById(e.getId());
            Assert.assertNotNull(ee);
            Assert.assertNotNull(ee.getProdutos());
            Assert.assertEquals(6,ee.getProdutos().size());

        } catch (AdminServiceException e) {
            LOG.error("Error:", e);
            Assert.assertTrue(e.getMessage(), false);
        }

    }

    @Test
    public void getEstoqueById(){
        EstoqueEntity estoqueEntity = adminService.getEstoqueById(1l);
        Assert.assertNotNull(estoqueEntity);
        Assert.assertNotNull(estoqueEntity.getProdutos());
        Assert.assertEquals("obs",estoqueEntity.getObservacao() );
        Assert.assertTrue(estoqueEntity.getPrecoCompra() == 10);
        Assert.assertEquals(5, estoqueEntity.getProdutos().size());
        Assert.assertNotNull(estoqueEntity.getFornecedor());
    }

    @Test
    public void atualizarEstoque(){

        EstoqueEntity estoqueEntity = new EstoqueEntity();
        estoqueEntity.setIdFornecedor(1l);
        estoqueEntity.setIdProduto(1l);
        estoqueEntity.setQuantidade(6);
        estoqueEntity.setObservacao("teste");
        estoqueEntity.setDataCompra(new Date());
        estoqueEntity.setPrecoCompra(34.4d);
        try {
            EstoqueEntity e = adminService.saveEstoque(estoqueEntity);
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getId());
            Assert.assertNotNull(e.getProdutos());
            Assert.assertEquals(6,e.getProdutos().size());

            Long id = e.getId();
            e.setIdProduto(2l);
            e.setObservacao("teste123456");
            e.setPrecoCompra(35d);
            e = adminService.saveEstoque(e);

            Assert.assertNotNull(e);
            Assert.assertEquals(id,e.getId());
            Assert.assertEquals(new Long(2l), e.getIdProduto());
            Assert.assertEquals("teste123456",e.getObservacao());
            Assert.assertEquals(new Double(35d),e.getPrecoCompra());



        } catch (AdminServiceException e) {
            LOG.error("Error:", e);
            Assert.assertTrue(e.getMessage(), false);
        }

    }


    @Test
    public void atualizarEstoqueMudarProduto(){

        EstoqueEntity estoqueEntity = new EstoqueEntity();
        estoqueEntity.setIdFornecedor(1l);
        estoqueEntity.setIdProduto(1l);
        estoqueEntity.setQuantidade(6);
        estoqueEntity.setObservacao("teste");
        estoqueEntity.setDataCompra(new Date());
        estoqueEntity.setPrecoCompra(34.4d);
        try {
            EstoqueEntity e = adminService.saveEstoque(estoqueEntity);
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getId());
            Assert.assertNotNull(e.getProdutos());
            Assert.assertEquals(6,e.getProdutos().size());

            for (EstoqueItemEntity item : e.getProdutos()){
                Assert.assertEquals(item.getIdProduto(),new Long(1l));
            }

            Long id = e.getId();
            e.setIdProduto(2l);
            e.setObservacao("teste123456");
            e.setPrecoCompra(35d);
            e = adminService.saveEstoque(e);

            Assert.assertNotNull(e);
            Assert.assertEquals(id,e.getId());
            Assert.assertEquals(new Long(2l), e.getIdProduto());
            Assert.assertEquals("teste123456",e.getObservacao());
            Assert.assertEquals(new Double(35d),e.getPrecoCompra());

            for (EstoqueItemEntity item : e.getProdutos()){
                Assert.assertEquals(new Long(2l),item.getIdProduto());
            }



        } catch (AdminServiceException e) {
            LOG.error("Error:", e);
            Assert.assertTrue(e.getMessage(), false);
        }

    }


    @Test
    public void atualizarEstoqueAumentarItens(){

        EstoqueEntity estoqueEntity = new EstoqueEntity();
        estoqueEntity.setIdFornecedor(1l);
        estoqueEntity.setIdProduto(1l);
        estoqueEntity.setQuantidade(6);
        estoqueEntity.setObservacao("teste");
        estoqueEntity.setDataCompra(new Date());
        estoqueEntity.setPrecoCompra(34.4d);
        try {
            EstoqueEntity e = adminService.saveEstoque(estoqueEntity);
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getId());
            Assert.assertNotNull(e.getProdutos());
            Assert.assertEquals(6,e.getProdutos().size());

            Long id = e.getId();
            e.setQuantidade(10);
            e.setObservacao("teste123456");
            e.setPrecoCompra(35d);
            e = adminService.saveEstoque(e);

            Assert.assertNotNull(e);
            Assert.assertEquals(id,e.getId());
            Assert.assertEquals("teste123456",e.getObservacao());
            Assert.assertEquals(new Double(35d),e.getPrecoCompra());
            Assert.assertEquals(10,e.getProdutos().size());

        } catch (AdminServiceException e) {
            LOG.error("Error:", e);
            Assert.assertTrue(e.getMessage(), false);
        }

    }


    @Test
    public void atualizarEstoqueDiminuirItens(){

        EstoqueEntity estoqueEntity = new EstoqueEntity();
        estoqueEntity.setIdFornecedor(1l);
        estoqueEntity.setIdProduto(1l);
        estoqueEntity.setQuantidade(6);
        estoqueEntity.setObservacao("teste");
        estoqueEntity.setDataCompra(new Date());
        estoqueEntity.setPrecoCompra(34.4d);
        try {
            EstoqueEntity e = adminService.saveEstoque(estoqueEntity);
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getId());
            Assert.assertNotNull(e.getProdutos());
            Assert.assertEquals(6,e.getProdutos().size());

            Long id = e.getId();
            e.setQuantidade(5);
            e.setObservacao("teste123456");
            e.setPrecoCompra(35d);
            e = adminService.saveEstoque(e);

            Assert.assertNotNull(e);
            Assert.assertEquals(id,e.getId());
            Assert.assertEquals("teste123456",e.getObservacao());
            Assert.assertEquals(new Double(35d),e.getPrecoCompra());
            Assert.assertEquals(5,e.getProdutos().size());

        } catch (AdminServiceException e) {
            LOG.error("Error:", e);
            Assert.assertTrue(e.getMessage(), false);
        }

    }


    @Test
    public void listEstoque(){
        List<ProdutoEstoqueVo> produtos = adminService.listProdutoEmEstoque();

        Assert.assertNotNull(produtos);
        Assert.assertTrue(produtos.size() > 0);


        for (ProdutoEstoqueVo produto : produtos){
            if (produto.getIdProduto() == 3) {
                Assert.assertEquals("PRODUTO TESTE ESTOQUE", produto.getNomeProduto());
                Assert.assertEquals("TESTE", produto.getReferencia());
                Assert.assertEquals("PORTA RETRATO", produto.getTipoProduto());
                Assert.assertEquals(new Long(2), produto.getQuantidade());
            }
        }
    }
}