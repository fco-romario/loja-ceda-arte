package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.controllers.ClienteController;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.file.exporter.contract.FileExporter;
import br.com.fco_romario.loja_ceda_artes.file.exporter.factory.FileExporterFactory;
import br.com.fco_romario.loja_ceda_artes.mapper.ClienteMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PagedResourcesAssembler<ClienteDTO> assembler;

    @Autowired
    private FileExporterFactory exporter;

    //todo adicionar validacao com validators @NotNull e @Positive
    public ClienteDTO buscarPorId(Integer id) {
        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + ClienteDTO.class.getSimpleName()));

        ClienteDTO dto = ClienteMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public PagedModel<EntityModel<ClienteDTO>> buscarTodos(Pageable paginado) {
        Page<Cliente> clientesPaginado = clienteRepository.findAll(paginado);

        var clientesComLinks = clientesPaginado
                .map(cliente -> {
                    ClienteDTO dto = ClienteMapper.toDTO(cliente);
                    adicionaLinksHateoas(dto);
                    return dto;
                });

        Link findAllLinks = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ClienteController.class).buscarTodos(
                    paginado.getPageNumber(),
                    paginado.getPageSize(),
                    String.valueOf(paginado.getSort()))
            ).withSelfRel();
        return assembler.toModel(clientesComLinks, findAllLinks);
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

    @Transactional
    public ClienteDTO inativarCliente(Integer id) {
        ClienteDTO dto = buscarPorId(id);

        if(!Objects.equals(dto.getId(), id))
            throw new IllegalArgumentException("Erro ao tentar encontrar cliente para inativar ID: " + id);

        clienteRepository.inativarCliente(id);

        var entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Após inativar, não foi possível resgatar o Cleinte de ID: " + id));

        dto = ClienteMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public Resource exportarPagina(Pageable paginado, String acceptHeader) {
        List<ClienteDTO> dtos = clienteRepository.findAll(paginado)
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();

        try {
            FileExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportFile(dtos);
        } catch (Exception e) {
            throw new RuntimeException("Erro durando exportação do arquivo", e);
        }
    }

    private void adicionaLinksHateoas(ClienteDTO dto) {
        dto.add(linkTo(methodOn(ClienteController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ClienteController.class).buscarTodos(0, 12, "asc")).withRel("buscarTodos").withType("GET"));
        dto.add(linkTo(methodOn(ClienteController.class).criar(dto)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(ClienteController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(ClienteController.class).inativarCliente(dto.getId())).withRel("inativar").withType("PATCH"));
        dto.add(linkTo(methodOn(ClienteController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }
}