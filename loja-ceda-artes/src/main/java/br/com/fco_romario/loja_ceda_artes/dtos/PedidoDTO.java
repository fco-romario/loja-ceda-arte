package br.com.fco_romario.loja_ceda_artes.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date instante;
    private PagamentoDTO pagamento;
    private ClienteDTO cliente;
    private EnderecoDTO enderecoDeEntrega;
    private Set<ItemPedidoDTO> itens = new HashSet<>();

    public PedidoDTO() {}

    public PedidoDTO(Integer id, Date instante, PagamentoDTO pagamento, ClienteDTO cliente, EnderecoDTO enderecoDeEntrega) {
        this.id = id;
        this.instante = instante;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public PedidoDTO(Integer id, Date instante, ClienteDTO cliente, EnderecoDTO enderecoDeEntrega) {
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public PagamentoDTO getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoDTO pagamento) {
        this.pagamento = pagamento;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public EnderecoDTO getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(EnderecoDTO enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Set<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PedidoDTO pedido = (PedidoDTO) o;
        return Objects.equals(getId(), pedido.getId()) && Objects.equals(getInstante(), pedido.getInstante()) && Objects.equals(getPagamento(), pedido.getPagamento()) && Objects.equals(getCliente(), pedido.getCliente()) && Objects.equals(getEnderecoDeEntrega(), pedido.getEnderecoDeEntrega()) && Objects.equals(getItens(), pedido.getItens());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
