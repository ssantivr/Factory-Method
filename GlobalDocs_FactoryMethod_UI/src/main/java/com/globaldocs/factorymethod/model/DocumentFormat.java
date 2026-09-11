package com.globaldocs.factorymethod.model;

/** Formatos de archivo soportados por la plataforma. */
public enum DocumentFormat {
    PDF(".pdf"),
    DOC(".doc"),
    DOCX(".docx"),
    MD(".md"),
    CSV(".csv"),
    TXT(".txt"),
    XLSX(".xlsx");

    private final String extension;

    DocumentFormat(String extension) {
        this.extension = extension;
    }

    public String extension() {
        return extension;
    }

    @Override
    public String toString() {
        return extension;
    }
}
