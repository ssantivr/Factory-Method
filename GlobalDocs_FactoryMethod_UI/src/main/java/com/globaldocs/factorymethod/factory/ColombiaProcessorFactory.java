package com.globaldocs.factorymethod.factory;

import com.globaldocs.factorymethod.factory.impl.ColombiaDocumentProcessor;

/** Creador concreto: fabrica el procesador de documentos para Colombia (DIAN). */
public class ColombiaProcessorFactory extends DocumentProcessorFactory {
    @Override
    public DocumentProcessor createProcessor() {
        return new ColombiaDocumentProcessor();
    }
}
