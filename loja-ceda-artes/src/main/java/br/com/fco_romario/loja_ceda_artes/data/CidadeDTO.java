package br.com.fco_romario.loja_ceda_artes.data;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class CidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private EstadoDTO estado;

    public CidadeDTO() {}

    public CidadeDTO(Integer id, String nome, EstadoDTO estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
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

    public EstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CidadeDTO cidade = (CidadeDTO) o;
        return Objects.equals(getId(), cidade.getId()) && Objects.equals(getNome(), cidade.getNome()) && Objects.equals(getEstado(), cidade.getEstado());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEstado());
    }
}
