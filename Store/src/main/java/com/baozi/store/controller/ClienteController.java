package com.baozi.store.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baozi.store.exception.RecursoNaoEncontradoException;
import com.baozi.store.model.Cliente;
import com.baozi.store.repository.ClienteRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repo;

    public ClienteController(ClienteRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        Cliente salvo = repo.save(cliente);
        return ResponseEntity.created(URI.create("/clientes/" + salvo.getId())).body(salvo);
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Cliente " + id + " não encontrado"));
    }

    @GetMapping
    public List<Cliente> listar() { return repo.findAll(); }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody Cliente dados) {
        Cliente existente = buscar(id);
        existente.setNome(dados.getNome());
        if (dados.getClienteDesde() != null) {
            existente.setClienteDesde(dados.getClienteDesde());
        }
        return repo.save(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        buscar(id);
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}