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
        Server server = new Server()
                .url("https://endpoints-production-4a52.up.railway.app")
                .description("Servidor de producción en Railway");

        return new OpenAPI()
                .info(new Info()
                        .title("Proyecto Inventario API")
                        .version("1.0.0")
                        .description("Documentación generada automáticamente con SpringDoc OpenAPI"))
                .servers(List.of(server));
    }
}
