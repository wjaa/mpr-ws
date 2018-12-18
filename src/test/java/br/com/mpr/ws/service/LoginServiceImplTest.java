package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.LoginServiceException;
import br.com.mpr.ws.vo.LoginForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class LoginServiceImplTest extends BaseDBTest {

    @Autowired
    private LoginService loginService;

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
            loginForm.setEmail("wag184@gmail.com");
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


}
