package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SessionServiceImplTest extends BaseDBTest {

    @Autowired
    private SessionService sessionService;


    @Test
    public void createSession() {
        SessionEntity sessionEntity = sessionService.createSession();
        Assert.assertNotNull(sessionEntity);
        Assert.assertNotNull(sessionEntity.getSessionToken());
        Assert.assertNotNull(sessionEntity.getExpirationDate());
        Assert.assertEquals(64l , sessionEntity.getSessionToken().length());
        Assert.assertTrue(new Date().before(sessionEntity.getExpirationDate()));
        Assert.assertTrue(sessionEntity.getExpirationDate().getTime() - new Date().getTime() > 1000*60*59);

    }

    @Test
    public void renewSession(){
        try{
            SessionEntity sessionEntity = sessionService.createSession();
            Thread.sleep(1000);
            sessionService.renewSession(sessionEntity.getSessionToken());
            SessionEntity sessionRenew = sessionService.getSessionByToken(sessionEntity.getSessionToken());
            Assert.assertEquals(sessionEntity.getSessionToken(), sessionRenew.getSessionToken());
            Assert.assertTrue(sessionEntity.getExpirationDate().before(sessionRenew.getExpirationDate()));
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void getSessionByToken(){
        SessionEntity session1 = sessionService.createSession();
        SessionEntity session2 = sessionService.getSessionByToken(session1.getSessionToken());

        Assert.assertEquals(session1.getId(),session2.getId());
        Assert.assertEquals(session1.getSessionToken(),session2.getSessionToken());
        Assert.assertEquals(session1.getExpirationDate(),session2.getExpirationDate());
    }

}