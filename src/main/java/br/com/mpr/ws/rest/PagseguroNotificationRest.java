package br.com.mpr.ws.rest;

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

    @PostConstruct
    private void init(){
        //Credential credential = Credential.applicationCredential("app3620108836", "2E3924589797D5A8848FCF8A50692011");
        Credential credential = Credential.sellerCredential("admin@meuportaretrato.com", "9F613A6E90C447599BA6BA793221620B");
        PagSeguroEnv environment = PagSeguroEnv.SANDBOX;
        pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(),
                new JSEHttpClient(),
                credential, environment);

    }

    @Autowired
    private PagSeguroNotificationHandler pagseguroNotification;

    @RequestMapping(value = "/notification",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public void notification(HttpServletRequest request){
        pagSeguro.notifications().handle(request,pagseguroNotification);
    }

}
