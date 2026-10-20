/* Acesso ao banco para a tabela historico_status (CRUD + histórico de um pedido em ordem de data). */

package com.umamifoods.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.HistoricoStatus;

public interface HistoricoStatusRepository extends JpaRepository<HistoricoStatus, Integer> {

    List<HistoricoStatus> findByPedido_IdPedidoOrderByDataAlteracaoAsc(Integer idPedido);
}
