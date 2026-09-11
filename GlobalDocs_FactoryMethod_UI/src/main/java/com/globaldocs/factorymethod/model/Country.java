package com.globaldocs.factorymethod.model;

/**
 * Países soportados por GlobalDocs Solutions, cada uno con su propio
 * organismo regulador y normativa de documentos electrónicos.
 */
public enum Country {
    COLOMBIA("Colombia", "CO", "DIAN · Resolución 000042 (Facturación Electrónica)"),
    MEXICO("México", "MX", "SAT · CFDI 4.0"),
    ARGENTINA("Argentina", "AR", "AFIP · RG 4291 (Comprobantes Electrónicos)"),
    CHILE("Chile", "CL", "SII · Documento Tributario Electrónico (DTE)");

    private final String displayName;
    private final String isoCode;
    private final String regulation;

    Country(String displayName, String isoCode, String regulation) {
        this.displayName = displayName;
        this.isoCode = isoCode;
        this.regulation = regulation;
    }

    public String displayName() {
        return displayName;
    }

    public String isoCode() {
        return isoCode;
    }

    public String regulation() {
        return regulation;
    }
}
