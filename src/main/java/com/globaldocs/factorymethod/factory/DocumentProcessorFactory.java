package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;

/**
 * Creator of the Factory Method pattern.
 * Declares the factory method {@link #createProcessor()} that each
 * country (concrete creator) overrides to instantiate its own {@link DocumentProcessor}.
 */
public abstract class DocumentProcessorFactory {

    /** Factory method: subclasses decide which concrete {@link DocumentProcessor} to create. */
    public abstract DocumentProcessor createProcessor();

    /** Business operation that relies on the factory method (no need to know the concrete class). */
    public final ProcessingResult processDocument(String fileName, DocumentType type, DocumentFormat format) {
        DocumentProcessor processor = createProcessor();
        return processor.process(fileName, type, format);
    }

    /** Single access point: resolves the concrete creator for a country via pattern matching. */
    public static DocumentProcessorFactory forCountry(Country country) {
        return switch (country) {
            case COLOMBIA -> new ColombiaProcessorFactory();
            case MEXICO -> new MexicoProcessorFactory();
            case ARGENTINA -> new ArgentinaProcessorFactory();
            case CHILE -> new ChileProcessorFactory();
        };
    }
}
