/* DTO de entrada: dados que o usuário pode alterar no próprio perfil. */

package com.umamifoods.api.dto;

public class PerfilRequest {

    private String nome;
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
