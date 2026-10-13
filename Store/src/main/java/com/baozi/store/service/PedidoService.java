package com.baozi.store.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.baozi.store.dto.*;
import com.baozi.store.exception.*;
import com.baozi.store.model.*;
import com.baozi.store.repository.*;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    private final ProdutoRepository produtoRepo;

    public PedidoService(PedidoRepository pedidoRepo, ClienteRepository clienteRepo,
                         ProdutoRepository produtoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }

    public PedidoResponse criar(PedidoRequest req) {
        Cliente cliente = clienteRepo.findById(req.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente " + req.clienteId() + " não encontrado"));
        Produto produto = produtoRepo.findById(req.produtoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto " + req.produtoId() + " não encontrado"));

        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new RegraNegocioException("Produto sem estoque disponível");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(req.quantidade());
        return PedidoResponse.de(pedidoRepo.save(pedido));
    }

    public PedidoResponse buscar(Long id) {
        return PedidoResponse.de(pedidoRepo.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Pedido " + id + " não encontrado")));
    }

    public List<PedidoResponse> listar() {
        return pedidoRepo.findAll().stream().map(PedidoResponse::de).toList();
    }

    public void apagar(Long id) {
        if (!pedidoRepo.existsById(id)) {
            throw new RecursoNaoEncontradoException("Pedido " + id + " não encontrado");
        }
        pedidoRepo.deleteById(id);
    }
}