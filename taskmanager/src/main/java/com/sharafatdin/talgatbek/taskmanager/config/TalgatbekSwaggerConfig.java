package com.sharafatdin.talgatbek.taskmanager.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;

/** @author Talgatbek Sharafatdin */
@Configuration
public class TalgatbekSwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management System API")
                        .description("REST API for Task Management System by Talgatbek Sharafatdin")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Talgatbek Sharafatdin")
                                .email("talgatbek.sharafatdin@example.com")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
