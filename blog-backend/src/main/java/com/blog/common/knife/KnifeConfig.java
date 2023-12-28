package com.blog.common.knife;

import com.blog.common.property.ProjectProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author zouzhangpeng
 * @desc
 */
@RequiredArgsConstructor
@Configuration
public class KnifeConfig {

    private final ProjectProperty projectProperty;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title(projectProperty.getTitle()).version(projectProperty.getVersion())
                .description(projectProperty.getDescription()).termsOfService(projectProperty.getWebsite())
                .license(new License().name("Apache 2.0").url(projectProperty.getWebsite())))
            .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
            .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                new SecurityScheme().name(HttpHeaders.AUTHORIZATION).type(SecurityScheme.Type.HTTP).scheme("Bearer")));
    }

}
