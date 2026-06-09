package com.duoc.guia_despacho.exception;

public class PermisoDenegadoException extends RuntimeException {

    public PermisoDenegadoException() {
        super("El transportista no tiene permiso para acceder a esta guia");
    }
}
