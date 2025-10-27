package br.com.fco_romario.loja_ceda_artes.dto.wrapper.json;

import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;

import java.io.Serializable;
import java.util.List;

public class ClienteEmbeddedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ClienteDTO> clientes;

    public ClienteEmbeddedDTO() {}

    public List<ClienteDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }
}
