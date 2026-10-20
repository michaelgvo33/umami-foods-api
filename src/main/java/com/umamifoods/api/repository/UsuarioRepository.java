/* Acesso ao banco para a tabela usuarios (CRUD + busca por e-mail, usada no cadastro e no login). */

package com.umamifoods.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
