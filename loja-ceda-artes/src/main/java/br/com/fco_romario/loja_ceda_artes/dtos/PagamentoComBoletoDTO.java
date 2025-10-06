package br.com.fco_romario.loja_ceda_artes.dtos;

import br.com.fco_romario.loja_ceda_artes.enums.EstadoPagamento;

import java.util.Date;

public class PagamentoComBoletoDTO extends PagamentoDTO {
    private static final long serialVersionUID = 1L;

    private Date dataVencimento;
    private Date dataPagamento;

    public PagamentoComBoletoDTO() {}

    public PagamentoComBoletoDTO(Integer id, EstadoPagamento estado, PedidoDTO pedido, Date dataPagamento, Date dataVencimento) {
        super(id, estado, pedido);
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
