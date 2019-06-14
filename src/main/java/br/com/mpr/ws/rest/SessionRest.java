package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/core")
public class SessionRest {

    @Autowired
    private SessionService sessionService;


    @RequestMapping(value = "/session/create",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public SessionEntity createSession(OAuth2Authentication appUser){

        if (appUser.getUserAuthentication() != null){
            throw new AccessDeniedException("Usuário está logado e não pode criar uma session.");
        }

        return this.sessionService.createSession();
    }

}
