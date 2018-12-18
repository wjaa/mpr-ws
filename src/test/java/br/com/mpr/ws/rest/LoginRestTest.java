package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.helper.ClienteHelper;
import br.com.mpr.ws.helper.TestUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.LoginForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 */
public class LoginRestTest extends BaseMvcTest {



    @Test
    public void testLoginGPlus(){

        LoginForm loginForm = new LoginForm();
        loginForm.setLoginType(LoginType.GPLUS);
        loginForm.setSocialKey("SOCIAL_KEY_GPLUS");

        try{
            String content = TestUtils.toJson(loginForm);
            ResultActions ra = getMvcPostResultAction("/api/v1/core/login", content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ClienteEntity clienteEntity = ObjectUtils.fromJSON(resultJson,ClienteEntity.class);
            Assert.assertNotNull(clienteEntity);
            Assert.assertEquals(new Long(1),clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
            Assert.assertEquals(loginForm.getSocialKey(),clienteEntity.getLogin().getSocialKey());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void testLoginFacebook(){
        LoginForm loginForm = new LoginForm();
        loginForm.setLoginType(LoginType.FACEBOOK);
        loginForm.setSocialKey("SOCIAL_KEY_FACEBOOK");

        try{
            String content = TestUtils.toJson(loginForm);
            ResultActions ra = getMvcPostResultAction("/api/v1/core/login", content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ClienteEntity clienteEntity = ObjectUtils.fromJSON(resultJson,ClienteEntity.class);
            Assert.assertNotNull(clienteEntity);
            Assert.assertEquals(new Long(2),clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());
            Assert.assertEquals(loginForm.getSocialKey(),clienteEntity.getLogin().getSocialKey());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void testLoginPassword(){
        LoginForm loginForm = new LoginForm();
        loginForm.setLoginType(LoginType.PASSWORD);
        loginForm.setEmail("wag184@gmail.com");
        loginForm.setPassword("1234567");

        try{
            String content = TestUtils.toJson(loginForm);
            ResultActions ra = getMvcPostResultAction("/api/v1/core/login", content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ClienteEntity clienteEntity = ObjectUtils.fromJSON(resultJson,ClienteEntity.class);
            Assert.assertNotNull(clienteEntity);
            Assert.assertEquals(new Long(3),clienteEntity.getId());
            Assert.assertNotNull(clienteEntity.getLogin());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    @Test
    public void testErrorLoginGPlus(){
        try{
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.GPLUS);
            loginForm.setSocialKey("UNKNOW");
            //#1
            String content = TestUtils.toJson(loginForm);
            ResultActions ra = getMvcPostErrorResultAction("/api/v1/core/login", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Usuário não encontrado"));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    @Test
    public void testErrorLoginFacebook(){
        try{
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.FACEBOOK);
            loginForm.setSocialKey("UNKNOW");
            //#1
            String content = TestUtils.toJson(loginForm);
            ResultActions ra = getMvcPostErrorResultAction("/api/v1/core/login", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Usuário não encontrado"));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    @Test
    public void testErrorLoginPassword(){
        try{
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginType(LoginType.PASSWORD);
            loginForm.setEmail("wag184@gmail.com");
            loginForm.setPassword("12345678");
            //#1
            String content = TestUtils.toJson(loginForm);
            ResultActions ra = getMvcPostErrorResultAction("/api/v1/core/login", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Usuário ou senha inválida"));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }
}