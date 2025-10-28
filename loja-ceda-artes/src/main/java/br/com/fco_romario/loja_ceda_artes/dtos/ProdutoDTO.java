package br.com.fco_romario.loja_ceda_artes.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.*;

public class ProdutoDTO extends RepresentationModel<ProdutoDTO> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private Double preco;

    //@JsonBackReference
    @JsonIgnore
    private List<CategoriaDTO> categorias = new ArrayList<>();

    @JsonIgnore
    private Set<ItemPedidoDTO> itens = new HashSet<>();

    public ProdutoDTO() {}

    public ProdutoDTO(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    @JsonIgnore
    public List<PedidoDTO> getPedidos() {
        List<PedidoDTO> lista = new ArrayList<>();
        for (ItemPedidoDTO x : itens) {
            lista.add(x.getPedido());
        }
        return lista;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public Set<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDTO produto = (ProdutoDTO) o;
        return Objects.equals(getId(), produto.getId()) && Objects.equals(getNome(), produto.getNome()) && Objects.equals(getPreco(), produto.getPreco()) && Objects.equals(getCategorias(), produto.getCategorias()) && Objects.equals(getItens(), produto.getItens());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
