package com.artificer.clientes.domain.exception;

public class ClienteNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public ClienteNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public ClienteNaoEncontradaException(Long usuarioId) {
        this(String.format("Não existe Usuário com o Id %d cadastrado no banco de dados!", usuarioId));
    }

}
