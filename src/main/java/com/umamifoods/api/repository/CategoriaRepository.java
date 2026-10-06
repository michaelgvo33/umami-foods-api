package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}