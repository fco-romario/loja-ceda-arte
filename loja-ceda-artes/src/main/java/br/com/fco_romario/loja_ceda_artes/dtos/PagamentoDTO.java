package br.com.fco_romario.loja_ceda_artes.dtos;

import br.com.fco_romario.loja_ceda_artes.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PagamentoComCartaoDTO.class, name = "pagamentoComCartao"),
        @JsonSubTypes.Type(value = PagamentoComBoletoDTO.class, name = "pagamentoComBoleto")
})
public class PagamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer estado;

    @JsonIgnore
    private PedidoDTO pedido;

    public PagamentoDTO() {}

    public PagamentoDTO(Integer id, EstadoPagamento estado, PedidoDTO pedido) {
        this.id = id;
        this.estado = estado.getCodigo();
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCodigo();
    }

    @JsonIgnore
    public PedidoDTO getPedio() {
        return pedido;
    }

    public void setPedio(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PagamentoDTO pagamento = (PagamentoDTO) o;
        return Objects.equals(getId(), pagamento.getId()) && getEstado() == pagamento.getEstado() && Objects.equals(getPedio(), pagamento.getPedio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEstado(), getPedio());
    }
}
