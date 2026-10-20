/* Regras de negócio do cadastro (valida dados, criptografa a senha, salva usuário e endereço) e do login (confere e-mail e senha). */

package com.umamifoods.api.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umamifoods.api.dto.CadastroRequest;
import com.umamifoods.api.dto.LoginRequest;
import com.umamifoods.api.dto.LoginResponse;
import com.umamifoods.api.model.Endereco;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.repository.EnderecoRepository;
import com.umamifoods.api.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            EnderecoRepository enderecoRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario cadastrarUsuario(CadastroRequest dados) {

        if (dados.getNome() == null || dados.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        if (dados.getEmail() == null || dados.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }

        String email = dados.getEmail().trim().toLowerCase();

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("O formato do e-mail é inválido.");
        }

        if (dados.getSenha() == null || dados.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }

        if (!dados.getSenha().equals(dados.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não são iguais.");
        }

        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado.");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome().trim());
        usuario.setEmail(email);
        usuario.setTelefone(dados.getTelefone());
        usuario.setSenhaHash(passwordEncoder.encode(dados.getSenha()));
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setAtivo(true);

        usuario = usuarioRepository.save(usuario);

        Endereco endereco = new Endereco();

        endereco.setUsuario(usuario);
        endereco.setCep(dados.getCep());
        endereco.setLogradouro(dados.getLogradouro());
        endereco.setNumero(dados.getNumero());
        endereco.setComplemento(dados.getComplemento());
        endereco.setBairro(dados.getBairro());
        endereco.setCidade(dados.getCidade());
        endereco.setEstado(dados.getEstado());
        endereco.setPrincipal(true);

        enderecoRepository.save(endereco);

        return usuario;
    }

    public LoginResponse autenticarUsuario(LoginRequest dados) {

        if (dados.getEmail() == null || dados.getEmail().trim().isEmpty()
                || dados.getSenha() == null || dados.getSenha().isEmpty()) {
            throw new IllegalArgumentException(
                    "E-mail e senha são obrigatórios.");
        }

        String email = dados.getEmail().trim().toLowerCase();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "E-mail ou senha inválidos."));

        if (!Boolean.TRUE.equals(usuario.getAtivo())
                || !passwordEncoder.matches(
                        dados.getSenha(), usuario.getSenhaHash())) {

            throw new IllegalArgumentException(
                    "E-mail ou senha inválidos.");
        }

        return new LoginResponse(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                "Login realizado com sucesso!"
        );
    }
}
