package br.com.fco_romario.loja_ceda_artes.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class ItemPedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private ItemPedidoPKDTO id = new ItemPedidoPKDTO();//classe composta de acossiciação

    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedidoDTO() {}

    public ItemPedidoDTO(PedidoDTO pedido, ProdutoDTO produto, Double desconto, Integer quantidade, Double preco) {
        this.id.setPedido(pedido);
        this.id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @JsonIgnore
    public PedidoDTO getPedido() {
        return this.id.getPedido();
    }

    public ProdutoDTO getProduto() {
        return this.id.getProduto();
    }

    public ItemPedidoPKDTO getId() {
        return id;
    }

    public void setId(ItemPedidoPKDTO id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoDTO that = (ItemPedidoDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDesconto(), that.getDesconto()) && Objects.equals(getQuantidade(), that.getQuantidade()) && Objects.equals(getPreco(), that.getPreco());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDesconto(), getQuantidade(), getPreco());
    }
}
