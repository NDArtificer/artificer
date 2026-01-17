-- Cria o banco 'usuarios' se não existir
SELECT 'CREATE DATABASE usuarios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios')
\gexec

-- Cria o banco 'usuarios_test' se não existir
SELECT 'CREATE DATABASE usuarios_test'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios_test')
\gexec

-- Cria o banco 'auth' se não existir
SELECT 'CREATE DATABASE auth'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth')
\gexec

-- Cria o banco 'auth_test' se não existir
SELECT 'CREATE DATABASE auth_test'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth_test')
\gexec


-- Cria o banco 'auth' se não existir
SELECT 'CREATE DATABASE Clientes'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'clientes')
\gexec

-- Cria o banco 'auth_test' se não existir
SELECT 'CREATE DATABASE clientes_test'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'clientes_test')
\gexec