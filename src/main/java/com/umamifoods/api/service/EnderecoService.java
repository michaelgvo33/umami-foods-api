/* Regras de negócio dos endereços: listar os endereços do usuário, cadastrar um novo e atualizar um existente. */

package com.umamifoods.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umamifoods.api.dto.EnderecoRequest;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.Endereco;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.repository.EnderecoRepository;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioService usuarioService;

    public EnderecoService(EnderecoRepository enderecoRepository,
            UsuarioService usuarioService) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioService = usuarioService;
    }

    public List<Endereco> listar(Integer idUsuario) {
        usuarioService.buscarEntidade(idUsuario);
        return enderecoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Transactional
    public Endereco cadastrar(Integer idUsuario, EnderecoRequest dados) {
        Usuario usuario = usuarioService.buscarEntidade(idUsuario);

        Endereco endereco = new Endereco();
        endereco.setUsuario(usuario);
        preencher(endereco, dados);

        // Só pode haver um endereço principal por usuário
        if (Boolean.TRUE.equals(endereco.getPrincipal())) {
            removerPrincipalAntigo(idUsuario);
        }

        return enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco atualizar(Integer idEndereco, EnderecoRequest dados) {
        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço não encontrado."));

        preencher(endereco, dados);

        if (Boolean.TRUE.equals(endereco.getPrincipal())) {
            removerPrincipalAntigo(endereco.getUsuario().getIdUsuario());
        }

        return enderecoRepository.save(endereco);
    }

    private void removerPrincipalAntigo(Integer idUsuario) {
        for (Endereco e : enderecoRepository.findByUsuario_IdUsuario(idUsuario)) {
            e.setPrincipal(false);
            enderecoRepository.save(e);
        }
    }

    private void preencher(Endereco endereco, EnderecoRequest dados) {
        if (dados.getCep() == null || dados.getCep().trim().isEmpty()
                || dados.getLogradouro() == null || dados.getLogradouro().trim().isEmpty()
                || dados.getNumero() == null || dados.getNumero().trim().isEmpty()
                || dados.getCidade() == null || dados.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "CEP, logradouro, número e cidade são obrigatórios.");
        }

        endereco.setCep(dados.getCep());
        endereco.setLogradouro(dados.getLogradouro());
        endereco.setNumero(dados.getNumero());
        endereco.setComplemento(dados.getComplemento());
        endereco.setBairro(dados.getBairro());
        endereco.setCidade(dados.getCidade());
        endereco.setEstado(dados.getEstado());
        endereco.setPrincipal(Boolean.TRUE.equals(dados.getPrincipal()));
    }
}
