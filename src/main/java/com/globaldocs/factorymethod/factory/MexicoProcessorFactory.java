package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.MexicoDocumentProcessor;

/** Concrete creator: builds the document processor for Mexico (SAT). */
public class MexicoProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new MexicoDocumentProcessor();
    }
}
