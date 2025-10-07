package br.com.fco_romario.loja_ceda_artes.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;

public class EnderecoMapper {

    public static Endereco toEntity(EnderecoDTO dto) {
        if(dto == null) return null;

        return new Endereco(dto.getId(), dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), CidadeMapper.toEntity(dto.getCidade()));
    }

    public static EnderecoDTO  toDTO(Endereco entity) {
        if(entity == null) return null;

        return new EnderecoDTO(entity.getId(), entity.getLogradouro(), entity.getNumero(), entity.getComplemento(), entity.getBairro(), entity.getCep(), CidadeMapper.toDTO(entity.getCidade()));
    }
}
