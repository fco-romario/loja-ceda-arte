package br.com.fco_romario.loja_ceda_artes.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EstadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    @JsonBackReference
    private List<CidadeDTO> cidades = new ArrayList<>();

    public EstadoDTO() {}

    public EstadoDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public List<CidadeDTO> getCidades() {
        return cidades;
    }

    public void setCidades(List<CidadeDTO> cidades) {
        this.cidades = cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EstadoDTO estado = (EstadoDTO) o;
        return Objects.equals(getId(), estado.getId()) && Objects.equals(getNome(), estado.getNome()) && Objects.equals(getCidades(), estado.getCidades());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCidades());
    }
}
