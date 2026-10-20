/* Endpoints de autenticação: POST /api/auth/cadastro (criar conta) e POST /api/auth/login (entrar). */

package com.umamifoods.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.CadastroRequest;
import com.umamifoods.api.dto.LoginRequest;
import com.umamifoods.api.dto.LoginResponse;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(
            @RequestBody CadastroRequest dados) {

        Usuario usuario = authService.cadastrarUsuario(dados);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso! ID: "
                        + usuario.getIdUsuario());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest dados) {

        LoginResponse resposta = authService.autenticarUsuario(dados);

        return ResponseEntity.ok(resposta);
    }
}
