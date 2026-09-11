package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;

import java.util.Set;

/**
 * Product of the Factory Method pattern.
 * Each country implements its own processor with country-specific
 * format rules and regulatory validation.
 */
public interface DocumentProcessor {

    Country getCountry();

    /** File formats allowed for a document type in this country. */
    Set<DocumentFormat> getSupportedFormats(DocumentType type);

    /** Processes and validates the document, returning a result (success or handled error). */
    ProcessingResult process(String fileName, DocumentType type, DocumentFormat format);
}
