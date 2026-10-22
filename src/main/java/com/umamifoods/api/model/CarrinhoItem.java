package com.umamifoods.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "carrinho_itens",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_carrinho_usuario_produto",
            columnNames = {"id_usuario", "id_produto"}
        )
    }
)
public class CarrinhoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrinho_item")
    private Integer idCarrinhoItem;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_produto", nullable = false)
    private Integer idProduto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(
        name = "data_adicao",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime dataAdicao;

    public CarrinhoItem() {
    }

    public Integer getIdCarrinhoItem() {
        return idCarrinhoItem;
    }

    public void setIdCarrinhoItem(Integer idCarrinhoItem) {
        this.idCarrinhoItem = idCarrinhoItem;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }
}