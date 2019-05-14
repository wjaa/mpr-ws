package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.exception.LoginServiceException;
import br.com.mpr.ws.service.ClienteService;
import br.com.mpr.ws.service.LoginService;
import br.com.mpr.ws.vo.LoginForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/core")
public class LoginRest extends BaseRest {

    private static final Log LOG = LogFactory.getLog(LoginRest.class);


    @Autowired
    private LoginService loginService;


    @RequestMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ClienteEntity login(@RequestBody @Valid LoginForm loginForm) throws LoginServiceException {
        return this.loginService.login(loginForm);
    }


    @RequestMapping(value = "/login/reset",
                   produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
                   method = RequestMethod.POST)
    public ResponseEntity reset(@RequestParam String email, @RequestParam String cpf) throws LoginServiceException {
        this.loginService.resetSenha(email,cpf);
        return new ResponseEntity<>(
                "OK", HttpStatus.OK);
    }


    @RequestMapping(value = "/login/changePass",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ResponseEntity changePass(@RequestParam String hash, @RequestParam String pass) throws LoginServiceException {
        this.loginService.trocarSenha(hash,pass);
        return new ResponseEntity<>(
                "OK", HttpStatus.OK);
    }

}
