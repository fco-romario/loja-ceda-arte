package br.com.fco_romario.loja_ceda_artes.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class ItemPedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //@JsonIgnore
    //private ItemPedidoPKDTO id = new ItemPedidoPKDTO();//classe composta de acossiciação
    private ProdutoDTO produto;
    private PedidoDTO pedido;
    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedidoDTO() {}

//    public ItemPedidoDTO(PedidoDTO pedido, ProdutoDTO produto, Double desconto, Integer quantidade, Double preco) {
//        this.id.setPedido(pedido);
//        this.id.setProduto(produto);
//        this.desconto = desconto;
//        this.quantidade = quantidade;
//        this.preco = preco;
//    }

    public ItemPedidoDTO(ProdutoDTO produto, Double desconto, Integer quantidade, Double preco) {
        this.produto = produto;
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
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
        return Objects.equals(getProduto(), that.getProduto()) && Objects.equals(getPedido(), that.getPedido()) && Objects.equals(getDesconto(), that.getDesconto()) && Objects.equals(getQuantidade(), that.getQuantidade()) && Objects.equals(getPreco(), that.getPreco());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduto(), getPedido(), getDesconto(), getQuantidade(), getPreco());
    }
}
