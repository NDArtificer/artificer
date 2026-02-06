CREATE TABLE pedido (
    id BIGSERIAL PRIMARY KEY,              -- chave primária auto-incrementada
    codigo_pedido UUID NOT NULL,           -- identificador único do pedido
    subtotal NUMERIC(19,2) NOT NULL,       -- subtotal do pedido
    taxa_frete NUMERIC(19,2) NOT NULL,     -- valor do frete
    valor_total NUMERIC(19,2) NOT NULL,    -- valor total do pedido
    status VARCHAR(50) NOT NULL,           -- enum armazenado como string

    -- dados do cliente vindos de outro microserviço
    pedido_cliente_id bigint NOT NULL,
    pedido_cliente_nome VARCHAR(255) NOT NULL,
    pedido_cliente_email VARCHAR(255) NOT NULL
);
