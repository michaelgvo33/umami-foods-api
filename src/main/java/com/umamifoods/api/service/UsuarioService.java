/* Regras de negócio do usuário: buscar usuário, consultar/atualizar o perfil (nome e telefone) e desativar a conta. */

package com.umamifoods.api.service;

import org.springframework.stereotype.Service;

import com.umamifoods.api.dto.PerfilRequest;
import com.umamifoods.api.dto.PerfilResponse;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Usado também pelos outros services para validar se o usuário existe
    public Usuario buscarEntidade(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("O idUsuario é obrigatório.");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado."));
        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }
        return usuario;
    }

    public PerfilResponse buscarPerfil(Integer idUsuario) {
        return converter(buscarEntidade(idUsuario));
    }

    public PerfilResponse atualizarPerfil(Integer idUsuario, PerfilRequest dados) {
        Usuario usuario = buscarEntidade(idUsuario);

        if (dados.getNome() == null || dados.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        usuario.setNome(dados.getNome().trim());
        usuario.setTelefone(dados.getTelefone());

        return converter(usuarioRepository.save(usuario));
    }

    // Não apaga do banco: apenas marca como inativo (o login deixa de funcionar)
    public void desativar(Integer idUsuario) {
        Usuario usuario = buscarEntidade(idUsuario);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    private PerfilResponse converter(Usuario u) {
        PerfilResponse r = new PerfilResponse();
        r.setIdUsuario(u.getIdUsuario());
        r.setNome(u.getNome());
        r.setEmail(u.getEmail());
        r.setTelefone(u.getTelefone());
        r.setDataCadastro(u.getDataCadastro());
        return r;
    }
}
