package br.com.fco_romario.loja_ceda_artes.domain;

import br.com.fco_romario.loja_ceda_artes.enums.EstadoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento_cartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    @Column(name = "numero_parcelas", nullable = false)
    private Integer numeroDeParcelas;

    public PagamentoComCartao() {}

    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }

}
