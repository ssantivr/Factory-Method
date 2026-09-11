package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;

import java.util.Set;

/**
 * Producto del patrón Factory Method.
 * Cada país implementa su propio procesador con reglas de formato y
 * validación regulatoria específicas.
 */
public interface DocumentProcessor {

    Country getCountry();

    /** Formatos de archivo permitidos para un tipo de documento en este país. */
    Set<DocumentFormat> getSupportedFormats(DocumentType type);

    /** Procesa y valida el documento, devolviendo un resultado (éxito o error controlado). */
    ProcessingResult process(String fileName, DocumentType type, DocumentFormat format);
}
