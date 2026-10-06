package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

}