package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;

import java.util.EnumSet;
import java.util.Set;

import static com.globaldocs.factorymethod.model.DocumentFormat.*;

/** Producto concreto: procesador de documentos que cumple la normativa DIAN (Colombia). */
public class ColombiaDocumentProcessor extends AbstractDocumentProcessor {

    @Override
    public Country getCountry() {
        return Country.COLOMBIA;
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
        if (simulateFailure(8)) {
            throw new RegulatoryValidationException(
                    "DIAN rechazó el documento: CUFE inválido o resolución de facturación vencida (%s)"
                            .formatted(type.displayName()));
        }
        return randomCode("CUFE-CO");
    }
}
