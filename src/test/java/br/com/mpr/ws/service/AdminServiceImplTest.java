package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.dao.CommonDaoImplTest;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.TabelaPrecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

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
     *
     * TESTES NO CADASTRO DE FORNECEDOR.
     *
     */
    @Test
    public void criarFornecedor(){
        FornecedorEntity fe = new FornecedorEntity();
        fe.setNome("Wagner Jeronimo");
        fe.setCnpj("11111111111");
        fe.setEndereco("Rua balbalba, 333, vila ahuahau, guarulhos sp");
        fe.setEmail("wag182@gmail.com");
        fe.setTelefonePrincipal("11983777633");
        fe.setTelefoneSecundario("11983777633");
        try {
            fe = adminService.saveFornecedor(fe);
            Assert.assertNotNull(fe.getId());
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }
    }

    @Test
    public void atualizarFornecedor(){
        FornecedorEntity fe = commonDao.get(FornecedorEntity.class,1l);
        fe.setNome("Claudio PR");
        try {
            fe = adminService.saveFornecedor(fe);
            Assert.assertTrue(fe.getId().equals(1l));
            Assert.assertNotNull(fe.getNome().equals("Claudio PR"));
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }
    }


    @Test
    public void removerFornecedor(){
        try{
            adminService.removeFornecedorById(2);
            FornecedorEntity fornecedorRemovido = commonDao.get(FornecedorEntity.class,2l);
            Assert.assertNull(fornecedorRemovido);
        }catch(AdminServiceException ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    /*REGRA1*/
    @Test
    public void criarFornecedorMesmoCnpjException(){
        FornecedorEntity fornecedorMesmoCnpj = commonDao.get(FornecedorEntity.class,1l);
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

    /*REGRA2*/
    @Test
    public void removerFornecedorProdutoVinculadoException(){
        try {
            adminService.removeFornecedorById(1);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("NÃO PODE"));
        }

    }


    /**
     *
     * TESTES NO CADASTRO DE PRECO.
     *
     */
    @Test
    public void criarTabelaPreco(){
        TabelaPrecoEntity preco = new TabelaPrecoEntity();

        preco.setDataVigencia(DateUtils.addDays(new Date(),2));
        preco.setIdProduto(30l);
        preco.setPreco(50.50);
        preco.setDescricao("valor inicial");
        try {
            preco = adminService.savePreco(preco);
            Assert.assertNotNull(preco.getId());
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }

    }

    @Test
    public void atualizarTabelaPreco(){
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class,1l);
        tabPreco.setId(null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,30);
        c.set(Calendar.MONTH,Calendar.JANUARY);
        c.set(Calendar.YEAR,c.get(Calendar.YEAR)+1);
        tabPreco.setDataVigencia(c.getTime());
        tabPreco.setPreco(555.40);
        try {
            tabPreco = adminService.savePreco(tabPreco);
            Assert.assertNotNull(tabPreco.getId());
            Assert.assertTrue(tabPreco.getPreco().equals(555.40));

        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }

    }


    @Test
    public void removerTabelaPreco(){
        try {
            TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class,1l);
            tabPreco.setId(null);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH,1);
            c.set(Calendar.MONTH,Calendar.JANUARY);
            c.set(Calendar.YEAR,c.get(Calendar.YEAR)+1);
            tabPreco.setDataVigencia(c.getTime());
            tabPreco.setPreco(50.40);
            tabPreco = adminService.savePreco(tabPreco);
            Assert.assertNotNull(tabPreco.getId());
            adminService.removeTabelaPrecoById(tabPreco.getId());
            Assert.assertTrue(true);

        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }

    }

    /*REGRA1: NAO PODE REMOVER UMA TABELA DE PRECO COM DATA <= DATA_HOJE*/
    @Test
    public void removerTabelaPrecoDataRetroativaException(){
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
    public void criarTabelaPrecoMesmaDataException(){
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class,1l);
        tabPreco.setId(null);
        tabPreco.setPreco(50.40);
        try {
            tabPreco = adminService.savePreco(tabPreco);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("JÁ EXISTE"));
        }
    }

    /*REGRA3: NAO PODE ADICIONAR UMA TABELA DE PRECO COM DATA <= DATA_HOJE */
    @Test
    public void criarTabelaPrecoDataRetroativaException(){
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class,1l);
        tabPreco.setId(null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,27);
        c.set(Calendar.MONTH,Calendar.JUNE);
        c.set(Calendar.YEAR,2018);
        tabPreco.setDataVigencia(c.getTime());
        tabPreco.setPreco(50.40);
        try {
            tabPreco = adminService.savePreco(tabPreco);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("RETROATIVA"));
        }
    }

    /*REGRA4: NAO PODE ALTERAR UMA TABELA DE PRECO COM DATA <= DATA_HOJE */
    @Test
    public void alterarTabelaPrecoDataRetroativaException(){
        TabelaPrecoEntity tabPreco = commonDao.get(TabelaPrecoEntity.class,1l);
        tabPreco.setDescricao("alterar");
        tabPreco.setPreco(50.40);
        try {
            tabPreco = adminService.savePreco(tabPreco);
            Assert.assertTrue(false);
        } catch (AdminServiceException e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e.getMessage().toUpperCase().contains("RETROATIVA"));
        }
    }

    /**
     *
     * CADASTRO DE CLIENTES
     *
     */
    @Test
    public void saveCliente(){
        LOG.info("test.save");
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Philipe Coutinho");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,12);
        c.set(Calendar.MONTH,Calendar.JULY);
        c.set(Calendar.YEAR,1992);
        cliente.setAniversario(c.getTime());
        cliente.setCelular("111111111111");
        cliente.setCpf("89249345038");
        cliente.setEmail("email@email.com");
        try{
            cliente = adminService.saveCliente(cliente);
            Assert.assertNotNull(cliente.getId());
            Assert.assertTrue(cliente.getNome().equals("Philipe Coutinho"));
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    @Test
    //REGRA1
    public void saveClienteCpfInvalido(){

    }

    @Test
    //REGRA2
    public void saveClienteCpfJaExiste(){

    }

    @Test
    //REGRA3
    public void saveClienteEmailInvalido(){

    }

    @Test
    //REGRA4
    public void saveClienteEmailJaExiste(){

    }

    @Test
    public void alterarCliente(){
        ClienteEntity clienteEntity = commonDao.get(ClienteEntity.class,1l);
        clienteEntity.setNome("NOVO NOME");
        try{
            clienteEntity = adminService.saveCliente(clienteEntity);
            Assert.assertTrue(clienteEntity.getNome().equals("NOVO NOME"));
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    @Test
    public void deleteCliente(){
        ClienteEntity clienteEntity = commonDao.get(ClienteEntity.class,4l);
        Assert.assertNotNull(clienteEntity);
        try{
            adminService.removeClienteById(clienteEntity.getId());
            clienteEntity = commonDao.get(ClienteEntity.class,4l);
            Assert.assertNull(clienteEntity);
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

}