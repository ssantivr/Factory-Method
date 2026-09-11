package com.globaldocs.factorymethod.exception;

/** Se lanza cuando el formato del documento no es compatible con el tipo de documento/país. */
public class UnsupportedFormatException extends RuntimeException {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
