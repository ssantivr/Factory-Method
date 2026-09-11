package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;

import java.util.EnumSet;
import java.util.Set;

import static com.globaldocs.factorymethod.model.DocumentFormat.*;

/** Concrete product: document processor that complies with SII/DTE regulations (Chile). */
public class ChileDocumentProcessor extends AbstractDocumentProcessor {

    @Override
    public Country getCountry() {
        return Country.CHILE;
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
        if (simulateFailure(7)) {
            throw new RegulatoryValidationException(
                    "SII rejected the DTE: CAF folio exhausted or invalid electronic signature (%s)"
                            .formatted(type.displayName()));
        }
        return randomCode("DTE-CL");
    }
}
