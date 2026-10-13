package com.baozi.store.dto;

import java.math.BigDecimal;
import com.baozi.store.model.Pedido;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String clienteNome,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal total) {

    public static PedidoResponse de(Pedido p) {
        BigDecimal total = p.getProduto().getPreco()
                .multiply(BigDecimal.valueOf(p.getQuantidade()));
        return new PedidoResponse(
                p.getId(),
                p.getCliente().getId(), p.getCliente().getNome(),
                p.getProduto().getId(), p.getProduto().getNome(),
                p.getQuantidade(), total);
    }
}