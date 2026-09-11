package com.globaldocs.factorymethod.model;

import java.time.LocalDateTime;

/**
 * Immutable result of processing a single document.
 * Uses a Java 21 {@code record} as a concise data carrier.
 */
public record ProcessingResult(
        String fileName,
        Country country,
        DocumentType type,
        DocumentFormat format,
        boolean success,
        String message,
        String regulatoryCode,
        LocalDateTime timestamp
) {
}
