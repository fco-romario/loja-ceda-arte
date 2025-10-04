package br.com.fco_romario.loja_ceda_artes.data;

import br.com.fco_romario.loja_ceda_artes.enums.EstadoPagamento;

public class PagamentoComCartaoDTO extends PagamentoDTO {
    private static final long serialVersionUID = 1L;

    private Integer numeroDeParcelas;

    public PagamentoComCartaoDTO() {}

    public PagamentoComCartaoDTO(Integer id, EstadoPagamento estado, PedidoDTO pedido, Integer numeroDeParcelas) {
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
