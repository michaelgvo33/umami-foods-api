package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}