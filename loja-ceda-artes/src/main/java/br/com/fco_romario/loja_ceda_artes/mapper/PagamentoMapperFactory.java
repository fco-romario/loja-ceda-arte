package br.com.fco_romario.loja_ceda_artes.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Pagamento;
import br.com.fco_romario.loja_ceda_artes.domain.PagamentoComBoleto;
import br.com.fco_romario.loja_ceda_artes.domain.PagamentoComCartao;
import br.com.fco_romario.loja_ceda_artes.dtos.PagamentoComBoletoDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.PagamentoComCartaoDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.PagamentoDTO;

public class PagamentoMapperFactory {

    public static PagamentoDTO toDTO(Pagamento pagamento) {
        if(pagamento instanceof PagamentoComBoleto boleto) {
            PagamentoComBoletoDTO dto = new PagamentoComBoletoDTO();
            dto.setId(boleto.getId());
            dto.setEstado(boleto.getEstado());
            //dto.setPedio(PedidoMapper.toDTO(boleto.getPedio()));
            dto.setDataVencimento(boleto.getDataVencimento());
            dto.setDataPagamento(boleto.getDataPagamento());

            return dto;
        } else if (pagamento instanceof PagamentoComCartao cartao) {
            PagamentoComCartaoDTO dto = new PagamentoComCartaoDTO();
            dto.setId(cartao.getId());
            dto.setEstado(cartao.getEstado());
            //dto.setPedio(PedidoMapper.toDTO(cartao.getPedio()));
            dto.setNumeroDeParcelas(cartao.getNumeroDeParcelas());

            return dto;
        }

        throw new IllegalArgumentException("Tipo de pagamento desconhecido: " + pagamento.getClass().getSimpleName());
    }

    public static Pagamento toEntity(PagamentoDTO pagamento) {
        if(pagamento instanceof PagamentoComBoletoDTO boleto) {
            PagamentoComBoleto entity = new PagamentoComBoleto();
            entity.setId(boleto.getId());
            entity.setEstado(boleto.getEstado());
            entity.setPedio(PedidoMapper.toEntity(boleto.getPedio()));
            entity.setDataVencimento(boleto.getDataVencimento());
            entity.setDataPagamento(boleto.getDataPagamento());

            return entity;
        } else if (pagamento instanceof PagamentoComCartaoDTO cartao) {
            PagamentoComCartao entity = new PagamentoComCartao();
            entity.setId(cartao.getId());
            entity.setEstado(cartao.getEstado());
            entity.setPedio(PedidoMapper.toEntity(cartao.getPedio()));
            entity.setNumeroDeParcelas(cartao.getNumeroDeParcelas());

            return entity;
        }

        throw new IllegalArgumentException("Tipo de pagamento desconhecido: " + pagamento.getClass().getSimpleName());
    }
}
