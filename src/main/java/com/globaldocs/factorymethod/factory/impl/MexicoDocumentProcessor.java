package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;

import java.util.EnumSet;
import java.util.Set;

import static com.globaldocs.factorymethod.model.DocumentFormat.*;

/** Concrete product: document processor that complies with SAT/CFDI regulations (Mexico). */
public class MexicoDocumentProcessor extends AbstractDocumentProcessor {

    @Override
    public Country getCountry() {
        return Country.MEXICO;
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
        if (simulateFailure(9)) {
            throw new RegulatoryValidationException(
                    "SAT rejected the stamping: invalid CFDI UUID or expired digital certificate (CSD) (%s)"
                            .formatted(type.displayName()));
        }
        return randomCode("CFDI-MX");
    }
}
