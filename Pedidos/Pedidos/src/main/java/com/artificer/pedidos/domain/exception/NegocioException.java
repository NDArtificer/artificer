package com.artificer.pedidos.domain.exception;

public class NegocioException extends RuntimeException{

    public NegocioException(String message) {
        super(message);
    }
}
