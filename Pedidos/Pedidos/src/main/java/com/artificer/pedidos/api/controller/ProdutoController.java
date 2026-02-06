package com.artificer.pedidos.api.controller;

import com.artificer.clientes.page.CustomPage;
import com.artificer.clientes.page.PageMetaData;
import com.artificer.pedidos.api.model.input.ProdutoInput;
import com.artificer.pedidos.api.model.output.ProdutoOutput;
import com.artificer.pedidos.domain.model.Produto;
import com.artificer.pedidos.domain.repository.ProdutoRepository;
import com.artificer.pedidos.domain.service.ProdutoService;
import com.artificer.pedidos.mapper.ProdutoMapper;
import com.artificer.pedidos.security.CanEditProdutos;
import com.artificer.pedidos.security.CanReadProdutos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoMapper produtoMapper;

    @GetMapping
    @CanReadProdutos
    public ResponseEntity<CustomPage<ProdutoOutput>> listarProdutos(@PageableDefault Pageable pageable) {
        Page<Produto> produtosPage = produtoRepository.findAll(pageable);
        List<ProdutoOutput> modelList = produtoMapper.toModelList(produtosPage.getContent());
        CustomPage<ProdutoOutput> customPage = new CustomPage<>(modelList, PageMetaData.brandNewPage(produtosPage));
        return ResponseEntity.ok(customPage);
    }

    @CanReadProdutos
    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> buscarProduto(@PathVariable UUID produtoId) {
        Produto produto = produtoService.buscarOuFalhar(produtoId);
        var produtoModel = produtoMapper.toModel(produto);
        return ResponseEntity.ok(produtoModel);
    }

    @PostMapping
    @CanEditProdutos
    public ResponseEntity<ProdutoOutput> adicionarProduto(@RequestBody @Valid ProdutoInput produtoInput) {
        Produto novoProduto = produtoMapper.toEntity(produtoInput);
        novoProduto = produtoService.salvarProduto(novoProduto);
        ProdutoOutput produtoOutput = produtoMapper.toModel(novoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoOutput);
    }

    @CanEditProdutos
    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> atualizarProduto(@PathVariable UUID produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoExistente = produtoService.buscarOuFalhar(produtoId);
        Produto produtoAtualizado = produtoService.atualizarProduto(produtoExistente, produtoInput);
        ProdutoOutput produtoOutput = produtoMapper.toModel(produtoAtualizado);
        return ResponseEntity.ok(produtoOutput);
    }

    @CanReadProdutos
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID produtoId) {
        Produto produto = produtoService.buscarOuFalhar(produtoId);
        produtoRepository.deleteByCodigoProduto(produtoId);
        return ResponseEntity.noContent().build();
    }

}
