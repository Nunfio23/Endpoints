package com.example.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        // Server relativo => Swagger usa el mismo origen (https en Railway)
        Server relativeServer = new Server().url("/");
        return new OpenAPI()
                .info(new Info()
                        .title("Proyecto Inventario API")
                        .version("1.0.0"))
                .servers(List.of(relativeServer));
    }
}
