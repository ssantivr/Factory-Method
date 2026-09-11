package com.globaldocs.factorymethod.service;

import com.globaldocs.factorymethod.factory.DocumentProcessorFactory;
import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * Simula el procesamiento por lotes (batch) en tiempo real, apoyándose en el
 * {@link DocumentProcessorFactory#forCountry(Country)} para obtener el creador
 * concreto correspondiente al país seleccionado.
 */
public class BatchProcessingService {

    public Task<Void> createBatchTask(Country country, DocumentType type, DocumentFormat format,
                                       int batchSize, Consumer<ProcessingResult> onResult) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                DocumentProcessorFactory factory = DocumentProcessorFactory.forCountry(country);
                for (int i = 1; i <= batchSize && !isCancelled(); i++) {
                    String fileName = "%s_%04d%s".formatted(type.name(), i, format.extension());
                    ProcessingResult result = factory.processDocument(fileName, type, format);
                    Platform.runLater(() -> onResult.accept(result));
                    updateProgress(i, batchSize);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(35, 130));
                }
                return null;
            }
        };
    }
}
