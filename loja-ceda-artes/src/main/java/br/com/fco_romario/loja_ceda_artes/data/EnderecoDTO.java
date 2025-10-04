package br.com.fco_romario.loja_ceda_artes.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private CidadeDTO cidade;

    @JsonBackReference
    private ClienteDTO cliente;

    public EnderecoDTO() {}

    public EnderecoDTO(Integer id, String logradouro, String numero, String complemento, String bairro, String cep, CidadeDTO cidade, ClienteDTO cliente) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public CidadeDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDTO cidade) {
        this.cidade = cidade;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoDTO endereco = (EnderecoDTO) o;
        return Objects.equals(getId(), endereco.getId()) && Objects.equals(getLogradouro(), endereco.getLogradouro()) && Objects.equals(getNumero(), endereco.getNumero()) && Objects.equals(getComplemento(), endereco.getComplemento()) && Objects.equals(getBairro(), endereco.getBairro()) && Objects.equals(getCep(), endereco.getCep()) && Objects.equals(getCidade(), endereco.getCidade()) && Objects.equals(getCliente(), endereco.getCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogradouro(), getNumero(), getComplemento(), getBairro(), getCep(), getCidade(), getCliente());
    }
}
