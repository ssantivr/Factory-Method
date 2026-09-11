package com.globaldocs.factorymethod.exception;

/** Se lanza cuando un documento no supera la validación regulatoria específica del país. */
public class RegulatoryValidationException extends RuntimeException {
    public RegulatoryValidationException(String message) {
        super(message);
    }
}
