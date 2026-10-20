/* Regras de negócio dos favoritos: listar os produtos favoritos do usuário, favoritar e remover dos favoritos. */

package com.umamifoods.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.umamifoods.api.dto.ProdutoResponse;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.Favorito;
import com.umamifoods.api.model.Produto;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.repository.FavoritoRepository;
import com.umamifoods.api.repository.ProdutoRepository;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioService usuarioService;

    public FavoritoService(FavoritoRepository favoritoRepository,
            ProdutoRepository produtoRepository,
            UsuarioService usuarioService) {
        this.favoritoRepository = favoritoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioService = usuarioService;
    }

    public List<ProdutoResponse> listar(Integer idUsuario) {
        usuarioService.buscarEntidade(idUsuario);

        List<ProdutoResponse> lista = new ArrayList<>();
        for (Favorito f : favoritoRepository.findByUsuario_IdUsuario(idUsuario)) {
            lista.add(ProdutoService.converter(f.getProduto()));
        }
        return lista;
    }

    public void adicionar(Integer idUsuario, Integer idProduto) {
        Usuario usuario = usuarioService.buscarEntidade(idUsuario);
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado."));

        // Se já é favorito, não faz nada (evita duplicar)
        if (favoritoRepository
                .findByUsuario_IdUsuarioAndProduto_IdProduto(idUsuario, idProduto)
                .isPresent()) {
            return;
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProduto(produto);
        favorito.setDataAdicao(LocalDateTime.now());
        favoritoRepository.save(favorito);
    }

    public void remover(Integer idUsuario, Integer idProduto) {
        Favorito favorito = favoritoRepository
                .findByUsuario_IdUsuarioAndProduto_IdProduto(idUsuario, idProduto)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Favorito não encontrado."));
        favoritoRepository.delete(favorito);
    }
}
