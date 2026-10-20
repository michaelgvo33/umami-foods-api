/* Acesso ao banco para a tabela contatos (CRUD básico: salvar e consultar mensagens). */

package com.umamifoods.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umamifoods.api.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}
