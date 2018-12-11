package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.helper.ClienteHelper;
import br.com.mpr.ws.helper.TestUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 */
public class ClienteRestTest extends BaseMvcTest {


    /**
     * 1. Cadastrar um cliente com sucesso.
     */
    @Test
    public void saveCliente() {

        ClienteEntity cliente = ClienteHelper.createClienteLoginPassword();

        try{
            String content = TestUtils.toJson(cliente);
            ResultActions ra = getMvcPostResultAction("/api/v1/core/cliente/save", content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ClienteEntity clienteResult = ObjectUtils.fromJSON(resultJson,ClienteEntity.class);
            ResultActions raGet = getMvcGetResultActions("/api/v1/core/cliente/byId/" + clienteResult.getId());

            raGet.andExpect(content().json(resultJson));

            Assert.assertNotNull(clienteResult.getId());
            Assert.assertNotNull(clienteResult.getEnderecos());
            Assert.assertEquals(1, clienteResult.getEnderecos().size());
            Assert.assertNotNull(clienteResult.getLogin());
            Assert.assertNotNull(clienteResult.getLogin().getKeyDeviceGcm());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }


    /**
     * 1. Tentar cadastrar um cliente com erro de cpf.
     * 2. Tentar cadastrar um cliente com erro de email.
     * 3. Tentar cadastrar um cliente sem endereco.
     * 4. Tentar cadastrar um cliente sem login.
     */
    @Test
    public void saveClienteErro() {
        try{
            //#1
            ClienteEntity cliente = ClienteHelper.createClienteLoginPassword();
            cliente.setCpf(null);
            String content = TestUtils.toJson(cliente);
            ResultActions ra = getMvcPostErrorResultAction("/api/v1/core/cliente/save", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Cpf é obrigatório"));

            //#2
            cliente = ClienteHelper.createClienteLoginPassword();
            cliente.setEmail(null);
            content = TestUtils.toJson(cliente);
            ra = getMvcPostErrorResultAction("/api/v1/core/cliente/save", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("E-mail é obrigatório"));

            //#3
            cliente = ClienteHelper.createClienteLoginPassword();
            cliente.setEnderecos(null);
            content = TestUtils.toJson(cliente);
            ra = getMvcPostErrorResultAction("/api/v1/core/cliente/save", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Endereço é obrigatório"));

            //#4
            cliente = ClienteHelper.createClienteLoginPassword();
            cliente.setLogin(null);
            content = TestUtils.toJson(cliente);
            ra = getMvcPostErrorResultAction("/api/v1/core/cliente/save", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Login é obrigatório"));



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    /**
     * 1. Pegar um cliente pelo id.
     */
    @Test
    public void getClienteById() {
        try{

            ResultActions raGet = getMvcGetResultActions("/api/v1/core/cliente/byId/1");

            String resultJson = raGet.andReturn().getResponse().getContentAsString();

            ClienteEntity clienteResult = ObjectUtils.fromJSON(resultJson,ClienteEntity.class);
            Assert.assertNotNull(clienteResult.getId());
            Assert.assertEquals(new Long(1),clienteResult.getId());
            Assert.assertNotNull(clienteResult.getEnderecos());
            Assert.assertEquals(1, clienteResult.getEnderecos().size());
            Assert.assertNotNull(clienteResult.getLogin());
            Assert.assertNotNull(clienteResult.getLogin().getKeyDeviceGcm());
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    /**
     * 1. Pegar um cliente pelo keydevice.
     */
    @Test
    public void getClienteByKeyDevice() {

        try{

            ResultActions raGet = getMvcGetResultActions("/api/v1/core/cliente/byKeyDevice/ZAZAZAZA");

            String resultJson = raGet.andReturn().getResponse().getContentAsString();

            ClienteEntity clienteResult = ObjectUtils.fromJSON(resultJson,ClienteEntity.class);
            Assert.assertNotNull(clienteResult.getId());
            Assert.assertNotNull(clienteResult.getEnderecos());
            Assert.assertEquals(1, clienteResult.getEnderecos().size());
            Assert.assertNotNull(clienteResult.getLogin());
            Assert.assertNotNull(clienteResult.getLogin().getKeyDeviceGcm());
            Assert.assertEquals("ZAZAZAZA", clienteResult.getLogin().getKeyDeviceGcm());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }
}