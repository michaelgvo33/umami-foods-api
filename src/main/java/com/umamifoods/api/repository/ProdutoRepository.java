package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}