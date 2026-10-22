package com.umamifoods.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.CarrinhoResponse;
import com.umamifoods.api.service.CarrinhoService;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public CarrinhoResponse listar(@PathVariable Integer idUsuario) {
        return carrinhoService.listar(idUsuario);
    }

    @PostMapping("/usuario/{idUsuario}/produto/{idProduto}")
    public ResponseEntity<String> adicionar(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProduto,
            @RequestBody Map<String, Integer> corpo) {

        Integer quantidade = corpo.get("quantidade");

        carrinhoService.adicionar(idUsuario, idProduto, quantidade);

        return ResponseEntity.ok("Produto adicionado ao carrinho.");
    }

    @PutMapping("/usuario/{idUsuario}/produto/{idProduto}")
    public ResponseEntity<String> atualizarQuantidade(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProduto,
            @RequestBody Map<String, Integer> corpo) {

        Integer quantidade = corpo.get("quantidade");

        carrinhoService.atualizarQuantidade(
                idUsuario, idProduto, quantidade);

        return ResponseEntity.ok("Quantidade atualizada.");
    }

    @DeleteMapping("/usuario/{idUsuario}/produto/{idProduto}")
    public ResponseEntity<String> remover(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProduto) {

        carrinhoService.remover(idUsuario, idProduto);

        return ResponseEntity.ok("Produto removido do carrinho.");
    }

    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<String> limpar(
            @PathVariable Integer idUsuario) {

        carrinhoService.limpar(idUsuario);

        return ResponseEntity.ok("Carrinho esvaziado.");
    }
}