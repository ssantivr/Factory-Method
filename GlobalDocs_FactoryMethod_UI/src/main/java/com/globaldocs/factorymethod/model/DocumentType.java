package com.globaldocs.factorymethod.model;

/** Tipos de documentos empresariales que procesa GlobalDocs Solutions. */
public enum DocumentType {
    FACTURA_ELECTRONICA("Factura Electrónica"),
    CONTRATO_LEGAL("Contrato Legal"),
    REPORTE_FINANCIERO("Reporte Financiero"),
    CERTIFICADO_DIGITAL("Certificado Digital"),
    DECLARACION_TRIBUTARIA("Declaración Tributaria");

    private final String displayName;

    DocumentType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
