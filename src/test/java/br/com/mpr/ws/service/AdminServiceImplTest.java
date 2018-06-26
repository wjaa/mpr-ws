package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDaoImplTest;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.TabelaPrecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wagner on 6/25/18.
 */
public class AdminServiceImplTest extends BaseDBTest {

    private static final Log LOG = LogFactory.getLog(CommonDaoImplTest.class);

    @Autowired
    private AdminService adminService;


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
        FornecedorEntity fe = new FornecedorEntity();
        fe.setId(1l);
        fe.setNome("Claudeio PR");
        fe.setCnpj("11111111111");
        fe.setEndereco("Rua balbalba, 333, vila ahuahau, guarulhos sp");
        fe.setEmail("wag182@gmail.com");
        fe.setTelefonePrincipal("11983777633");
        fe.setTelefoneSecundario("11983777633");
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
        try {
            preco = adminService.savePreco(preco);
            Assert.assertNotNull(preco.getId());
        } catch (AdminServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }


    }




}
