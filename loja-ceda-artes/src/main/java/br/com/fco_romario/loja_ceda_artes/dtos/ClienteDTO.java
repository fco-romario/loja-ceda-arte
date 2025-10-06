package br.com.fco_romario.loja_ceda_artes.dtos;

import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import br.com.fco_romario.loja_ceda_artes.serializer.TipoClienteSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    private String email;
    private String cpfOuCnpj;

    //@JsonSerialize(using = TipoClienteSerializer.class)
    private Integer tipo;
    private Set<String> telefones = new HashSet<>();

    @JsonManagedReference
    private List<EnderecoDTO> enderecos = new ArrayList<>();

    @JsonIgnore
    private List<PedidoDTO> pedidos = new ArrayList<>();

    public ClienteDTO() {}

    public ClienteDTO(Integer id, String nome, LocalDate dataNascimento, String email, String cpfOuCnpj, TipoCliente tipo) {
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
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

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO that = (ClienteDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getDataNascimento(), that.getDataNascimento()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getCpfOuCnpj(), that.getCpfOuCnpj()) && Objects.equals(getTipo(), that.getTipo()) && Objects.equals(getTelefones(), that.getTelefones()) && Objects.equals(getEnderecos(), that.getEnderecos()) && Objects.equals(getPedidos(), that.getPedidos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDataNascimento(), getEmail(), getCpfOuCnpj(), getTipo(), getTelefones(), getEnderecos(), getPedidos());
    }
}
