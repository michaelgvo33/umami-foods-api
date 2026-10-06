package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}