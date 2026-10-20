/* Disponibiliza os produtos cadastrados para o aplicativo. */

package com.umamifoods.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.ProdutoResponse;
import com.umamifoods.api.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar(
            @RequestParam(required = false) Integer idCategoria) {

        // Diagnóstico: verifica se o parâmetro chegou ao controller.
        System.out.println("Categoria recebida: " + idCategoria);

        return ResponseEntity.ok(produtoService.listar(idCategoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(produtoService.buscar(id));
    }

}