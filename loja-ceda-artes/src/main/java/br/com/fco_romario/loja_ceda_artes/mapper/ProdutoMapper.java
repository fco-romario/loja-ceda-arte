package br.com.fco_romario.loja_ceda_artes.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Produto;
import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoDTO dto) {
        if(dto == null) return null;

        return new Produto(dto.getId(),dto.getNome(), dto.getPreco());
    }

    public static ProdutoDTO toDTO(Produto entity) {
        if(entity == null) return null;

        return new ProdutoDTO(entity.getId(),entity.getNome(), entity.getPreco());
    }
}
