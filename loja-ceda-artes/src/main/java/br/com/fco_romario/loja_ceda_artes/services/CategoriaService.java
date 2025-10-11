package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.controllers.CategoriaController;
import br.com.fco_romario.loja_ceda_artes.dtos.CategoriaDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Categoria;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaDTO buscarPorId(Integer id) {
        Categoria entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + CategoriaDTO.class.getSimpleName()));

        CategoriaDTO dto = modelMapper.map(entity, CategoriaDTO.class);
        adicionaLinksHateoas(dto);
        return dto;
    }
    public CategoriaDTO buscarPorNome(String nome) {
        Categoria entity = categoriaRepository.findByNome(nome)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado de nome: " + nome + ", tipo: " + CategoriaDTO.class.getSimpleName()));

        CategoriaDTO dto = modelMapper.map(entity, CategoriaDTO.class);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public List<CategoriaDTO> buscarTodos() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> {
                    CategoriaDTO dto = modelMapper.map(categoria, CategoriaDTO.class);
                    adicionaLinksHateoas(dto);
                    return  dto;
                }).toList();
    }

    public CategoriaDTO criar(CategoriaDTO categoriaDTO) {
        if(categoriaDTO.getId() != null)
            throw new IllegalArgumentException("O ID da Categoria deve ser nulo na criação.");


        Categoria entityJaPesistida =  categoriaRepository.findByNome(categoriaDTO.getNome())
                .orElse(null);

        if(entityJaPesistida != null && entityJaPesistida.getNome().equals(categoriaDTO.getNome()))
            throw new IllegalArgumentException("Categoria já cadastrada de nome: " + categoriaDTO.getNome() + ".");

        Categoria entityParaPesistir = modelMapper.map(categoriaDTO, Categoria.class);
        CategoriaDTO dto = modelMapper.map(categoriaRepository.save(entityParaPesistir), CategoriaDTO.class);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public CategoriaDTO atualizar(CategoriaDTO categoriaDTO) {
        Categoria entity =  modelMapper.map(buscarPorId(categoriaDTO.getId()), Categoria.class);

        entity.setNome(categoriaDTO.getNome());

        CategoriaDTO dto = modelMapper.map(categoriaRepository.save(entity), CategoriaDTO.class);
        adicionaLinksHateoas(dto);
        return dto;
    }

    public void deletar(Integer id) {
        Categoria entity =  modelMapper.map(buscarPorId(id), Categoria.class);
        categoriaRepository.delete(entity);
    }

    private void adicionaLinksHateoas(CategoriaDTO dto) {
        dto.add(linkTo(methodOn(CategoriaController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(CategoriaController.class).buscarTodos()).withRel("buscarTodos").withType("GET"));
        dto.add(linkTo(methodOn(CategoriaController.class).criar(dto)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(CategoriaController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(CategoriaController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }
}
