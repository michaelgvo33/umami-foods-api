/* Operações da conta do usuário: DELETE /api/usuarios/{id} desativa a conta (o login e o cadastro ficam em AuthController, o perfil em PerfilController). */

package com.umamifoods.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> desativar(@PathVariable Integer id) {
        usuarioService.desativar(id);
        return ResponseEntity.ok("Conta desativada com sucesso.");
    }
}
