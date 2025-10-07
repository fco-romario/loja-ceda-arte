package br.com.fco_romario.loja_ceda_artes.mapper;


import br.com.fco_romario.loja_ceda_artes.domain.Pedido;
import br.com.fco_romario.loja_ceda_artes.dtos.PedidoDTO;

public class PedidoMapper {

    public static Pedido toEntity(PedidoDTO dto) {
        if(dto == null) return null;

        Pedido entity = new Pedido(dto.getId(),dto.getInstante(), PagamentoMapperFactory.toEntity(dto.getPagamento()), ClienteMapper.toEntity(dto.getCliente()), EnderecoMapper.toEntity(dto.getEnderecoDeEntrega()));

        entity.getItens().addAll(
                dto.getItens()
                    .stream()
                    .map(ItemPedidoMapper::toEntity)
                    .toList()
        );
        return entity;
    }

    public static PedidoDTO toDTO(Pedido entity) {
        if(entity == null) return null;

        PedidoDTO dto = new PedidoDTO(entity.getId(),entity.getInstante(), PagamentoMapperFactory.toDTO(entity.getPagamento()), ClienteMapper.toDTO(entity.getCliente()), EnderecoMapper.toDTO(entity.getEnderecoDeEntrega()));

        dto.getItens().addAll(
                entity.getItens()
                        .stream()
                        .map(ItemPedidoMapper::toDTO)
                        .toList()
        );
        return dto;
    }
}
