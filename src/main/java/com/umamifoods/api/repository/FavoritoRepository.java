/* Acesso ao banco para a tabela favoritos (CRUD + busca por usuário e por usuário/produto). */

package com.umamifoods.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    List<Favorito> findByUsuario_IdUsuario(Integer idUsuario);

    Optional<Favorito> findByUsuario_IdUsuarioAndProduto_IdProduto(Integer idUsuario, Integer idProduto);
}
