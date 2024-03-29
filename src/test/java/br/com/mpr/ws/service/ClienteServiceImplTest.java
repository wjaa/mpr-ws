package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.helper.ClienteHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wagner on 7/11/18.
 */
public class ClienteServiceImplTest extends BaseDBTest {

    private static final Log LOG = LogFactory.getLog(ClienteServiceImplTest.class);

    @Autowired
    private ClienteService clienteService;

    @Test
    public void getClienteById(){
        ClienteEntity clienteEntity = clienteService.getClienteById(1l);
        Assert.assertEquals(new Long(1l),clienteEntity.getId());
        Assert.assertNotNull(clienteEntity);
        Assert.assertNotNull(clienteEntity.getEnderecos());
        Assert.assertNotNull(clienteEntity.getLogin());
    }

    @Test
    public void getClienteByKeyDevice(){
        ClienteEntity clienteEntity = clienteService.getClienteByKeyDevice("ZAZAZAZA");
        Assert.assertEquals(new Long(1l),clienteEntity.getId());
        Assert.assertEquals("ZAZAZAZA",clienteEntity.getLogin().getKeyDeviceGcm());
        Assert.assertNotNull(clienteEntity);
        Assert.assertNotNull(clienteEntity.getEnderecos());
        Assert.assertNotNull(clienteEntity.getLogin());
    }



    /**
     *
     * CADASTRO SIMPLES DE CLIENTES PASSWORD
     *
     */
    @Test
    public void createClientePassword(){
        LOG.info("createClientePassword");
        ClienteEntity cliente = ClienteHelper.createClienteLoginPassword();
        try{
            cliente = clienteService.saveCliente(cliente);
            Assert.assertNotNull(cliente.getId());
            Assert.assertTrue(cliente.getNome().equals("Paulo Paulada"));
            ClienteEntity clienteEntity = clienteService.getClienteById(cliente.getId());
            Assert.assertNotNull(clienteEntity);
            Assert.assertNotNull(clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
            Assert.assertNotNull(clienteEntity.getLogin().getId());
            Assert.assertEquals(LoginType.PASSWORD, clienteEntity.getLogin().getLoginType());
            Assert.assertNotNull(clienteEntity.getEnderecos());
            Assert.assertEquals(1,clienteEntity.getEnderecos().size());

        }catch (Exception ex){
            LOG.error("Error:" , ex);
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    /**
     *
     * CADASTRO SIMPLES DE CLIENTES SOCIAL
     *
     */
    @Test
    public void createClienteSocial(){
        LOG.info("createClienteSocial");
        ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
        try{
            cliente = clienteService.saveCliente(cliente);
            Assert.assertNotNull(cliente.getId());
            Assert.assertTrue(cliente.getNome().equals("Fernanda Nigths Nigths"));
            ClienteEntity clienteEntity = clienteService.getClienteById(cliente.getId());
            Assert.assertNotNull(clienteEntity);
            Assert.assertNotNull(clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
            Assert.assertNotNull(clienteEntity.getLogin().getId());
            Assert.assertEquals(LoginType.FACEBOOK, clienteEntity.getLogin().getLoginType());
            Assert.assertNotNull(clienteEntity.getEnderecos());
            Assert.assertEquals(1,clienteEntity.getEnderecos().size());

        }catch (Exception ex){
            LOG.error("Error:" , ex);
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    /**
     * 1. Tentar cadastrar cliente com cpf invalido
     * 2. Tentar cadastrar cliente com cpf já existente
     * 3. Tentar cadastrar cliente com email invalido.
     * 4. Tentar cadastrar cliente com email já existente.
     * 5. Tentar cadastrar cliente com endereco invalido.
     * 6. Tentar cadastrar cliente com login invalido.
     */
    @Test
    public void saveClienteValidation(){

        //#1
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            cliente.setCpf("123.456.789-11");
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("Cpf inválido"));
        }


        //#2
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            clienteService.saveCliente(cliente);
            ClienteEntity cliente2 = ClienteHelper.createClienteLoginSocial();
            cliente2.setCpf(cliente.getCpf());
            clienteService.saveCliente(cliente2);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("Já existe um cliente cadastrado com esse CPF"));
        }

        //#3
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginPassword();
            cliente.setEmail("ahahahahahajaja@");
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("E-mail inválido"));
        }

        //#4
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            clienteService.saveCliente(cliente);
            ClienteEntity cliente2 = ClienteHelper.createClienteLoginSocial();
            cliente2.setEmail(cliente.getEmail());
            clienteService.saveCliente(cliente2);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("Já existe um cliente cadastrado com esse E-MAIL"));
        }

        //#5
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            cliente.setEnderecos(null);
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("Endereço é obrigatório"));
        }

        //#5.1
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            cliente.getEnderecos().get(0).setLogradouro(null);
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("Logradouro é obrigatório"));
        }

        //#6
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            cliente.setLogin(null);
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("Login é obrigatório"));
        }

        //#6.1
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginSocial();
            cliente.getLogin().setSocialKey("");
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("SocialKey é obrigatório"));
        }

        //#6.2
        try {
            ClienteEntity cliente = ClienteHelper.createClienteLoginPassword();
            cliente.getLogin().setSenha("");
            clienteService.saveCliente(cliente);
        } catch (ClienteServiceException e) {
            Assert.assertTrue(e.getMessage(),e.getMessage().contains("cliente precisa criar uma senha"));
        }
    }


    @Test
    public void alterarCliente(){
        ClienteEntity clienteEntity = clienteService.getClienteById(1l);
        clienteEntity.setNome("NOVO NOME");
        try{
            clienteEntity = clienteService.saveCliente(clienteEntity);
            Assert.assertTrue(clienteEntity.getNome().equals("NOVO NOME"));
            Assert.assertNotNull(clienteEntity.getEnderecos());
            Assert.assertNotNull(clienteEntity.getLogin());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    /**
     * 1. Alterar um cliente, e remover o Login (nao deve fazer nada).
     * 2. Alterar um cliente e remover o endereco (deverá estourar um erro).
     */
    @Test
    public void alterarClienteValidate(){

        //#1
        try{
            ClienteEntity clienteEntity = clienteService.getClienteById(1l);
            clienteEntity.setLogin(null);
            clienteService.saveCliente(clienteEntity);
            clienteEntity = clienteService.getClienteById(1l);
            Assert.assertTrue(clienteEntity.getLogin() != null);

        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }

        //#2
        try{
            ClienteEntity clienteEntity = clienteService.getClienteById(1l);
            clienteEntity.setEnderecos(null);
            clienteService.saveCliente(clienteEntity);

        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),ex.getMessage().contains("Endereço é obrigatório"));
        }
    }






}
