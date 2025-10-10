package br.com.fco_romario.loja_ceda_artes.mapper;


import br.com.fco_romario.loja_ceda_artes.domain.ItemPedido;
import br.com.fco_romario.loja_ceda_artes.domain.ItemPedidoPK;
import br.com.fco_romario.loja_ceda_artes.dtos.ItemPedidoDTO;

public class ItemPedidoMapper {

    public static ItemPedido toEntity(ItemPedidoDTO dto) {
        if(dto == null) return null;


        return new ItemPedido(ProdutoMapper.toEntity(dto.getProduto()), dto.getDesconto(), dto.getQuantidade(), dto.getPreco());
    }

    public static ItemPedidoDTO toDTO(ItemPedido entity) {
        if(entity == null) return null;

        return new ItemPedidoDTO(ProdutoMapper.toDTO(entity.getProduto()), entity.getDesconto(), entity.getQuantidade(), entity.getPreco());
    }
}
