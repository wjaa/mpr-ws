package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.constants.GeneroType;
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
import java.util.List;

/**
 * Created by wagner on 7/11/18.
 */
public class ClienteServiceImplTest extends BaseDBTest {

    private static final Log LOG = LogFactory.getLog(ClienteServiceImplTest.class);

    @Autowired
    private ClienteService clienteService;


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
        cliente.setGenero(GeneroType.M);
        try{
            cliente = clienteService.saveCliente(cliente);
            Assert.assertNotNull(cliente.getId());
            Assert.assertTrue(cliente.getNome().equals("Philipe Coutinho"));
        }catch (Exception ex){
            LOG.error("Error:" , ex);
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
        ClienteEntity clienteEntity = clienteService.getClienteById(1l);
        clienteEntity.setNome("NOVO NOME");
        try{
            clienteEntity = clienteService.saveCliente(clienteEntity);
            Assert.assertTrue(clienteEntity.getNome().equals("NOVO NOME"));
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

}
