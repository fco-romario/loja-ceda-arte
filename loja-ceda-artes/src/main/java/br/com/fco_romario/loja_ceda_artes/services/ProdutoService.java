package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.controllers.ProdutoController;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.exception.BadRequestException;
import br.com.fco_romario.loja_ceda_artes.exception.FileStorageException;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.file.importer.contract.FileImporter;
import br.com.fco_romario.loja_ceda_artes.file.importer.factory.FileImporterFactory;
import br.com.fco_romario.loja_ceda_artes.mapper.ProdutoMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FileImporterFactory importerFactory;

//    @Autowired
//    private ModelMapper modelMapper;

    public ProdutoDTO buscarPorId(Integer id) {
        Produto entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + ProdutoDTO.class.getSimpleName()));

        ProdutoDTO dto = ProdutoMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(entity -> {
                    ProdutoDTO dto = ProdutoMapper.toDTO(entity);
                    adicionaLinksHateoas(dto);
                    return dto;
                })
                .toList();
    }

    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Produto entity = ProdutoMapper.toEntity(produtoDTO);

        ProdutoDTO dto = ProdutoMapper.toDTO(produtoRepository.save(entity));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public ProdutoDTO atualizar(ProdutoDTO produtoDTO) {
        if(produtoDTO.getId() == null)
            throw new IllegalArgumentException("É necessáiro que o produto tenha um ID para atulizar.");

        Produto entity =  ProdutoMapper.toEntity(buscarPorId(produtoDTO.getId()));

        entity.setNome(produtoDTO.getNome());
        entity.setPreco(produtoDTO.getPreco());
//        entity.getCategorias().addAll(
//                Arrays.asList(modelMapper.map(produtoDTO.getCategorias(), Categoria.class))
//        );

        ProdutoDTO dto = ProdutoMapper.toDTO(produtoRepository.save(entity));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public void deletar(Integer id) {
        Produto entity =  ProdutoMapper.toEntity(buscarPorId(id));
        produtoRepository.delete(entity);
    }

    public List<ProdutoDTO> criacaoMassiva(MultipartFile file) {
        if(file.isEmpty())
            throw new BadRequestException("Impossível salvar Arquivo vasio. Envie um arquivo válido!");

        try(InputStream inputStream = file.getInputStream()) {
            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("Nome do arquivo não pode ser nulo!"));

            FileImporter importer = this.importerFactory.getImporter(fileName);

            List<Produto> entities =  importer.importFile(inputStream)
                    .stream()
                    .map(dto -> produtoRepository.save(ProdutoMapper.toEntity(dto)))
                    .toList();

            return entities
                    .stream()
                    .map(entity -> {
                        var dto = ProdutoMapper.toDTO(entity);
                        adicionaLinksHateoas(dto);
                        return dto;
                    }).toList();

        } catch (Exception e) {
            throw new FileStorageException("Erro ao processar o arquivo!");
        }
    }

    private void adicionaLinksHateoas(ProdutoDTO dto) {
        dto.add(linkTo(methodOn(ProdutoController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        //dto.add(linkTo(methodOn(ProdutoController.class).buscarTodos(0, 12, "asc")).withRel("buscarTodos").withType("GET"));
        dto.add(linkTo(methodOn(ProdutoController.class).criar(dto)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(ProdutoController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
        dto.add(linkTo(methodOn(ProdutoController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        //dto.add(linkTo(methodOn(ProdutoController.class).inativarCliente(dto.getId())).withRel("inativar").withType("PATCH"));
        dto.add(linkTo(methodOn(ProdutoController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }
}
