package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Cidade;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository produtoRepository;

    public Cidade buscarPorId(Integer id) {
        Optional<Cidade> obj = cidadeRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(
                "Objecto não encontrado id: " + id + ", tipo: " + Cidade.class.getSimpleName()));
    }
}
