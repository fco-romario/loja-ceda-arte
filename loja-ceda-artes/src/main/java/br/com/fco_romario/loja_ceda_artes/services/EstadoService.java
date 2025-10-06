package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.EstadoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.Estado;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.EstadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO buscarPorId(Integer id) {
        Estado entity = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + EstadoDTO.class.getSimpleName()));

        return modelMapper.map(entity, EstadoDTO.class);
    }

    public List<EstadoDTO> buscarTodos() {
        return estadoRepository.findAll()
                .stream()
                .map(cliente -> modelMapper.map(cliente, EstadoDTO.class))
                .collect(Collectors.toList());
    }

    public EstadoDTO criar(EstadoDTO estadoDTO) {
        Estado entity = modelMapper.map(estadoDTO, Estado.class);
        return modelMapper.map(estadoRepository.save(entity), EstadoDTO.class);
    }

    public EstadoDTO atualizar(EstadoDTO estadoDTO) {
        Estado entity =  modelMapper.map(buscarPorId(estadoDTO.getId()), Estado.class);

        entity.setNome(estadoDTO.getNome());
        return modelMapper.map(estadoRepository.save(entity), EstadoDTO.class);
    }

    public void deletar(Integer id) {
        Estado entity =  modelMapper.map(buscarPorId(id), Estado.class);
        estadoRepository.delete(entity);
    }
}

