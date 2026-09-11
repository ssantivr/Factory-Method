package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.exception.UnsupportedFormatException;
import com.globaldocs.factorymethod.factory.DocumentProcessor;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Plantilla común para los procesadores concretos: valida formato,
 * delega la validación regulatoria específica del país y traduce
 * cualquier excepción de negocio en un {@link ProcessingResult} fallido
 * (manejo de errores centralizado, sin romper el flujo del lote).
 */
public abstract class AbstractDocumentProcessor implements DocumentProcessor {

    @Override
    public final ProcessingResult process(String fileName, DocumentType type, DocumentFormat format) {
        try {
            validateFormat(type, format);
            String regulatoryCode = validateRegulation(fileName, type);
            return new ProcessingResult(fileName, getCountry(), type, format, true,
                    "Documento procesado y validado correctamente", regulatoryCode, LocalDateTime.now());
        } catch (UnsupportedFormatException | RegulatoryValidationException ex) {
            return new ProcessingResult(fileName, getCountry(), type, format, false,
                    ex.getMessage(), null, LocalDateTime.now());
        }
    }

    protected void validateFormat(DocumentType type, DocumentFormat format) {
        if (!getSupportedFormats(type).contains(format)) {
            throw new UnsupportedFormatException(
                    "Formato %s no permitido para %s en %s".formatted(
                            format.extension(), type.displayName(), getCountry().displayName()));
        }
    }

    /** Simula la validación ante el organismo regulador del país; puede fallar y devolver el motivo. */
    protected abstract String validateRegulation(String fileName, DocumentType type);

    /** Simula una probabilidad de fallo regulatorio (para la demo de manejo de errores). */
    protected boolean simulateFailure(int failurePercentage) {
        return ThreadLocalRandom.current().nextInt(100) < failurePercentage;
    }

    protected String randomCode(String prefix) {
        return prefix + "-" + ThreadLocalRandom.current().nextInt(100_000, 999_999);
    }
}
