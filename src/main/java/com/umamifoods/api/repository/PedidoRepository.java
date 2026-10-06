package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}