package br.com.fco_romario.loja_ceda_artes.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Cidade;
import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.dtos.CidadeDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;

public class CidadeMapper {

    public static Cidade toEntity(CidadeDTO dto) {
        if(dto == null) return null;

        return new Cidade(dto.getId(),dto.getNome(), EstadoMapper.toEntity(dto.getEstado()));
    }

    public static CidadeDTO toDTO(Cidade entity) {
        if(entity == null) return null;

        return new CidadeDTO(entity.getId(),entity.getNome(), EstadoMapper.toDTO(entity.getEstado()));
    }
}
