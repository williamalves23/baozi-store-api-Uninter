package com.baozi.store.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baozi.store.exception.RecursoNaoEncontradoException;
import com.baozi.store.model.Produto;
import com.baozi.store.repository.ProdutoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repo;

    public ProdutoController(ProdutoRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        produto.setId(null);
        Produto salvo = repo.save(produto);
        return ResponseEntity.created(URI.create("/produtos/" + salvo.getId())).body(salvo);
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Produto " + id + " não encontrado"));
    }

    @GetMapping
    public List<Produto> listar() { return repo.findAll(); }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto dados) {
        Produto existente = buscar(id);
        existente.setNome(dados.getNome());
        existente.setPreco(dados.getPreco());
        existente.setEstoque(dados.getEstoque());
        return repo.save(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        buscar(id);
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}