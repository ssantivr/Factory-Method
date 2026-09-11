package com.globaldocs.factorymethod.exception;

/** Thrown when a document fails the country-specific regulatory validation. */
public class RegulatoryValidationException extends RuntimeException {
    public RegulatoryValidationException(String message) {
        super(message);
    }
}
