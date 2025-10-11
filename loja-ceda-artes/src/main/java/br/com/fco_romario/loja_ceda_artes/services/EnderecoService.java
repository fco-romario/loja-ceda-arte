package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.controllers.ClienteController;
import br.com.fco_romario.loja_ceda_artes.controllers.EnderecoController;
import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.mapper.ClienteMapper;
import br.com.fco_romario.loja_ceda_artes.mapper.EnderecoMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import br.com.fco_romario.loja_ceda_artes.repositories.EnderecoRepository;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteService clienteService;

//    @Autowired
//    private ModelMapper modelMapper;

    public EnderecoDTO buscarPorId(Integer id) {
        Endereco entity = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + EnderecoDTO.class.getSimpleName()));

        EnderecoDTO dto = EnderecoMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public List<EnderecoDTO> buscarTodos() {
        return enderecoRepository.findAll()
                .stream()
                .map(endereco -> {
                     EnderecoDTO dto =  EnderecoMapper.toDTO(endereco);
                     adicionaLinksHateoas(dto);
                     return dto;
                }).toList();
    }

    public EnderecoDTO criar(EnderecoDTO enderecoDTO) {
        if(enderecoDTO.getId() != null)
            throw new IllegalArgumentException("O ID do Endereço deve ser nulo na criação");

        if(enderecoDTO.getCliente() == null || enderecoDTO.getCliente().getId() == null)
            throw new IllegalArgumentException("O Cliente é obrigaótrio.");

        Cliente cliente = ClienteMapper.toEntity(clienteService.buscarPorId(enderecoDTO.getCliente().getId()));
        Endereco entity = EnderecoMapper.toEntity(enderecoDTO);
        entity.setCliente(cliente);

        EnderecoDTO dto = EnderecoMapper.toDTO((enderecoRepository.save(entity)));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public EnderecoDTO atualizar(EnderecoDTO enderecoDTO) {
        Endereco entity = EnderecoMapper.toEntity(buscarPorId(enderecoDTO.getId()));
        Cliente cliente = ClienteMapper.toEntity(clienteService.buscarClientePorEnderecoId(enderecoDTO.getId()));

        entity.setLogradouro(enderecoDTO.getLogradouro());
        entity.setNumero(enderecoDTO.getNumero());
        entity.setComplemento(enderecoDTO.getComplemento());
        entity.setBairro(enderecoDTO.getBairro());
        entity.setCep(enderecoDTO.getCep());
        entity.setCliente(cliente);

        EnderecoDTO dto = EnderecoMapper.toDTO(enderecoRepository.save(entity));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public void deletar(Integer id) {
        Endereco entity =  EnderecoMapper.toEntity(buscarPorId(id));
        enderecoRepository.delete(entity);
    }

    private void adicionaLinksHateoas(EnderecoDTO dto) {
        dto.add(linkTo(methodOn(EnderecoController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(EnderecoController.class).buscarTodos()).withRel("buscarTodos").withType("GET"));
        dto.add(linkTo(methodOn(EnderecoController.class).criar(dto)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(EnderecoController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(EnderecoController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }
}