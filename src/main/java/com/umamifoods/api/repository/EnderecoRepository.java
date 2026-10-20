/* Acesso ao banco para a tabela enderecos (CRUD + busca dos endereços de um usuário). */

package com.umamifoods.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByUsuario_IdUsuario(Integer idUsuario);
}
