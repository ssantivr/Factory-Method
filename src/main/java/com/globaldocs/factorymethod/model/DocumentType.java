package com.globaldocs.factorymethod.model;

/** Business document types processed by GlobalDocs Solutions. */
public enum DocumentType {
    ELECTRONIC_INVOICE("Electronic Invoice"),
    LEGAL_CONTRACT("Legal Contract"),
    FINANCIAL_REPORT("Financial Report"),
    DIGITAL_CERTIFICATE("Digital Certificate"),
    TAX_RETURN("Tax Return");

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
