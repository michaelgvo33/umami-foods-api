/* Regras de negócio do contato: valida a mensagem enviada pelo formulário e salva no banco. */

package com.umamifoods.api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.umamifoods.api.dto.ContatoRequest;
import com.umamifoods.api.model.Contato;
import com.umamifoods.api.repository.ContatoRepository;
import com.umamifoods.api.repository.UsuarioRepository;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final UsuarioRepository usuarioRepository;

    public ContatoService(ContatoRepository contatoRepository,
            UsuarioRepository usuarioRepository) {
        this.contatoRepository = contatoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Contato enviar(ContatoRequest dados) {
        if (dados.getNome() == null || dados.getNome().trim().isEmpty()
                || dados.getEmail() == null || dados.getEmail().trim().isEmpty()
                || dados.getMensagem() == null || dados.getMensagem().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Nome, e-mail e mensagem são obrigatórios.");
        }

        Contato contato = new Contato();
        contato.setNome(dados.getNome().trim());
        contato.setEmail(dados.getEmail().trim());
        contato.setAssunto(dados.getAssunto());
        contato.setMensagem(dados.getMensagem().trim());
        contato.setDataEnvio(LocalDateTime.now());
        contato.setStatus("Recebido");

        // Usuário é opcional: visitante sem login também pode enviar contato
        if (dados.getIdUsuario() != null) {
            contato.setUsuario(
                    usuarioRepository.findById(dados.getIdUsuario()).orElse(null));
        }

        return contatoRepository.save(contato);
    }
}
