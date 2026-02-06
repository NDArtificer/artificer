CREATE TABLE produto (
    id BIGSERIAL PRIMARY KEY,          -- chave primária auto-incrementada
    codigo_produto UUID NOT NULL,      -- identificador único do produto
    nome VARCHAR(255) NOT NULL,        -- nome do produto
    descricao TEXT,                    -- descrição detalhada
    preco NUMERIC(19,2) NOT NULL       -- preço com precisão decimal
);
ALTER TABLE produto
ADD CONSTRAINT uk_codigo_produto UNIQUE (codigo_produto);
