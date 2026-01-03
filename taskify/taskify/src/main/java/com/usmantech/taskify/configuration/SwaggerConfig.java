package com.usmantech.taskify.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI swaggerCustomeConfig() {
		return new OpenAPI().info(
				new Info().title("Taskify App API's")
				.description("Taskify help you to manage your task efficently"))
				.servers(Arrays.asList(
						new Server().url("http://localhost:8080").description("local"),
						new Server().url("http://localhost:8081").description("live")))
				// Add security requirement globally
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                // Define the security scheme
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)
                                        .name("Authorization")));				
	}

}
