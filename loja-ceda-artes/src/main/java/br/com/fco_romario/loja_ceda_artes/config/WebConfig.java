package br.com.fco_romario.loja_ceda_artes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //@Value("${cors.originPatterns}")
    @Value("${cors.originPatterns:http://localhost:8080}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");

        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                //.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json", MediaType.APPLICATION_JSON)
                    .mediaType("xml", MediaType.APPLICATION_XML)
                    .mediaType("yaml", MediaType.APPLICATION_YAML);
    }
}
//consumes:
    //Accept: application/json
    //Accept: application/xml
    //Accept: application/xml

// produces:
    //Content-Type: application/json
    //Content-Type: application/xml
    //Content-Type: application/xml
