package com.baozi.store.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baozi.store.dto.*;
import com.baozi.store.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest req) {
        PedidoResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/pedidos/" + criado.id())).body(criado);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) { return service.buscar(id); }

    @GetMapping
    public List<PedidoResponse> listar() { return service.listar(); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}