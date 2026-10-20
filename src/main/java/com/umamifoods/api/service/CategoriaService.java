/* Regras de negócio das categorias: lista categorias ativas,
   busca por ID e converte para CategoriaResponse. */

package com.umamifoods.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.umamifoods.api.dto.CategoriaResponse;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.Categoria;
import com.umamifoods.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Lista todas as categorias ativas.
    public List<CategoriaResponse> listar() {

        List<CategoriaResponse> lista = new ArrayList<>();

        for (Categoria c : categoriaRepository.findByAtivoTrue()) {
            lista.add(converter(c));
        }

        return lista;
    }

    // Busca uma categoria ativa pelo ID.
    public CategoriaResponse buscar(Integer id) {

        Categoria categoria = categoriaRepository
                .findById(id)
                .filter(Categoria::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada."));

        return converter(categoria);
    }

    // Converte a entidade Categoria em CategoriaResponse.
    private CategoriaResponse converter(Categoria c) {

        CategoriaResponse r = new CategoriaResponse();

        r.setIdCategoria(c.getIdCategoria());
        r.setNome(c.getNome());
        r.setDescricao(c.getDescricao());

        return r;
    }
}