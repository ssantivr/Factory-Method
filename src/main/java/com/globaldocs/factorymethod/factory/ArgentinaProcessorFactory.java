package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.ArgentinaDocumentProcessor;

/** Concrete creator: builds the document processor for Argentina (AFIP). */
public class ArgentinaProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new ArgentinaDocumentProcessor();
    }
}
