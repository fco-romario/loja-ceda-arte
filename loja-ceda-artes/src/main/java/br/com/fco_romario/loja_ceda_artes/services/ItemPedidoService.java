package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.ItemPedidoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.ItemPedido;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.repositories.ItemPedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoDTO buscarPorPedidoIdEProdutoId(Integer idPedido, Integer idProduto) {
        ItemPedido entity = itemPedidoRepository.findByIdPedidoIdAndIdProdutoId(idPedido, idProduto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado Pedido id: " + idPedido + ", Produto id: "+ idProduto  + ", tipo: " + ItemPedidoDTO.class.getSimpleName()));

        return modelMapper.map(entity, ItemPedidoDTO.class);
    }

    public List<ItemPedidoDTO> buscarTodos() {
        return itemPedidoRepository.findAll()
                .stream()
                .map(itemPedido -> modelMapper.map(itemPedido, ItemPedidoDTO.class))
                .collect(Collectors.toList());
    }

    public ItemPedidoDTO criar(ItemPedidoDTO itemPedidoDTO) {
        ItemPedido entity = modelMapper.map(itemPedidoDTO, ItemPedido.class);
        return modelMapper.map(itemPedidoRepository.save(entity), ItemPedidoDTO.class);
    }

    public ItemPedidoDTO atualizar(ItemPedidoDTO itemPedidoDTO) {
        ItemPedido entity =  modelMapper.map(buscarPorPedidoIdEProdutoId(
                itemPedidoDTO.getId().getPedido().getId(),
                itemPedidoDTO.getId().getProduto().getId()),
            ItemPedido.class);

        entity.setDesconto(itemPedidoDTO.getDesconto());
        entity.setQuantidade(itemPedidoDTO.getQuantidade());
        entity.setPreco(itemPedidoDTO.getPreco());

        return modelMapper.map(itemPedidoRepository.save(entity), ItemPedidoDTO.class);
    }

    public void deletar(Integer idPedido, Integer idProduto) {
        ItemPedido entity =  modelMapper.map(buscarPorPedidoIdEProdutoId(idPedido, idProduto), ItemPedido.class);
        itemPedidoRepository.delete(entity);
    }
}
