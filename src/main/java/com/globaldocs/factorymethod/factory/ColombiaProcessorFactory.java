package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.ColombiaDocumentProcessor;

/** Concrete creator: builds the document processor for Colombia (DIAN). */
public class ColombiaProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new ColombiaDocumentProcessor();
    }
}
