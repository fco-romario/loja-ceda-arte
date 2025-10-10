package br.com.fco_romario.loja_ceda_artes.mapper;


import br.com.fco_romario.loja_ceda_artes.domain.Categoria;
import br.com.fco_romario.loja_ceda_artes.dtos.CategoriaDTO;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaDTO dto) {
        if(dto == null) return null;

        return new Categoria(dto.getId(),dto.getNome());
    }

    public static CategoriaDTO toDTO(Categoria entity) {
        if(entity == null) return null;

        return new CategoriaDTO(entity.getId(),entity.getNome());
    }
}
