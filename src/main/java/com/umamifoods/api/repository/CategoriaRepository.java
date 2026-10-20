/* Acesso ao banco para a tabela categorias (CRUD pronto + busca das categorias ativas). */

package com.umamifoods.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByAtivoTrue();
}
