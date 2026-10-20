/* Receber mensagens de contato: POST /api/contatos grava a mensagem enviada pelo formulário. */

package com.umamifoods.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.ContatoRequest;
import com.umamifoods.api.service.ContatoService;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @PostMapping
    public ResponseEntity<String> enviar(@RequestBody ContatoRequest dados) {
        contatoService.enviar(dados);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Mensagem enviada com sucesso!");
    }
}
