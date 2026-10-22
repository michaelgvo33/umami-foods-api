package com.umamifoods.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umamifoods.api.dto.CarrinhoItemResponse;
import com.umamifoods.api.dto.CarrinhoResponse;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.CarrinhoItem;
import com.umamifoods.api.model.Produto;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.repository.CarrinhoRepository;
import com.umamifoods.api.repository.ProdutoRepository;
import com.umamifoods.api.repository.UsuarioRepository;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public CarrinhoService(
            CarrinhoRepository carrinhoRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository) {

        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Consulta o carrinho de um usuário.
    public CarrinhoResponse listar(Integer idUsuario) {

        validarUsuario(idUsuario);

        List<CarrinhoItem> itens =
                carrinhoRepository.findByIdUsuario(idUsuario);

        List<CarrinhoItemResponse> respostas = new ArrayList<>();

        for (CarrinhoItem item : itens) {

            Produto produto = produtoRepository.findById(item.getIdProduto())
                    .filter(Produto::getAtivo)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Produto não encontrado ou inativo."));

            respostas.add(new CarrinhoItemResponse(
                    produto.getIdProduto(),
                    produto.getNome(),
                    produto.getPreco(),
                    item.getQuantidade()
            ));
        }

        return new CarrinhoResponse(idUsuario, respostas);
    }

    // Adiciona um produto ao carrinho.
    @Transactional
    public void adicionar(
            Integer idUsuario,
            Integer idProduto,
            Integer quantidade) {

        validarUsuario(idUsuario);

        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero.");
        }

        Produto produto = produtoRepository.findById(idProduto)
                .filter(Produto::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado ou inativo."));

        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException(
                    "Quantidade solicitada maior que o estoque disponível.");
        }

        CarrinhoItem item = carrinhoRepository
                .findByIdUsuarioAndIdProduto(idUsuario, idProduto)
                .orElse(null);

        int novaQuantidade = quantidade;

        if (item != null) {
            novaQuantidade += item.getQuantidade();
        } else {
            item = new CarrinhoItem();
            item.setIdUsuario(idUsuario);
            item.setIdProduto(idProduto);
        }

        if (novaQuantidade > produto.getEstoque()) {
            throw new IllegalArgumentException(
                    "Quantidade total no carrinho maior que o estoque disponível.");
        }

        item.setQuantidade(novaQuantidade);
        carrinhoRepository.save(item);
    }

    // Valida se o usuário existe e está ativo.
    private void validarUsuario(Integer idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado."));

        if (!usuario.getAtivo()) {
            throw new IllegalArgumentException(
                    "O usuário está inativo.");
        }
    }
        // Altera a quantidade de um produto no carrinho.
    @Transactional
    public void atualizarQuantidade(
            Integer idUsuario,
            Integer idProduto,
            Integer quantidade) {

        validarUsuario(idUsuario);

        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero.");
        }

        CarrinhoItem item = carrinhoRepository
                .findByIdUsuarioAndIdProduto(idUsuario, idProduto)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado no carrinho."));

        Produto produto = produtoRepository.findById(idProduto)
                .filter(Produto::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado ou inativo."));

        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException(
                    "Quantidade solicitada maior que o estoque disponível.");
        }

        item.setQuantidade(quantidade);
        carrinhoRepository.save(item);
    }

    // Remove um produto específico do carrinho.
    @Transactional
    public void remover(Integer idUsuario, Integer idProduto) {

        validarUsuario(idUsuario);

        CarrinhoItem item = carrinhoRepository
                .findByIdUsuarioAndIdProduto(idUsuario, idProduto)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado no carrinho."));

        carrinhoRepository.delete(item);
    }

    // Remove todos os produtos do carrinho do usuário.
    @Transactional
    public void limpar(Integer idUsuario) {

        validarUsuario(idUsuario);

        carrinhoRepository.deleteByIdUsuario(idUsuario);
    }
}