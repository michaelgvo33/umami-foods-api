/* Organiza os dados das categorias de produtos. */

package com.umamifoods.api.dto;

public class CategoriaResponse {

    private Integer idCategoria;
    private String nome;
    private String descricao;

    public CategoriaResponse() {
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}