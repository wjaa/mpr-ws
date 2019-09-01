package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.LoginEntity;
import br.com.mpr.ws.exception.LoginServiceException;
import br.com.mpr.ws.vo.LoginForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;


/**
 *
 */
public class LoginServiceImplTest extends BaseDBTest {

    private static final String EMAIL_TESTE = "wag184@gmail.com";
    private static final String CPF_TESTE= "33333333333";
    @Autowired
    private LoginService loginService;

    @Autowired
    private CommonDao commonDao;

    /**
     *
     */
    @Test
    public void testLoginGPlus(){
        try{
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.GPLUS);
            loginForm.setSocialKey("SOCIAL_KEY_GPLUS");
            ClienteEntity clienteEntity = loginService.login(loginForm);

            Assert.assertNotNull(clienteEntity);
            Assert.assertEquals(new Long(1),clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
            Assert.assertEquals(loginForm.getSocialKey(),clienteEntity.getLogin().getSocialKey());
        }catch (LoginServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }



    }

    /**
     *
     */
    @Test
    public void testLoginFacebook(){
        try{
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.FACEBOOK);
            loginForm.setSocialKey("SOCIAL_KEY_FACEBOOK");
            ClienteEntity clienteEntity = loginService.login(loginForm);

            Assert.assertNotNull(clienteEntity);
            Assert.assertEquals(new Long(2),clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
            Assert.assertEquals(loginForm.getSocialKey(),clienteEntity.getLogin().getSocialKey());
        }catch (LoginServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    /**
     *
     */
    @Test
    public void testLoginPassword(){
        try{
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.PASSWORD);
            loginForm.setEmail(EMAIL_TESTE);
            loginForm.setPassword("1234567");
            ClienteEntity clienteEntity = loginService.login(loginForm);

            Assert.assertNotNull(clienteEntity);
            Assert.assertEquals(new Long(3),clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
        }catch (LoginServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }

    }

    /**
     *
     */
    @Test
    public void testErrorLoginGPlus(){

        try{

            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.GPLUS);
            loginForm.setSocialKey("UNKNOW");
            ClienteEntity clienteEntity = loginService.login(loginForm);
            Assert.assertTrue("Não deveria ter chegado aqui", false);

        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Usuário não encontrado"));
        }


    }

    /**
     *
     */
    @Test
    public void testErrorLoginFacebook(){
        try{

            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.FACEBOOK);
            loginForm.setSocialKey("UNKNOW");
            ClienteEntity clienteEntity = loginService.login(loginForm);
            Assert.assertTrue("Não deveria ter chegado aqui", false);

        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Usuário não encontrado"));
        }


    }

    /**
     *
     */
    @Test
    public void testErrorLoginPassword(){

        try{

            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.PASSWORD);
            loginForm.setEmail("fulano@fulano.com");
            loginForm.setPassword("UNKNOW");
            ClienteEntity clienteEntity = loginService.login(loginForm);
            Assert.assertTrue("Não deveria ter chegado aqui", false);

        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Usuário ou senha inválida"));
        }


    }


    /**
     *
     */
    @Test
    public void testResetPassword(){
        try{
            loginService.resetSenha(EMAIL_TESTE,CPF_TESTE);
        }catch (LoginServiceException ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    /**
     *
     */
    @Test
    public void testResetPasswordException1(){
        try{
            loginService.resetSenha(EMAIL_TESTE,"123123456456");
            Assert.assertTrue("Não deveria ter chegado aqui", false);
        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Cadastro não encontrado"));
        }
    }

    /**
     *
     */
    @Test
    public void testResetPasswordException2(){
        try{
            loginService.resetSenha("wag18@gmail.com",CPF_TESTE);
            Assert.assertTrue("Não deveria ter chegado aqui", false);
        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Cadastro não encontrado"));
        }
    }


    @Test
    public void testChangePassword(){
        try{
            String novaSenha = "123456789";
            loginService.resetSenha(EMAIL_TESTE,CPF_TESTE);
            ClienteEntity cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});
            loginService.trocarSenha(cliente.getLogin().getHashTrocaSenha(), novaSenha);
            cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});
            Assert.assertNotNull(cliente.getLogin().getPass());
        }catch (LoginServiceException ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    @Test
    public void testChangePasswordException1(){
        try{
            String novaSenha = "abcdefgh";
            loginService.resetSenha(EMAIL_TESTE,CPF_TESTE);
            ClienteEntity cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});
            LoginEntity login = cliente.getLogin();
            login.setExpirationTrocaSenha(new Date());
            commonDao.update(login);
            loginService.trocarSenha(cliente.getLogin().getHashTrocaSenha(), novaSenha);
            Assert.assertTrue("Não deveria ter chegado aqui", false);
        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Código para troca de senha expirou"));
        }
    }

    /**
     *
     */
    @Test
    public void testChangePasswordException2(){
        try{
            loginService.trocarSenha("xxxxxxxx", "aaaaaaa");
            Assert.assertTrue("Não deveria ter chegado aqui", false);
        }catch (LoginServiceException ex){
            Assert.assertNotNull(ex);
            Assert.assertTrue(ex.getMessage().contains("Código para troca de senha não encontrado"));
        }
    }


}
