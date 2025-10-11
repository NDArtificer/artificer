#!/bin/bash
set -e

# Inicia o PostgreSQL em segundo plano
docker-entrypoint.sh postgres &

# Espera o PostgreSQL iniciar
until pg_isready -h localhost -p 5432 -U "$POSTGRES_USER"; do
  echo "Aguardando PostgreSQL iniciar..."
  sleep 2
done

# Executa o script init.sql
psql -U "$POSTGRES_USER" -d postgres -f /scripts/init.sql

# Mantém o processo principal rodando
wait
