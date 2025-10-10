package br.com.fco_romario.loja_ceda_artes.domain;

import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 14, nullable = false)
    private String cpfOuCnpj;

    @Column(nullable = false)
    private Integer tipo;

    @ElementCollection
    @CollectionTable(name = "telefones")
    private Set<String> telefones = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {}

    public Cliente(Integer id, String nome, LocalDate dataNascimento, String email, String cpfOuCnpj, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo.getCodigo();
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

    public LocalDate  getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate  dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCodigo();
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId()) && Objects.equals(getNome(), cliente.getNome()) && Objects.equals(getDataNascimento(), cliente.getDataNascimento()) && Objects.equals(getEmail(), cliente.getEmail()) && Objects.equals(getCpfOuCnpj(), cliente.getCpfOuCnpj()) && Objects.equals(getTipo(), cliente.getTipo()) && Objects.equals(getTelefones(), cliente.getTelefones()) && Objects.equals(getEnderecos(), cliente.getEnderecos()) && Objects.equals(getPedidos(), cliente.getPedidos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDataNascimento(), getEmail(), getCpfOuCnpj(), getTipo(), getTelefones(), getEnderecos(), getPedidos());
    }
}
