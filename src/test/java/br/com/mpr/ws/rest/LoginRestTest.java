package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.LoginEntity;
import br.com.mpr.ws.exception.LoginServiceException;
import br.com.mpr.ws.helper.ClienteHelper;
import br.com.mpr.ws.helper.TestUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.LoginForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 */
public class LoginRestTest extends BaseMvcTest {


    private static final String EMAIL_TESTE = "wag184@gmail.com";
    private static final String CPF_TESTE= "33333333333";


    @Autowired
    private CommonDao commonDao;

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


    /**
     *
     */
    @Test
    public void testResetPassword(){
        try{
            ResultActions ra = getMvcPostFormResultAction("/api/v1/core/login/reset",
                    "email=" + EMAIL_TESTE + "&cpf=" + CPF_TESTE);
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    /**
     *
     */
    @Test
    public void testResetPasswordException1(){
        try{
            ResultActions ra = getMvcPostFormErrorResultAction("/api/v1/core/login/reset",
                    "email=" + EMAIL_TESTE + "&cpf=45645645646");
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Cadastro não encontrado"));
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    /**
     *
     */
    @Test
    public void testResetPasswordException2(){

        try{
            ResultActions ra = getMvcPostFormErrorResultAction("/api/v1/core/login/reset",
                    "email=wag18@gmail.com" + "&cpf=" + CPF_TESTE);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Cadastro não encontrado"));
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    @Test
    public void testChangePassword(){
        try{
            String novaSenha = "123456789";
            getMvcPostFormResultAction("/api/v1/core/login/reset",
                    "email=" + EMAIL_TESTE + "&cpf=" + CPF_TESTE);
            ClienteEntity cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});

            getMvcPostFormResultAction("/api/v1/core/login/changePass",
                    "hash=" + cliente.getLogin().getHashTrocaSenha() + "&pass=" + novaSenha);
            cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});
            Assert.assertEquals(novaSenha,cliente.getLogin().getPass());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    @Test
    public void testChangePasswordException1(){

        try{
            String novaSenha = "abcdefgi";
            getMvcPostFormResultAction("/api/v1/core/login/reset",
                    "email=" + EMAIL_TESTE + "&cpf=" + CPF_TESTE);
            ClienteEntity cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});

            LoginEntity login = cliente.getLogin();
            login.setExpirationTrocaSenha(new Date());
            commonDao.update(login);

            ResultActions ra = getMvcPostFormErrorResultAction("/api/v1/core/login/changePass",
                    "hash=" + cliente.getLogin().getHashTrocaSenha() + "&pass=" + novaSenha);


            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Código para troca de senha expirou"));

            cliente = commonDao.findByPropertiesSingleResult(
                    ClienteEntity.class,new String[]{"email"}, new Object[]{EMAIL_TESTE});

            Assert.assertNotEquals(novaSenha,cliente.getLogin().getPass());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    /**
     *
     */
    @Test
    public void testChangePasswordException2(){

        try{
            String novaSenha = "mgmfkjfjfjf";
            ResultActions ra = getMvcPostFormErrorResultAction("/api/v1/core/login/changePass",
                    "hash=adfasfasdfsadfsad" + "&pass=" + novaSenha);


            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString()
                    .contains("Código para troca de senha não encontrado"));

        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    protected AppUser getAppUser(){
        return getAppUserClient();
    }

}