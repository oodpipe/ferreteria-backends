package cl.ferreteria.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Ferretería Herramientas Plus")
                        .version("1.0")
                        .description("API REST para sistema de gestión de ferretería")
                        .contact(new Contact()
                                .name("Equipo Desarrollo")
                                .email("desarrollo@herramientasplus.cl")));
    }
}