package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.ChileDocumentProcessor;

/** Concrete creator: builds the document processor for Chile (SII). */
public class ChileProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new ChileDocumentProcessor();
    }
}
