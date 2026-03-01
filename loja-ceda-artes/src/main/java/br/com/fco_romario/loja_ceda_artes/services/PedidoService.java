package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.controllers.PedidoController;
import br.com.fco_romario.loja_ceda_artes.dtos.PedidoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.domain.Pedido;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.mapper.*;
import br.com.fco_romario.loja_ceda_artes.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EnderecoService enderecoService;

//    @Autowired
//    private ModelMapper modelMapper;

    public PedidoDTO buscarPorId(Integer id) {
        Pedido entity = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + PedidoDTO.class.getSimpleName()));

        PedidoDTO dto = PedidoMapper.toDTO(entity);
        adicionaLinksHateoas(dto);
        return dto;
        //return modelMapper.map(entity, PedidoDTO.class);
    }

    public List<PedidoDTO> buscarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map( pedido -> {
                    PedidoDTO dto = PedidoMapper.toDTO(pedido);
                    adicionaLinksHateoas(dto);
                    return dto;
                }).toList();
    }

    //todo: testar
    public PedidoDTO criar(PedidoDTO pedidoDTO) {
        if(pedidoDTO.getId() != null)
            throw new IllegalArgumentException("O ID do pedido deve ser nulo na criação.");

        if(pedidoDTO.getPagamento().getId() != null)
            throw new IllegalArgumentException("O ID do pagamento deve ser nulo na criação.");

        if(pedidoDTO.getPagamento() == null)
            throw new IllegalArgumentException("É necessário um pagamento para fazer o pedido.");

        if(pedidoDTO.getItens().isEmpty())
            throw new IllegalArgumentException("É necessário ao menos um item para fazer o pedido.");


        Cliente cliente = ClienteMapper.toEntity(clienteService.buscarPorId(pedidoDTO.getCliente().getId()));
        Endereco endereco = EnderecoMapper.toEntity(enderecoService.buscarPorId(pedidoDTO.getEnderecoDeEntrega().getId()));
        Pagamento pagamento = PagamentoMapperFactory.toEntity(pedidoDTO.getPagamento());
        Set<ItemPedido> itemPedidoList = pedidoDTO.getItens()
                .stream()
                .map(ItemPedidoMapper::toEntity)
                .collect(Collectors.toSet());
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO);

        pedido.setCliente(cliente);
        pedido.setEnderecoDeEntrega(endereco);
        pedido.setPagamento(pagamento);
        pagamento.setPedio(pedido);
        pedido.setPagamento(pagamento);
        itemPedidoList.forEach(itemPedido -> {
            itemPedido.getId().setPedido(pedido);
        });
        pedido.setItens(itemPedidoList);

        PedidoDTO dto = PedidoMapper.toDTO(pedidoRepository.save(pedido));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public PedidoDTO atualizar(PedidoDTO pedidoDTO) {
        if(pedidoDTO.getId() == null)
            throw new IllegalArgumentException("O ID do pedido não deve ser nulo na atualização.");

        Pedido entity =  PedidoMapper.toEntity(buscarPorId(pedidoDTO.getId()));

        Cliente cliente = ClienteMapper.toEntity(clienteService.buscarPorId(pedidoDTO.getCliente().getId()));
        Endereco endereco = EnderecoMapper.toEntity(enderecoService.buscarPorId(pedidoDTO.getEnderecoDeEntrega().getId()));
        Pagamento pagamento = PagamentoMapperFactory.toEntity(pedidoDTO.getPagamento());
        Set<ItemPedido> itemPedidoList = pedidoDTO.getItens()
                .stream()
                .map(ItemPedidoMapper::toEntity)
                .collect(Collectors.toSet());

        entity.setCliente(cliente);
        entity.setEnderecoDeEntrega(endereco);
        entity.setPagamento(pagamento);
        pagamento.setPedio(entity);
        entity.setPagamento(pagamento);
        itemPedidoList.forEach(itemPedido -> {
            itemPedido.getId().setPedido(entity);
        });
        entity.setItens(itemPedidoList);

        PedidoDTO dto = PedidoMapper.toDTO(pedidoRepository.save(entity));
        adicionaLinksHateoas(dto);
        return dto;
    }

    public void deletar(Integer id) {
        Pedido entity =  PedidoMapper.toEntity(buscarPorId(id));
        pedidoRepository.delete(entity);
    }

    private void adicionaLinksHateoas(PedidoDTO dto) {
        dto.add(linkTo(methodOn(PedidoController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PedidoController.class).buscarTodos()).withRel("buscarTodos").withType("GET"));
        dto.add(linkTo(methodOn(PedidoController.class).criar(dto)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(PedidoController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(PedidoController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }
}
