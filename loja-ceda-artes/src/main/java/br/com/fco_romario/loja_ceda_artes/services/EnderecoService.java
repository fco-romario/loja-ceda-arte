package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco buscarPorId(Integer id) {
        Optional<Endereco> obj = enderecoRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(
                "Objecto não encontrado id: " + id + ", tipo: " + Endereco.class.getSimpleName()));
    }
}
