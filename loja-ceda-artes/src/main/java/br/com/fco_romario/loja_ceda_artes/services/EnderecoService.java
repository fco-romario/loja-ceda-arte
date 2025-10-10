package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.mapper.ClienteMapper;
import br.com.fco_romario.loja_ceda_artes.mapper.EnderecoMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import br.com.fco_romario.loja_ceda_artes.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteService clienteService;

//    @Autowired
//    private ModelMapper modelMapper;

    public EnderecoDTO buscarPorId(Integer id) {
        Endereco entity = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + EnderecoDTO.class.getSimpleName()));

        return EnderecoMapper.toDTO(entity);
        //return modelMapper.map(entity, EnderecoDTO.class);
    }

    public List<EnderecoDTO> buscarTodos() {
        return enderecoRepository.findAll()
                .stream()
                .map(EnderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO criar(EnderecoDTO enderecoDTO) {
        if(enderecoDTO.getId() != null)
            throw new IllegalArgumentException("O ID do Endereço deve ser nulo na criação");

        if(enderecoDTO.getCliente() == null || enderecoDTO.getCliente().getId() == null)
            throw new IllegalArgumentException("O Cliente é obrigaótrio.");

        Cliente cliente = ClienteMapper.toEntity(clienteService.buscarPorId(enderecoDTO.getCliente().getId()));
        Endereco entity = EnderecoMapper.toEntity(enderecoDTO);
        entity.setCliente(cliente);

        return EnderecoMapper.toDTO((enderecoRepository.save(entity)));
        //return modelMapper.map(enderecoRepository.save(entity), EnderecoDTO.class);
    }

    public EnderecoDTO atualizar(EnderecoDTO enderecoDTO) {
        Endereco entity =   EnderecoMapper.toEntity(buscarPorId(enderecoDTO.getId()));

        entity.setLogradouro(enderecoDTO.getLogradouro());
        entity.setNumero(enderecoDTO.getNumero());
        entity.setComplemento(enderecoDTO.getComplemento());
        entity.setBairro(enderecoDTO.getBairro());
        entity.setCep(enderecoDTO.getCep());
//        entity.setCidade(modelMapper.map(enderecoDTO.getCidade(), Cidade.class));
//        entity.setCliente(modelMapper.map(enderecoDTO.getCliente(), Cliente.class));

        return EnderecoMapper.toDTO(enderecoRepository.save(entity));
    }

    public void deletar(Integer id) {
        Endereco entity =  EnderecoMapper.toEntity(buscarPorId(id));
        enderecoRepository.delete(entity);
    }
}