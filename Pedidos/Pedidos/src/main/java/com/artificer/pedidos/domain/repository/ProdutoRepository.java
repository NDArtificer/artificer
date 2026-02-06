package com.artificer.pedidos.domain.repository;

import com.artificer.pedidos.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigoProduto(UUID codigoProduto);

    void deleteByCodigoProduto(UUID produtoId);
}
