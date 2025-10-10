package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.PedidoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.domain.Pedido;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.mapper.PedidoMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository cedidoRepository;

//    @Autowired
//    private ModelMapper modelMapper;

    public PedidoDTO buscarPorId(Integer id) {
        Pedido entity = cedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + PedidoDTO.class.getSimpleName()));

        return PedidoMapper.toDTO(entity);
        //return modelMapper.map(entity, PedidoDTO.class);
    }

    public List<PedidoDTO> buscarTodos() {
        return cedidoRepository.findAll()
                .stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO criar(PedidoDTO pedidoDTO) {
        Pedido entity = PedidoMapper.toEntity(pedidoDTO);
        return PedidoMapper.toDTO(cedidoRepository.save(entity));
    }

    public PedidoDTO atualizar(PedidoDTO pedidoDTO) {
        Pedido entity =  PedidoMapper.toEntity(buscarPorId(pedidoDTO.getId()));
        entity.setInstante(pedidoDTO.getInstante());

        //entity.setPagamento(modelMapper.map(pedidoDTO.getPagamento(), Pagamento.class));
        //entity.setCliente(modelMapper.map(pedidoDTO.getCliente(), Cliente.class));
        //entity.setEnderecoDeEntrega(modelMapper.map(pedidoDTO.getEnderecoDeEntrega(), Endereco.class));

        return PedidoMapper.toDTO(cedidoRepository.save(entity));
    }

    public void deletar(Integer id) {
        Pedido entity =  PedidoMapper.toEntity(buscarPorId(id));
        cedidoRepository.delete(entity);
    }
}
