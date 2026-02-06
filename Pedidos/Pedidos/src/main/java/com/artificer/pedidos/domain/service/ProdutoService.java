package com.artificer.pedidos.domain.service;

import com.artificer.pedidos.api.model.input.ProdutoInput;
import com.artificer.pedidos.domain.exception.NegocioException;
import com.artificer.pedidos.domain.model.Produto;
import com.artificer.pedidos.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvarProduto(Produto novoProduto) {
        return produtoRepository.save(novoProduto);
    }

    public Produto buscarOuFalhar(UUID produtoId) {
        return produtoRepository.findByCodigoProduto(produtoId)
                .orElseThrow(() -> new NegocioException("Produto não encontrado!"));
    }

    public Produto atualizarProduto(Produto produtoExistente, @Valid ProdutoInput produtoInput) {
        produtoExistente.setNome(produtoInput.getNome());
        produtoExistente.setDescricao(produtoInput.getDescricao());
        produtoExistente.setPreco(produtoInput.getPreco());
        return produtoRepository.save(produtoExistente);
    }
}
