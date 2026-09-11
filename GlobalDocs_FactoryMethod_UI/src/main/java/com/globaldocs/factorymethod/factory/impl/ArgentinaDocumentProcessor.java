package com.globaldocs.factorymethod.factory.impl;

import com.globaldocs.factorymethod.exception.RegulatoryValidationException;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;

import java.util.EnumSet;
import java.util.Set;

import static com.globaldocs.factorymethod.model.DocumentFormat.*;

/** Producto concreto: procesador de documentos que cumple la normativa AFIP (Argentina). */
public class ArgentinaDocumentProcessor extends AbstractDocumentProcessor {

    @Override
    public Country getCountry() {
        return Country.ARGENTINA;
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
        if (simulateFailure(10)) {
            throw new RegulatoryValidationException(
                    "AFIP rechazó el comprobante: CAE denegado o punto de venta no habilitado (%s)"
                            .formatted(type.displayName()));
        }
        return randomCode("CAE-AR");
    }
}
