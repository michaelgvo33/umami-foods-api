/* Regras de negócio dos pedidos: cria o pedido (valida itens, calcula valores, baixa o estoque, grava histórico) e consulta pedidos do usuário. */

package com.umamifoods.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umamifoods.api.dto.PedidoRequest;
import com.umamifoods.api.dto.PedidoResponse;
import com.umamifoods.api.exception.RecursoNaoEncontradoException;
import com.umamifoods.api.model.Endereco;
import com.umamifoods.api.model.HistoricoStatus;
import com.umamifoods.api.model.ItemPedido;
import com.umamifoods.api.model.Pedido;
import com.umamifoods.api.model.Produto;
import com.umamifoods.api.model.StatusPedido;
import com.umamifoods.api.model.Usuario;
import com.umamifoods.api.repository.EnderecoRepository;
import com.umamifoods.api.repository.HistoricoStatusRepository;
import com.umamifoods.api.repository.ItemPedidoRepository;
import com.umamifoods.api.repository.PedidoRepository;
import com.umamifoods.api.repository.ProdutoRepository;
import com.umamifoods.api.repository.StatusPedidoRepository;

@Service
public class PedidoService {

    // Frete fixo para simplificar o trabalho
    private static final double FRETE = 10.0;

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EnderecoRepository enderecoRepository;
    private final StatusPedidoRepository statusPedidoRepository;
    private final HistoricoStatusRepository historicoRepository;
    private final UsuarioService usuarioService;

    public PedidoService(PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository,
            ProdutoRepository produtoRepository,
            EnderecoRepository enderecoRepository,
            StatusPedidoRepository statusPedidoRepository,
            HistoricoStatusRepository historicoRepository,
            UsuarioService usuarioService) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
        this.enderecoRepository = enderecoRepository;
        this.statusPedidoRepository = statusPedidoRepository;
        this.historicoRepository = historicoRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest dados) {

        Usuario usuario = usuarioService.buscarEntidade(dados.getIdUsuario());

        if (dados.getItens() == null || dados.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                    "O pedido precisa ter pelo menos um item.");
        }
        if (dados.getFormaPagamento() == null
                || dados.getFormaPagamento().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A forma de pagamento é obrigatória.");
        }
        if (dados.getIdEndereco() == null) {
            throw new IllegalArgumentException("O idEndereco é obrigatório.");
        }

        Endereco endereco = enderecoRepository.findById(dados.getIdEndereco())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço não encontrado."));

        // O endereço precisa ser do próprio usuário
        if (!endereco.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new IllegalArgumentException(
                    "Este endereço não pertence ao usuário.");
        }

        // O primeiro status da tabela status_pedido é usado como status inicial
        StatusPedido statusInicial = statusPedidoRepository
                .findFirstByOrderByIdStatusAsc()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nenhum status de pedido cadastrado no banco."));

        // Pedido é salvo primeiro para ganhar um id; os valores são ajustados depois
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEndereco(endereco);
        pedido.setStatus(statusInicial);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setFormaPagamento(dados.getFormaPagamento());
        pedido.setObservacao(dados.getObservacao());
        pedido.setSubtotal(0.0);
        pedido.setFrete(FRETE);
        pedido.setTotal(FRETE);

        // Cópia do endereço no pedido (se o usuário mudar o endereço depois, o pedido não muda)
        pedido.setEnderecoCep(endereco.getCep());
        pedido.setEnderecoLogradouro(endereco.getLogradouro());
        pedido.setEnderecoNumero(endereco.getNumero());
        pedido.setEnderecoComplemento(endereco.getComplemento());
        pedido.setEnderecoBairro(endereco.getBairro());
        pedido.setEnderecoCidade(endereco.getCidade());
        pedido.setEnderecoEstado(endereco.getEstado());

        pedido = pedidoRepository.save(pedido);

        double subtotal = 0;

        for (PedidoRequest.Item itemDto : dados.getItens()) {

            if (itemDto.getIdProduto() == null || itemDto.getQuantidade() == null
                    || itemDto.getQuantidade() <= 0) {
                throw new IllegalArgumentException(
                        "Cada item precisa de idProduto e quantidade maior que zero.");
            }

            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Produto não encontrado: " + itemDto.getIdProduto()));

            if (!Boolean.TRUE.equals(produto.getAtivo())) {
                throw new IllegalArgumentException(
                        "Produto indisponível: " + produto.getNome());
            }
            if (produto.getEstoque() == null
                    || produto.getEstoque() < itemDto.getQuantidade()) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para: " + produto.getNome());
            }

            // Baixa o estoque
            produto.setEstoque(produto.getEstoque() - itemDto.getQuantidade());
            produtoRepository.save(produto);

            double itemSubtotal = arredondar(
                    produto.getPreco() * itemDto.getQuantidade());

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setSubtotal(itemSubtotal);
            itemPedidoRepository.save(item);

            subtotal += itemSubtotal;
        }

        subtotal = arredondar(subtotal);
        pedido.setSubtotal(subtotal);
        pedido.setTotal(arredondar(subtotal + FRETE));
        pedido = pedidoRepository.save(pedido);

        // Primeiro registro do histórico de status
        HistoricoStatus historico = new HistoricoStatus();
        historico.setPedido(pedido);
        historico.setStatus(statusInicial);
        historico.setDataAlteracao(LocalDateTime.now());
        historico.setObservacao("Pedido criado");
        historicoRepository.save(historico);

        return converter(pedido);
    }

    public List<PedidoResponse> listarPorUsuario(Integer idUsuario) {
        usuarioService.buscarEntidade(idUsuario);

        List<PedidoResponse> lista = new ArrayList<>();
        for (Pedido p : pedidoRepository
                .findByUsuario_IdUsuarioOrderByDataPedidoDesc(idUsuario)) {
            lista.add(converter(p));
        }
        return lista;
    }

    public PedidoResponse buscar(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Pedido não encontrado."));
        return converter(pedido);
    }

    private PedidoResponse converter(Pedido p) {
        PedidoResponse r = new PedidoResponse();
        r.setIdPedido(p.getIdPedido());
        r.setDataPedido(p.getDataPedido());
        r.setStatus(p.getStatus().getNome());
        r.setSubtotal(p.getSubtotal());
        r.setFrete(p.getFrete());
        r.setTotal(p.getTotal());
        r.setFormaPagamento(p.getFormaPagamento());
        r.setObservacao(p.getObservacao());
        r.setEndereco(p.getEnderecoLogradouro() + ", " + p.getEnderecoNumero()
                + " - " + p.getEnderecoBairro() + ", " + p.getEnderecoCidade()
                + "/" + p.getEnderecoEstado() + " - CEP " + p.getEnderecoCep());

        List<PedidoResponse.Item> itens = new ArrayList<>();
        for (ItemPedido i : itemPedidoRepository.findByPedido_IdPedido(p.getIdPedido())) {
            PedidoResponse.Item item = new PedidoResponse.Item();
            item.setNomeProduto(i.getProduto().getNome());
            item.setQuantidade(i.getQuantidade());
            item.setPrecoUnitario(i.getPrecoUnitario());
            item.setSubtotal(i.getSubtotal());
            itens.add(item);
        }
        r.setItens(itens);

        List<String> historico = new ArrayList<>();
        for (HistoricoStatus h : historicoRepository
                .findByPedido_IdPedidoOrderByDataAlteracaoAsc(p.getIdPedido())) {
            historico.add(h.getDataAlteracao() + " - " + h.getStatus().getNome());
        }
        r.setHistorico(historico);

        return r;
    }

    // Evita números como 10.299999 nos valores em dinheiro
    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
