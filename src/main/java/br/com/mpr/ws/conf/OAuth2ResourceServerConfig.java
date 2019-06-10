package br.com.mpr.ws.conf;

import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

//@Configuration
//@EnableResourceServer
public class OAuth2ResourceServerConfig extends GlobalMethodSecurityConfiguration {

    /*@Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }*/
}
