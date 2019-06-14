package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.PageVo;
import br.com.mpr.ws.vo.ProdutoVo;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Type;
import java.util.Date;

import static org.junit.Assert.*;

/**
 *
 */
public class SessionRestTest extends BaseMvcTest {


    @Test
    public void createSession() {

        try{

            ResultActions ra = getMvcGetResultActions("/api/v1/core/session/create");
            SessionEntity sessionEntity = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    SessionEntity.class);
            Assert.assertNotNull(sessionEntity);
            Assert.assertNotNull(sessionEntity.getSessionToken());
            Assert.assertNotNull(sessionEntity.getExpirationDate());
            Assert.assertEquals(64l , sessionEntity.getSessionToken().length());
            Assert.assertTrue(new Date().before(sessionEntity.getExpirationDate()));
            Assert.assertTrue(sessionEntity.getExpirationDate().getTime() - new Date().getTime() > 1000*60*59);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Override
    protected AppUser getAppUser() {
        return getAppUserClient();
    }
}