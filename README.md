# GlobalDocs Solutions — Factory Method Pattern Workshop

Real-world case study: multinational processing of business documents
(Colombia, Mexico, Argentina, Chile) with per-country regulatory validation,
implemented with the **Factory Method** design pattern in **Java 21**.

## Run

```bash
mvn clean javafx:run
```

The app opens in dark mode by default; use the ☀️/🌙 button in the header to
switch between dark and light mode.

## Structure

```
src/main/java/com/globaldocs/factorymethod/
 ├─ MainApp.java                 # JavaFX GUI (dashboard, uses styles.css / styles-light.css)
 ├─ model/                       # Country, DocumentType, DocumentFormat, ProcessingResult (record)
 ├─ exception/                   # UnsupportedFormatException, RegulatoryValidationException
 ├─ factory/                     # DocumentProcessor (Product) + DocumentProcessorFactory (Creator)
 │   ├─ ColombiaProcessorFactory / MexicoProcessorFactory / ArgentinaProcessorFactory / ChileProcessorFactory
 │   └─ impl/                    # AbstractDocumentProcessor + concrete per-country processors
 └─ service/BatchProcessingService.java   # Batch processing simulation (JavaFX Task)
src/main/resources/
 ├─ styles.css                   # Dark theme (#0b1020), cards, activity feed
 └─ styles-light.css             # Light theme, same layout/classes
```

## Pattern applied

- **Product**: `DocumentProcessor`
- **Concrete products**: `ColombiaDocumentProcessor`, `MexicoDocumentProcessor`, `ArgentinaDocumentProcessor`, `ChileDocumentProcessor`
- **Creator**: `DocumentProcessorFactory` (factory method `createProcessor()`)
- **Concrete creators**: `ColombiaProcessorFactory`, `MexicoProcessorFactory`, `ArgentinaProcessorFactory`, `ChileProcessorFactory`
