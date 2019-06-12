package br.com.mpr.ws.conf.security;

import br.com.mpr.ws.constants.MprConstants;
import br.com.mpr.ws.security.CustomBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 *
 */
@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**")
                .hasAuthority("ADMIN")
                //.hasRole("ADMIN")
                .antMatchers("/api/v1/core/**")
                .hasAuthority("CLIENT")
                //.hasAnyRole("CLIENT")
                .and().httpBasic().realmName(MprConstants.REALM_MPR).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//We don't need sessions to be created.
                ;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO ESSA AUTENTICACAO AQUI É PARA ACESSAR RECURSO DO USUARIO,
        //PRECISO TROCAR PARA A TABELA DE LOGIN, E IMPLENTAR ALGO PARA LOGAR COM ID DO USUARIO OU
        // TOKEN DE LOGIN DA REDE SOCIAL.
        // preciso criar um userdetailsservice. já está no papo.
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?");

    }

   /* @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }*/


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }



}
