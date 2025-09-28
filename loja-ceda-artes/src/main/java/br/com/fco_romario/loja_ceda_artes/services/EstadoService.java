package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Estado;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado buscarPorId(Integer id) {
        Optional<Estado> obj = estadoRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(
                "Objecto não encontrado id: " + id + ", tipo: " + Estado.class.getSimpleName()));
    }
}
