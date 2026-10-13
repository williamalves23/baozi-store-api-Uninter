package com.baozi.store.dto;

import jakarta.validation.constraints.*;

public record PedidoRequest(
        @NotNull(message = "clienteId é obrigatório") Long clienteId,
        @NotNull(message = "produtoId é obrigatório") Long produtoId,
        @NotNull(message = "quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade mínima é 1") Integer quantidade) {}