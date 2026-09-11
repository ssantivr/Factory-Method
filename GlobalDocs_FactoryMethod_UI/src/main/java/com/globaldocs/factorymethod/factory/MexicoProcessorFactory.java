package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.MexicoDocumentProcessor;

/** Creador concreto: fabrica el procesador de documentos para México (SAT). */
public class MexicoProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new MexicoDocumentProcessor();
    }
}
