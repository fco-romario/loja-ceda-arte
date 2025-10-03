package br.com.fco_romario.loja_ceda_artes.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "enderecos")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String logradouro;

    @Column(length = 10, nullable = false)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(length = 100)
    private String bairro;

    @Column(length = 8, nullable = false)
    private String cep;

    @ManyToOne()
    @JoinColumn(name = "cidade_fk")
    private Cidade cidade;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "cliente_fk")
    private Cliente cliente;

    public Endereco() {}

    public Endereco(Integer id, String logradouro, String numero, String complemento, String bairro, String cep, Cidade cidade, Cliente cliente) {
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

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(getId(), endereco.getId()) && Objects.equals(getLogradouro(), endereco.getLogradouro()) && Objects.equals(getNumero(), endereco.getNumero()) && Objects.equals(getComplemento(), endereco.getComplemento()) && Objects.equals(getBairro(), endereco.getBairro()) && Objects.equals(getCep(), endereco.getCep()) && Objects.equals(getCidade(), endereco.getCidade()) && Objects.equals(getCliente(), endereco.getCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogradouro(), getNumero(), getComplemento(), getBairro(), getCep(), getCidade(), getCliente());
    }
}
