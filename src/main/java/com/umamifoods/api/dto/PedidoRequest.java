/* DTO de entrada: dados para criar um pedido (usuário, endereço, pagamento e a lista de itens). */

package com.umamifoods.api.dto;

import java.util.List;

public class PedidoRequest {

    private Integer idUsuario;
    private Integer idEndereco;
    private String formaPagamento;
    private String observacao;
    private List<Item> itens;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public static class Item {

        private Integer idProduto;
        private Integer quantidade;

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
    }
}
