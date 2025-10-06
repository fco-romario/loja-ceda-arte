package br.com.fco_romario.loja_ceda_artes.dtos;

import java.io.Serializable;
import java.util.Objects;

public class ItemPedidoPKDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private PedidoDTO pedido;
    private ProdutoDTO produto;

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoPKDTO that = (ItemPedidoPKDTO) o;
        return Objects.equals(getPedido(), that.getPedido()) && Objects.equals(getProduto(), that.getProduto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPedido(), getProduto());
    }
}
