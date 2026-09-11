# GlobalDocs Solutions — Taller Patrón Factory Method

Caso de estudio real: procesamiento multinacional de documentos empresariales
(Colombia, México, Argentina, Chile) con validación regulatoria por país,
implementado con el patrón de diseño **Factory Method** en **Java 21**.

## Ejecutar

```bash
mvn clean javafx:run
```

## Estructura

```
src/main/java/com/globaldocs/factorymethod/
 ├─ MainApp.java                 # GUI JavaFX (usa styles.css)
 ├─ model/                       # Country, DocumentType, DocumentFormat, ProcessingResult (record)
 ├─ exception/                   # UnsupportedFormatException, RegulatoryValidationException
 ├─ factory/                     # DocumentProcessor (Producto) + DocumentProcessorFactory (Creador)
 │   ├─ ColombiaProcessorFactory / MexicoProcessorFactory / ArgentinaProcessorFactory / ChileProcessorFactory
 │   └─ impl/                    # AbstractDocumentProcessor + procesadores concretos por país
 └─ service/BatchProcessingService.java   # Simulación de procesamiento por lotes (JavaFX Task)
src/main/resources/styles.css    # Tema oscuro (#0f172a), tarjetas, consola tipo terminal
```

## Patrón aplicado

- **Producto**: `DocumentProcessor`
- **Productos concretos**: `ColombiaDocumentProcessor`, `MexicoDocumentProcessor`, `ArgentinaDocumentProcessor`, `ChileDocumentProcessor`
- **Creador**: `DocumentProcessorFactory` (método fábrica `createProcessor()`)
- **Creadores concretos**: `ColombiaProcessorFactory`, `MexicoProcessorFactory`, `ArgentinaProcessorFactory`, `ChileProcessorFactory`
