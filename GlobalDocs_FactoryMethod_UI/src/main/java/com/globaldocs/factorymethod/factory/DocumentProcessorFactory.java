package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;

/**
 * Creador (Creator) del patrón Factory Method.
 * Declara el método fábrica {@link #createProcessor()} que cada país
 * (creador concreto) sobreescribe para instanciar su propio {@link DocumentProcessor}.
 */
public abstract class DocumentProcessorFactory {

    /** Método fábrica: las subclases deciden qué {@link DocumentProcessor} concreto crear. */
    public abstract DocumentProcessor createProcessor();

    /** Operación de negocio que se apoya en el método fábrica (no requiere conocer la clase concreta). */
    public final ProcessingResult processDocument(String fileName, DocumentType type, DocumentFormat format) {
        DocumentProcessor processor = createProcessor();
        return processor.process(fileName, type, format);
    }

    /** Punto único de acceso: obtiene el creador concreto según el país, usando pattern matching. */
    public static DocumentProcessorFactory forCountry(Country country) {
        return switch (country) {
            case COLOMBIA -> new ColombiaProcessorFactory();
            case MEXICO -> new MexicoProcessorFactory();
            case ARGENTINA -> new ArgentinaProcessorFactory();
            case CHILE -> new ChileProcessorFactory();
        };
    }
}
