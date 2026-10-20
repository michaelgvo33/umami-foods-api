/* Acesso ao banco para a tabela produtos (CRUD + busca de produtos ativos, com ou sem filtro de categoria). */

package com.umamifoods.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findByAtivoTrue();

    List<Produto> findByCategoria_IdCategoriaAndAtivoTrue(Integer idCategoria);
}
