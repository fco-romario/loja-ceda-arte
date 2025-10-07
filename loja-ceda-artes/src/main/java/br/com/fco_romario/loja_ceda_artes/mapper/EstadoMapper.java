package br.com.fco_romario.loja_ceda_artes.mapper;


import br.com.fco_romario.loja_ceda_artes.domain.Estado;
import br.com.fco_romario.loja_ceda_artes.dtos.EstadoDTO;

public class EstadoMapper {

    public static Estado toEntity(EstadoDTO dto) {
        if(dto == null) return null;

        return new Estado(dto.getId(),dto.getNome());
    }

    public static EstadoDTO toDTO(Estado entity) {
        if(entity == null) return null;

        return new EstadoDTO(entity.getId(),entity.getNome());
    }
}
