package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.ChileDocumentProcessor;

/** Creador concreto: fabrica el procesador de documentos para Chile (SII). */
public class ChileProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new ChileDocumentProcessor();
    }
}
