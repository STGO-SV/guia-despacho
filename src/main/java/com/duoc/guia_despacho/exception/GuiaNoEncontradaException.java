package com.duoc.guia_despacho.exception;

public class GuiaNoEncontradaException extends RuntimeException {

    public GuiaNoEncontradaException(Long id) {
        super("No existe una guia de despacho con id " + id);
    }
}
