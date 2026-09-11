package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.ArgentinaDocumentProcessor;

/** Creador concreto: fabrica el procesador de documentos para Argentina (AFIP). */
public class ArgentinaProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new ArgentinaDocumentProcessor();
    }
}
