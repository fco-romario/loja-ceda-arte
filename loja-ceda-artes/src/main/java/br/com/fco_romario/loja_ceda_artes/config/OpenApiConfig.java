package br.com.fco_romario.loja_ceda_artes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Criando RESTFull API")
                        .version("v1")
                        .description("Exercitando conteúdos aprendidos nas últimas aulas")
                        .termsOfService("https://github.com/fco-romario/")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/license/MIT"))
                );
    }
}

//http://localhost:8080/swagger-ui/index.html
