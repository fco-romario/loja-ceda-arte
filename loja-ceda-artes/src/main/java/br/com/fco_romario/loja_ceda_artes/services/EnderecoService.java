package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EnderecoDTO buscarPorId(Integer id) {
        Endereco entity = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + EnderecoDTO.class.getSimpleName()));

        return modelMapper.map(entity, EnderecoDTO.class);
    }

    public List<EnderecoDTO> buscarTodos() {
        return enderecoRepository.findAll()
                .stream()
                .map(endereco -> modelMapper.map(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO criar(EnderecoDTO enderecoDTO) {
        Endereco entity = modelMapper.map(enderecoDTO, Endereco.class);
        return modelMapper.map(enderecoRepository.save(entity), EnderecoDTO.class);
    }

    public EnderecoDTO atualizar(EnderecoDTO enderecoDTO) {
        Endereco entity =  modelMapper.map(buscarPorId(enderecoDTO.getId()), Endereco.class);

        entity.setLogradouro(enderecoDTO.getLogradouro());
        entity.setNumero(enderecoDTO.getNumero());
        entity.setComplemento(enderecoDTO.getComplemento());
        entity.setBairro(enderecoDTO.getBairro());
        entity.setCep(enderecoDTO.getCep());
//        entity.setCidade(modelMapper.map(enderecoDTO.getCidade(), Cidade.class));
//        entity.setCliente(modelMapper.map(enderecoDTO.getCliente(), Cliente.class));

        return modelMapper.map(enderecoRepository.save(entity), EnderecoDTO.class);
    }

    public void deletar(Integer id) {
        Endereco entity =  modelMapper.map(buscarPorId(id), Endereco.class);
        enderecoRepository.delete(entity);
    }
}