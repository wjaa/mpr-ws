package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.service.SessionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public abstract class BaseRest {

    private static final Log LOG = LogFactory.getLog(BaseRest.class);


    @Autowired
    private SessionService sessionService;



    protected ClienteEntity validateUser(OAuth2Authentication principal) {
        Authentication user = principal.getUserAuthentication();
        if (user == null){
            throw new AccessDeniedException("Usuário não autenticado");
        }
        return (ClienteEntity) user.getPrincipal();
    }


}
