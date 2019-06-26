package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.MprParameterType;
import br.com.mpr.ws.service.MprParameterService;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.notification.PagSeguroNotificationHandler;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ps")
public class PagseguroNotificationRest {

    private PagSeguro pagSeguro;

    @Autowired
    private MprParameterService mprParameterService;

    @Autowired
    private PagSeguroNotificationHandler pagseguroNotification;

    @PostConstruct
    private void init(){

        String psApiEmail = mprParameterService.getParameter(MprParameterType.PS_API_EMAIL,"");
        String psApiToken = mprParameterService.getParameter(MprParameterType.PS_API_TOKEN,"");

        Credential credential = Credential.sellerCredential(psApiEmail, psApiToken);
        PagSeguroEnv environment = PagSeguroEnv.SANDBOX;
        pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(),
                new JSEHttpClient(),
                credential, environment);

    }



    @RequestMapping(value = "/notification",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public void notification(HttpServletRequest request){
        pagSeguro.notifications().handle(request,pagseguroNotification);
    }

}
