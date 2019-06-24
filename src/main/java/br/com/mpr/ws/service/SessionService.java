package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.SessionServiceException;

/**
 *
 */
public interface SessionService {


    SessionEntity createSession();

    void renewSession(String sessionToken) throws SessionServiceException;

    SessionEntity getSessionByToken(String sessionToken);

    String getSessionTokenById(Long idSession);
}
