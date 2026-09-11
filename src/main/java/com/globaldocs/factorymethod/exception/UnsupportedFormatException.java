package com.globaldocs.factorymethod.exception;

/** Thrown when the document format is not supported for the given document type/country. */
public class UnsupportedFormatException extends RuntimeException {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
