package br.com.mpr.ws.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.mpr.ws.rest"))
                .paths(PathSelectors.ant("/api/v1/core/**"))
                .build()
                .apiInfo(apiInfo());
    }

   /* @Bean
    public Docket oauth() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint"))
                .paths(PathSelectors.ant("/oauth/**"))
                .build()
                .apiInfo(oauthInfo());
    }
*/
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API MeuPortaRetrato.com",
                "API interna, não publica",
                "API 1.0",
                "Terms of service",
                new Contact("Wagner Jeronimo", "meuportaretrato.com", "wagner@meuportaretrato.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

   /* private ApiInfo oauthInfo() {
        return new ApiInfo(
                "OAuth2 MeuPortaRetrato.com",
                "OAuth2 da API",
                "OAuth 2",
                "Terms of service",
                new Contact("Wagner Jeronimo", "meuportaretrato.com", "wagner@meuportaretrato.com"),
                "License of API", "API license URL", Collections.emptyList());
    }*/



}
