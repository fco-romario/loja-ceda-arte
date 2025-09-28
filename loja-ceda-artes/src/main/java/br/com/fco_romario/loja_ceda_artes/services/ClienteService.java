package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarPorId(Integer id) {
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(
                "Objecto não encontrado id: " + id + ", tipo: " + Cliente.class.getSimpleName()));
    }
}
