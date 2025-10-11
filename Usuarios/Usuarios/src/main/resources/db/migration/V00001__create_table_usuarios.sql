CREATE TABLE IF NOT EXISTS public.usuario
(
    id bigint NOT NULL,
    data_criacao timestamp(6) with time zone,
    email character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default",
    senha character varying(255) COLLATE pg_catalog."default",
    tipo character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT uk4xad1enskw4j1t2866f7sodrx UNIQUE (email),
    CONSTRAINT usuario_tipo_check CHECK (tipo::text = ANY (ARRAY['ADMIN'::character varying, 'CLIENT'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.usuario
    OWNER to postgres;

CREATE SEQUENCE usuarios_id_seq;

ALTER TABLE IF EXISTS public.usuario
ALTER COLUMN id SET DEFAULT nextval('usuarios_id_seq');