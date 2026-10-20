/* Captura as exceções lançadas pelos services e devolve ao cliente uma resposta JSON com o campo "erro" e o status HTTP certo (400, 401 ou 404). */

package com.umamifoods.api.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarErro(
            IllegalArgumentException erro) {

        String mensagem = erro.getMessage();

        HttpStatus status;

        if ("E-mail ou senha inválidos.".equals(mensagem)) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status)
                .body(Map.of("erro", mensagem));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarNaoEncontrado(
            RecursoNaoEncontradoException erro) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", erro.getMessage()));
    }
}
