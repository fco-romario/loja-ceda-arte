package br.com.fco_romario.loja_ceda_artes.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapper {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
