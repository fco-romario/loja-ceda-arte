package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Produto;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository ProdutoRepository;

    public Produto buscarPorId(Integer id) {
        Optional<Produto> obj = ProdutoRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(
                "Objecto não encontrado id: " + id + ", tipo: " + Produto.class.getSimpleName()));
    }
}
