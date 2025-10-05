package br.com.fco_romario.loja_ceda_artes.mapper;

import br.com.fco_romario.loja_ceda_artes.data.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapper {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Cliente.class, ClienteDTO.class)
                .addMappings(mapper -> mapper.skip(ClienteDTO::setPedidos));

        return modelMapper;
    }
}
