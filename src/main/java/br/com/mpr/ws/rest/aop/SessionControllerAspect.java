package br.com.mpr.ws.rest.aop;

import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.service.SessionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class SessionControllerAspect {

    private static final Log LOG = LogFactory.getLog(SessionControllerAspect.class);

    @Autowired
    private SessionService sessionService;

    //@Around("@annotation(br.com.mpr.ws.rest.aop.SessionController)")
    public void validateSessionToken(String sessionToken){
        LOG.info(sessionToken);
        SessionEntity entity = sessionService.getSessionByToken(sessionToken);
        LOG.info("Session existis = " + entity != null);
    }

}
