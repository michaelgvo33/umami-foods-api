/* Permite consultar e atualizar os dados do usuário: GET e PUT em /api/perfil/{idUsuario}. */

package com.umamifoods.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umamifoods.api.dto.PerfilRequest;
import com.umamifoods.api.dto.PerfilResponse;
import com.umamifoods.api.service.UsuarioService;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{idUsuario}")
    public PerfilResponse buscar(@PathVariable Integer idUsuario) {
        return usuarioService.buscarPerfil(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public PerfilResponse atualizar(@PathVariable Integer idUsuario,
            @RequestBody PerfilRequest dados) {
        return usuarioService.atualizarPerfil(idUsuario, dados);
    }
}
