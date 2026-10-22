package com.umamifoods.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class CarrinhoResponse {

    private Integer idUsuario;
    private List<CarrinhoItemResponse> itens;
    private BigDecimal total;

    public CarrinhoResponse() {
    }

    public CarrinhoResponse(
            Integer idUsuario,
            List<CarrinhoItemResponse> itens) {

        this.idUsuario = idUsuario;
        this.itens = itens;

        this.total = itens.stream()
                .map(CarrinhoItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<CarrinhoItemResponse> getItens() {
        return itens;
    }

    public void setItens(List<CarrinhoItemResponse> itens) {
        this.itens = itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}