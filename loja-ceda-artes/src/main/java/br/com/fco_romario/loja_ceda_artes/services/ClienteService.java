package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.controllers.ClienteController;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.mapper.ClienteMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    //todo adicionar validacao com validators @NotNull e @Positive
    public ClienteDTO buscarPorId(Integer id) {
        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + ClienteDTO.class.getSimpleName()));

        ClienteDTO dto = ClienteMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public List<ClienteDTO> buscarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> {
                    ClienteDTO dto = ClienteMapper.toDTO(cliente);
                    adicionaLinksHateoas(dto);
                    return  dto;
                }).toList();
    }

    public ClienteDTO criar(ClienteDTO clienteDTO) {
        if(clienteDTO.getId() != null )
            throw new IllegalArgumentException("O ID do Cliente deve ser nulo na criação.");

        if(clienteDTO.getEnderecos() == null || clienteDTO.getEnderecos().isEmpty())
            throw new IllegalArgumentException("Ao criar um Cliente é necessário ao menos um endereço.");

        boolean enderecoComId = clienteDTO.getEnderecos()
                .stream()
                .anyMatch(endereco -> endereco.getId() != null);

        if(enderecoComId)
            throw new IllegalArgumentException("Os endereços não devem conter ID na criação do Cliente.");

        Cliente entity = clienteRepository.save(ClienteMapper.toEntity(clienteDTO));
        ClienteDTO dto = ClienteMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public ClienteDTO atualizar(ClienteDTO clienteDTO) {
        Cliente entity =  ClienteMapper.toEntity(buscarPorId(clienteDTO.getId()));

        entity.setNome(clienteDTO.getNome());
        entity.setEmail(clienteDTO.getEmail());
        entity.setCpfOuCnpj(clienteDTO.getCpfOuCnpj());
        entity.setTipo(clienteDTO.getTipo());

        ClienteDTO dto = ClienteMapper.toDTO(clienteRepository.save(entity));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public void deletar(Integer id) {
        Cliente entity =  ClienteMapper.toEntity(buscarPorId(id));
        clienteRepository.delete(entity);
    }

    public ClienteDTO buscarClientePorEnderecoId(Integer enderecoId) {
        Cliente entity = clienteRepository.buscarClientePorEnderecoId(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum cliente para o endereço de id: " + enderecoId));

        ClienteDTO dto = ClienteMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    private void adicionaLinksHateoas(ClienteDTO dto) {
        dto.add(linkTo(methodOn(ClienteController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ClienteController.class).buscarTodos()).withRel("buscarTodos").withType("GET"));
        dto.add(linkTo(methodOn(ClienteController.class).criar(dto)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(ClienteController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(ClienteController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }
}