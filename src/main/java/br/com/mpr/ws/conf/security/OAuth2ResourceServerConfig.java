package br.com.mpr.ws.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "oauth2-resource";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/secured/**";

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //.authorizeRequests()
                //.antMatchers("/api/v1/core/**")
                //.hasAuthority("CLIENT")
                //.and()
                //.authorizeRequests()
                //.antMatchers("/api/v1/core/produto/**")
                //.hasRole("READ")
                //.and()

                //requisições para o ADMIN só para para os apps que tem autorizacao.
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**")
                .hasAuthority("ADMIN")

                //.hasRole("ADMIN")
                //.antMatchers("/api/v1/core/**")
                //.hasAuthority("CLIENT")


        ;

                //.hasAnyRole("CLIENT")
    }
}
