/* Regras de negócio dos produtos: lista produtos ativos (com filtro opcional por categoria), busca um produto pelo id e converte para ProdutoResponse. */

package com.umamifoods.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.umamifoods.api.dto.ProdutoResponse;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.Produto;
import com.umamifoods.api.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Lista produtos ativos, com filtro opcional por categoria.
    public List<ProdutoResponse> listar(Integer idCategoria) {

        System.out.println(
                "Categoria recebida pelo Service: " + idCategoria);

        List<Produto> produtos;

        if (idCategoria == null) {

            System.out.println(
                    "Executando consulta: todos os produtos ativos");

            produtos = produtoRepository.findByAtivoTrue();

        } else {

            System.out.println(
                    "Executando consulta: produtos da categoria "
                            + idCategoria);

            produtos = produtoRepository
                    .findByCategoria_IdCategoriaAndAtivoTrue(idCategoria);
        }

        System.out.println(
                "Quantidade de produtos encontrados: " + produtos.size());

        List<ProdutoResponse> lista = new ArrayList<>();

        for (Produto p : produtos) {
            lista.add(converter(p));
        }

        return lista;
    }

    // Busca um produto pelo identificador.
    public ProdutoResponse buscar(Integer id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado."));

        return converter(produto);
    }

    // Converte a entidade Produto em ProdutoResponse.
    // Também é utilizado pelo FavoritoService.
    public static ProdutoResponse converter(Produto p) {

        ProdutoResponse r = new ProdutoResponse();

        r.setIdProduto(p.getIdProduto());
        r.setNome(p.getNome());
        r.setDescricao(p.getDescricao());
        r.setPreco(p.getPreco());
        r.setImagem(p.getImagem());
        r.setEstoque(p.getEstoque());
        r.setUnidade(p.getUnidade());

        if (p.getCategoria() != null) {
            r.setIdCategoria(p.getCategoria().getIdCategoria());
            r.setNomeCategoria(p.getCategoria().getNome());
        }

        return r;
    }
}