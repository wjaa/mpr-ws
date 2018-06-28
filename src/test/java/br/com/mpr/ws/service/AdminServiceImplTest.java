package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.dao.CommonDaoImplTest;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.TabelaPrecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;
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

    /**
     *
     * TESTES NO CADASTRO DE PRECO.
     *
     */


    @Test
    public void criarTabelaPreco(){
        TabelaPrecoEntity preco = new TabelaPrecoEntity();
        preco.setDataVigencia(new Date());
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
        tabPreco.setPreco(50.40);
        try {
            tabPreco = adminService.savePreco(tabPreco);
            Assert.assertTrue(tabPreco.getId().equals(1l));
            Assert.assertTrue(tabPreco.getPreco().equals(50.40));

        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }

    }



    /**
     *
     * CADASTRO DE CLIENTES
     *
     */
    @Test
    public void saveCliente() throws Exception {
        LOG.info("test.save");
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Philipe Coutinho");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,12);
        c.add(Calendar.MONTH,7);
        c.add(Calendar.YEAR,1992);
        cliente.setAniversario(c.getTime());
        cliente.setCelular("111111111111");
        cliente.setCpf("11111111111");
        cliente.setEmail("email@email.com");
        cliente = adminService.saveCliente(cliente);
        Assert.assertNotNull(cliente.getId());
        Assert.assertTrue(cliente.getNome().equals("Philipe Coutinho"));
    }



}
