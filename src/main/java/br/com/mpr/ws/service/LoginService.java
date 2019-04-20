package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.LoginServiceException;
import br.com.mpr.ws.vo.LoginForm;

/**
 *
 */
public interface LoginService {

    ClienteEntity login(LoginForm loginForm) throws LoginServiceException;

    void resetSenha(String emailTeste, String cpfTeste) throws LoginServiceException;

    void trocarSenha(String hash, String novaSenha) throws LoginServiceException;
}
