/* Acesso ao banco para a tabela pedidos (CRUD + busca dos pedidos de um usuário, do mais novo ao mais antigo). */

package com.umamifoods.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuario_IdUsuarioOrderByDataPedidoDesc(Integer idUsuario);
}
