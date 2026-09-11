package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;

import java.util.EnumSet;
import java.util.Set;

import static com.globaldocs.factorymethod.model.DocumentFormat.*;

/** Concrete product: document processor that complies with DIAN regulations (Colombia). */
public class ColombiaDocumentProcessor extends AbstractDocumentProcessor {

    @Override
    public Country getCountry() {
        return Country.COLOMBIA;
    }

    @Override
    public Set<DocumentFormat> getSupportedFormats(DocumentType type) {
        return switch (type) {
            case ELECTRONIC_INVOICE -> EnumSet.of(PDF, XLSX, CSV);
            case LEGAL_CONTRACT -> EnumSet.of(PDF, DOC, DOCX);
            case FINANCIAL_REPORT -> EnumSet.of(XLSX, CSV, PDF);
            case DIGITAL_CERTIFICATE -> EnumSet.of(PDF, TXT);
            case TAX_RETURN -> EnumSet.of(PDF, XLSX, CSV, TXT);
        };
    }

    @Override
    protected String validateRegulation(String fileName, DocumentType type) {
        if (simulateFailure(8)) {
            throw new RegulatoryValidationException(
                    "DIAN rejected the document: invalid CUFE or expired invoicing resolution (%s)"
                            .formatted(type.displayName()));
        }
        return randomCode("CUFE-CO");
    }
}
