CREATE TABLE IF NOT EXISTS public.cliente
(
    id bigint NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default",
    tipo_cliente character varying(4) COLLATE pg_catalog."default",
    cpf_cnpj character varying(14) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    telefone character varying(20) COLLATE pg_catalog."default",
    endereco_logradouro character varying(255) COLLATE pg_catalog."default",
    endereco_numero character varying(10) COLLATE pg_catalog."default",
    endereco_complemento character varying(255) COLLATE pg_catalog."default",
    endereco_bairro character varying(100) COLLATE pg_catalog."default",
    endereco_cidade character varying(150) COLLATE pg_catalog."default",
    endereco_estado character varying(50) COLLATE pg_catalog."default",
    endereco_cep character varying(15) COLLATE pg_catalog."default",
    CONSTRAINT cliente_pkey PRIMARY KEY (id),
    CONSTRAINT uk4xad1enskw4j1t2866f7sodrx UNIQUE (email),
    CONSTRAINT cilente_tipo_cliente_check CHECK (tipo_cliente::text = ANY (ARRAY['CPF'::character varying, 'CNPJ'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cliente
    OWNER to postgres;

CREATE SEQUENCE clientes_id_seq;

ALTER TABLE IF EXISTS public.cliente
ALTER COLUMN id SET DEFAULT nextval('clientes_id_seq');