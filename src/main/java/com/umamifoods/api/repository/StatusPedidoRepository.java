/* Acesso ao banco para a tabela status_pedido (CRUD + busca do primeiro status, usado como status inicial do pedido). */

package com.umamifoods.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.StatusPedido;

public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Integer> {

    Optional<StatusPedido> findFirstByOrderByIdStatusAsc();
}
