package br.com.fco_romario.loja_ceda_artes.enums;

public enum EstadoPagamento {
    PEDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private final int codigo;
    private final String descricao;

    EstadoPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo){
        if (codigo == null) return null;

        for (EstadoPagamento x: EstadoPagamento.values()) {
            if(codigo.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
