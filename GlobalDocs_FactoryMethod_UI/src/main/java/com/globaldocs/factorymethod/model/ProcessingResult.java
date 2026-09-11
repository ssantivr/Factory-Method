package com.globaldocs.factorymethod.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Resultado inmutable del procesamiento de un documento.
 * Usa un {@code record} de Java 21 como estructura de datos concisa.
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
    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /** Línea formateada para la consola/log estilo terminal de la GUI. */
    public String toLogLine() {
        String status = success ? "OK" : "ERROR";
        String codePart = (regulatoryCode != null && !regulatoryCode.isBlank())
                ? " | código: " + regulatoryCode
                : "";
        return "[%s] [%-5s] [%s] %s → %s%s".formatted(
                timestamp.format(TIME_PATTERN),
                status,
                country.isoCode(),
                fileName,
                message,
                codePart
        );
    }
}
