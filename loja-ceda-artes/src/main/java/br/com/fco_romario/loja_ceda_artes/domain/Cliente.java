package br.com.fco_romario.loja_ceda_artes.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 14, nullable = false)
    private String cpfOuCnpj;

    private Integer tipo;

    @ElementCollection
    @CollectionTable(name = "telefones")
    private Set<String> telefones = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Endereco> enderecos = new ArrayList<>();

    public Cliente() {}

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, Integer tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId()) && Objects.equals(getNome(), cliente.getNome()) && Objects.equals(getEmail(), cliente.getEmail()) && Objects.equals(getCpfOuCnpj(), cliente.getCpfOuCnpj()) && Objects.equals(getTipo(), cliente.getTipo()) && Objects.equals(getTelefones(), cliente.getTelefones()) && Objects.equals(getEnderecos(), cliente.getEnderecos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEmail(), getCpfOuCnpj(), getTipo(), getTelefones(), getEnderecos());
    }
}
