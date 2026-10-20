/* DTO de saída: dados do perfil do usuário (nunca inclui a senha). */

package com.umamifoods.api.dto;

public class PerfilResponse {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private java.time.LocalDateTime dataCadastro;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public java.time.LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(java.time.LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
