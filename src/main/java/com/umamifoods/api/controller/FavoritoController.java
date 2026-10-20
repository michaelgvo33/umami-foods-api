/* Endpoints dos favoritos: GET /api/favoritos/usuario/{id} lista, POST e DELETE em /api/favoritos/usuario/{idUsuario}/produto/{idProduto} adicionam e removem. */

package com.umamifoods.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.ProdutoResponse;
import com.umamifoods.api.service.FavoritoService;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }       

    @GetMapping("/usuario/{idUsuario}")
    public List<ProdutoResponse> listar(@PathVariable Integer idUsuario) {
        return favoritoService.listar(idUsuario);
    }

    @PostMapping("/usuario/{idUsuario}/produto/{idProduto}")
    public ResponseEntity<String> adicionar(@PathVariable Integer idUsuario,
            @PathVariable Integer idProduto) {
        favoritoService.adicionar(idUsuario, idProduto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Produto adicionado aos favoritos.");
    }

    @DeleteMapping("/usuario/{idUsuario}/produto/{idProduto}")
    public ResponseEntity<String> remover(@PathVariable Integer idUsuario,
            @PathVariable Integer idProduto) {
        favoritoService.remover(idUsuario, idProduto);
        return ResponseEntity.ok("Produto removido dos favoritos.");
    }
}
