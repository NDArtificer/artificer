CREATE TABLE itens_pedido (
    id BIGSERIAL PRIMARY KEY,              -- chave primária auto-incrementada
    valor_total_item NUMERIC(19,2) NOT NULL, -- BigDecimal -> NUMERIC com precisão
    quantidade INTEGER NOT NULL,           -- quantidade de produtos
    produto_id BIGINT NOT NULL,            -- FK para tabela produtos
    pedido_id BIGINT NOT NULL,             -- FK para tabela pedidos
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produto(id),
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);
