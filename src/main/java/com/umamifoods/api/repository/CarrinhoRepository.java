package com.umamifoods.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umamifoods.api.model.CarrinhoItem;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoItem, Integer> {

    List<CarrinhoItem> findByIdUsuario(Integer idUsuario);

    Optional<CarrinhoItem> findByIdUsuarioAndIdProduto(
            Integer idUsuario, Integer idProduto);

    void deleteByIdUsuarioAndIdProduto(
            Integer idUsuario, Integer idProduto);

    void deleteByIdUsuario(Integer idUsuario);
}