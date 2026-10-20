/* Permite consultar e atualizar o endereço do usuário: GET e POST em /api/enderecos/usuario/{idUsuario}, PUT em /api/enderecos/{idEndereco}. */

package com.umamifoods.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.EnderecoRequest;
import com.umamifoods.api.model.Endereco;
import com.umamifoods.api.service.EnderecoService;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Endereco> listar(@PathVariable Integer idUsuario) {
        return enderecoService.listar(idUsuario);
    }

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<Endereco> cadastrar(@PathVariable Integer idUsuario,
            @RequestBody EnderecoRequest dados) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enderecoService.cadastrar(idUsuario, dados));
    }

    @PutMapping("/{idEndereco}")
    public Endereco atualizar(@PathVariable Integer idEndereco,
            @RequestBody EnderecoRequest dados) {
        return enderecoService.atualizar(idEndereco, dados);
    }
}
