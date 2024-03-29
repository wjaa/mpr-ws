package br.com.mpr.ws.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder)
                .accessDeniedHandler(accessDeniedHandler);
    }



    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.jdbc(dataSource)/*.withClient("cliente.admin@meuportaretrato.com")
                .authorizedGrantTypes("client_credentials", "password","refresh_token")
                .authorities("ADMIN")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(86400)//24HS
                .secret("$2a$10$DoLpKKSqHW6lyyCMYVt/ee1SWOYTzGA5Tgesxy9eMRxM9a2kb7hv2").refreshTokenValiditySeconds(172800)//48HS

                .and()
                .withClient("cliente.mobile@meuportaretrato.com")
                .authorizedGrantTypes("client_credentials", "password","refresh_token")
                .authorities("CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(86400)//24HS
                .secret("$2a$10$26ypXHcuAP5oZA8/umYuo.0MP0UvSLnVIKsVR.1FpqD4I7neOS622").refreshTokenValiditySeconds(172800)

                .and()
                .withClient("cliente.website@meuportaretrato.com")
                .authorizedGrantTypes("client_credentials", "password","refresh_token")
                .authorities("CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(86400)//24HS
                .secret("$2a$10$1wzee5nelAzi4.6d71.Q0OyQdHNNoJcIFY9yPf6W4VS3X0cPI9MlS").refreshTokenValiditySeconds(172800)*/;
    }

    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {

        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                //.userDetailsService()
        ;
    }


    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


}
