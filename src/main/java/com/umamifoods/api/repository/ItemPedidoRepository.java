/* Acesso ao banco para a tabela itens_pedido (CRUD + busca dos itens de um pedido). */

package com.umamifoods.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findByPedido_IdPedido(Integer idPedido);
}
