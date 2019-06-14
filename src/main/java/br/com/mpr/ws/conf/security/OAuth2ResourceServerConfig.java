package br.com.mpr.ws.conf.security;

import br.com.mpr.ws.rest.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "oauth2-resource";


    @Autowired
    private CustomAccessDeniedHandler handler;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                //CAMADA NAO LOGADA DE PRODUTO.
                .authorizeRequests().antMatchers("/api/v1/core/**")
                .access("hasAuthority('ADMIN') or hasAuthority('APP') or hasAuthority('USER')")
                .and()

                /*.authorizeRequests().antMatchers("/api/v1/core/produto/**")
                .access("hasAuthority('USER')")
                .and()*/

                //requisições para o ADMIN só para para os apps que tem autorizacao.
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**")
                .hasAuthority("ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(handler)


        ;

                //.hasAnyRole("CLIENT")
    }
}
