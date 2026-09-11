package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;

import java.util.EnumSet;
import java.util.Set;

import static com.globaldocs.factorymethod.model.DocumentFormat.*;

/** Producto concreto: procesador de documentos que cumple la normativa SAT/CFDI (México). */
public class MexicoDocumentProcessor extends AbstractDocumentProcessor {

    @Override
    public Country getCountry() {
        return Country.MEXICO;
    }

    @Override
    public Set<DocumentFormat> getSupportedFormats(DocumentType type) {
        return switch (type) {
            case FACTURA_ELECTRONICA -> EnumSet.of(PDF, XLSX, CSV);
            case CONTRATO_LEGAL -> EnumSet.of(PDF, DOC, DOCX);
            case REPORTE_FINANCIERO -> EnumSet.of(XLSX, CSV, PDF);
            case CERTIFICADO_DIGITAL -> EnumSet.of(PDF, TXT);
            case DECLARACION_TRIBUTARIA -> EnumSet.of(PDF, XLSX, CSV, TXT);
        };
    }

    @Override
    protected String validateRegulation(String fileName, DocumentType type) {
        if (simulateFailure(9)) {
            throw new RegulatoryValidationException(
                    "SAT rechazó el timbrado: UUID de CFDI inválido o certificado (CSD) vencido (%s)"
                            .formatted(type.displayName()));
        }
        return randomCode("CFDI-MX");
    }
}
