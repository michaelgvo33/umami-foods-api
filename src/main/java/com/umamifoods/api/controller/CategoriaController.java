/* Endpoints das categorias: listagem e busca por ID. */

package com.umamifoods.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.CategoriaResponse;
import com.umamifoods.api.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Lista as categorias ativas.
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {

        return ResponseEntity.ok(categoriaService.listar());
    }

    // Busca uma categoria pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(categoriaService.buscar(id));
    }
}