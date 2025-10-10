package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ClienteDTO buscarPorId(Integer id) {
        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + ClienteDTO.class.getSimpleName()));

        return modelMapper.map(entity, ClienteDTO.class);
    }

    public List<ClienteDTO> buscarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
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

        Cliente entity = modelMapper.map(clienteDTO, Cliente.class);
        return modelMapper.map(clienteRepository.save(entity), ClienteDTO.class);
    }

    public ClienteDTO atualizar(ClienteDTO clienteDTO) {
        Cliente entity =  modelMapper.map(buscarPorId(clienteDTO.getId()), Cliente.class);

        entity.setNome(clienteDTO.getNome());
        entity.setEmail(clienteDTO.getEmail());
        entity.setCpfOuCnpj(clienteDTO.getCpfOuCnpj());
        entity.setTipo(clienteDTO.getTipo());

        return modelMapper.map(clienteRepository.save(entity), ClienteDTO.class);
    }

    public void deletar(Integer id) {
        Cliente entity =  modelMapper.map(buscarPorId(id), Cliente.class);
        clienteRepository.delete(entity);
    }
}
