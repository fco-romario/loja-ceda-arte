package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.CidadeDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Cidade;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.CidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO buscarPorId(Integer id) {
        Cidade entity = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + CidadeDTO.class.getSimpleName()));

        return modelMapper.map(entity, CidadeDTO.class);
    }

    public List<CidadeDTO> buscarTodos() {
        return cidadeRepository.findAll()
                .stream()
                .map(cidade -> modelMapper.map(cidade, CidadeDTO.class))
                .collect(Collectors.toList());
    }

    public CidadeDTO criar(CidadeDTO cidadeDTO) {
        Cidade entity = modelMapper.map(cidadeDTO, Cidade.class);
        return modelMapper.map(cidadeRepository.save(entity), CidadeDTO.class);
    }

    public CidadeDTO atualizar(CidadeDTO cidadeDTO) {
        Cidade entity =  modelMapper.map(buscarPorId(cidadeDTO.getId()), Cidade.class);

        entity.setNome(cidadeDTO.getNome());
        return modelMapper.map(cidadeRepository.save(entity), CidadeDTO.class);
    }

    public void deletar(Integer id) {
        Cidade entity =  modelMapper.map(buscarPorId(id), Cidade.class);
        cidadeRepository.delete(entity);
    }
}
