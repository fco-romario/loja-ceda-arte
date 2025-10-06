package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO buscarPorId(Integer id) {
        Produto entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + ProdutoDTO.class.getSimpleName()));

        return modelMapper.map(entity, ProdutoDTO.class);
    }

    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Produto entity = modelMapper.map(produtoDTO, Produto.class);
        return modelMapper.map(produtoRepository.save(entity), ProdutoDTO.class);
    }

    public ProdutoDTO atualizar(ProdutoDTO produtoDTO) {
        Produto entity =  modelMapper.map(buscarPorId(produtoDTO.getId()), Produto.class);

        entity.setNome(produtoDTO.getNome());
        entity.setPreco(produtoDTO.getPreco());
        entity.getCategorias().addAll(
                Arrays.asList(modelMapper.map(produtoDTO.getCategorias(), Categoria.class))
        );

        return modelMapper.map(produtoRepository.save(entity), ProdutoDTO.class);
    }

    public void deletar(Integer id) {
        Produto entity =  modelMapper.map(buscarPorId(id), Produto.class);
        produtoRepository.delete(entity);
    }
}
