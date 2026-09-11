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
 * Common template for the concrete processors: validates the format,
 * delegates the country-specific regulatory validation, and translates
 * any business exception into a failed {@link ProcessingResult}
 * (centralized error handling, without breaking the batch flow).
 */
public abstract class AbstractDocumentProcessor implements DocumentProcessor {

    @Override
    public final ProcessingResult process(String fileName, DocumentType type, DocumentFormat format) {
        try {
            validateFormat(type, format);
            String regulatoryCode = validateRegulation(fileName, type);
            return new ProcessingResult(fileName, getCountry(), type, format, true,
                    "Document processed and validated successfully", regulatoryCode, LocalDateTime.now());
        } catch (UnsupportedFormatException | RegulatoryValidationException ex) {
            return new ProcessingResult(fileName, getCountry(), type, format, false,
                    ex.getMessage(), null, LocalDateTime.now());
        }
    }

    protected void validateFormat(DocumentType type, DocumentFormat format) {
        if (!getSupportedFormats(type).contains(format)) {
            throw new UnsupportedFormatException(
                    "Format %s is not allowed for %s in %s".formatted(
                            format.extension(), type.displayName(), getCountry().displayName()));
        }
    }

    /** Simulates validation with the country's regulatory body; may fail and report the reason. */
    protected abstract String validateRegulation(String fileName, DocumentType type);

    /** Simulates a regulatory failure probability (for the error-handling demo). */
    protected boolean simulateFailure(int failurePercentage) {
        return ThreadLocalRandom.current().nextInt(100) < failurePercentage;
    }

    protected String randomCode(String prefix) {
        return prefix + "-" + ThreadLocalRandom.current().nextInt(100_000, 999_999);
    }
}
