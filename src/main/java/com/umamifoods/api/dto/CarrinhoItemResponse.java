package com.umamifoods.api.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CarrinhoItemResponse {

    private Integer idProduto;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private BigDecimal subtotal;

    public CarrinhoItemResponse() {
    }

    public CarrinhoItemResponse(
            Integer idProduto,
            String nome,
            Double preco,
            Integer quantidade) {

        this.idProduto = idProduto;
        this.nome = nome;
        this.preco = BigDecimal.valueOf(preco).setScale(2, RoundingMode.HALF_UP);
        this.quantidade = quantidade;
        this.subtotal = this.preco.multiply(BigDecimal.valueOf(quantidade))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}