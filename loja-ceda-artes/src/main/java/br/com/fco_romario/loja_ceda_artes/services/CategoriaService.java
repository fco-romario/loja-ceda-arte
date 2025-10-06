package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.CategoriaDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Categoria;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return modelMapper.map(entity, CategoriaDTO.class);
    }

    public List<CategoriaDTO> buscarTodos() {
        return categoriaRepository.findAll()
                .stream()
                .map(cliente -> modelMapper.map(cliente, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    public CategoriaDTO criar(CategoriaDTO categoriaDTO) {
        Categoria entity = modelMapper.map(categoriaDTO, Categoria.class);
        return modelMapper.map(categoriaRepository.save(entity), CategoriaDTO.class);
    }

    public CategoriaDTO atualizar(CategoriaDTO categoriaDTO) {
        Categoria entity =  modelMapper.map(buscarPorId(categoriaDTO.getId()), Categoria.class);

        entity.setNome(categoriaDTO.getNome());
        return modelMapper.map(categoriaRepository.save(entity), CategoriaDTO.class);
    }

    public void deletar(Integer id) {
        Categoria entity =  modelMapper.map(buscarPorId(id), Categoria.class);
        categoriaRepository.delete(entity);
    }
}
