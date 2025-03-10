package com.microservices_project.microservices_demo.modules.person.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("swagger example")
                                            .version("v1")
                                            .description("description")
                                            .termsOfService("terms of service")
                                            .license(new License().name("Apache 2")
                                                                  .url("url")));
    }
}
