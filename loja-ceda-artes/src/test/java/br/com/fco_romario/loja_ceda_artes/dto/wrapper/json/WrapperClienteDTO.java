package br.com.fco_romario.loja_ceda_artes.dto.wrapper.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private ClienteEmbeddedDTO embedded;

    public WrapperClienteDTO() {}

    public ClienteEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(ClienteEmbeddedDTO embedded) {
        this.embedded = embedded;
    }
}
