package br.com.fco_romario.loja_ceda_artes.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;

import java.util.stream.Collectors;

public class ClienteMapper {

    public static Cliente toEntity(ClienteDTO dto) {
        if(dto == null) return null;

        Cliente entity = new Cliente(dto.getId(),dto.getNome() ,dto.getDataNascimento(), dto.getEmail(), dto.getCpfOuCnpj(), dto.getTipo());
        entity.getTelefones().addAll(dto.getTelefones());
        entity.getEnderecos().addAll(
                dto.getEnderecos()
                        .stream()
                        .map(EnderecoMapper::toEntity)
                        .toList()
        );
        entity.getEnderecos().forEach(t -> t.setCliente(entity));
        return entity;
    }

    public static ClienteDTO toDTO(Cliente entity) {
        if(entity == null) return null;

        ClienteDTO dto = new ClienteDTO(entity.getId(),entity.getNome() ,entity.getDataNascimento(), entity.getEmail(), entity.getCpfOuCnpj(), entity.getTipo());
        dto.getTelefones().addAll(entity.getTelefones());
        dto.getEnderecos().addAll(
            entity.getEnderecos()
                    .stream()
                    .map(EnderecoMapper::toDTO)
                    .toList()
        );
        return dto;
    }
}
