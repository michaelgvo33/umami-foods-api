/* Cria pedidos e permite consultar os pedidos realizados: POST /api/pedidos, GET /api/pedidos/{id} e GET /api/pedidos/usuario/{idUsuario}. */

package com.umamifoods.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.PedidoRequest;
import com.umamifoods.api.dto.PedidoResponse;
import com.umamifoods.api.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody PedidoRequest dados) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.criar(dados));
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Integer id) {
        return pedidoService.buscar(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<PedidoResponse> listarPorUsuario(@PathVariable Integer idUsuario) {
        return pedidoService.listarPorUsuario(idUsuario);
    }
}
