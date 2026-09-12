
<div align="center">

# 🌎 GlobalDocs Solutions

### Factory Method · Document Processing System

<p>
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/JavaFX-21.0.2-007396?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven" />
  <img src="https://img.shields.io/badge/Pattern-Factory%20Method-blueviolet?style=for-the-badge" />
</p>

<p>
  <strong>Scalable document processing based on the Factory Method Design Pattern.</strong>
</p>

</div>

---

## 🚀 About the Project

**GlobalDocs Solutions** is a Java application designed to process
business documents according to the regulatory requirements of
different countries.

The system uses the **Factory Method Design Pattern** to create
country-specific document processors while keeping the application
scalable, maintainable and easy to extend.

### 🌎 Supported Countries

| Country | Processor |
|:---:|---|
| 🇨🇴 Colombia | `ColombiaDocumentProcessor` |
| 🇲🇽 Mexico | `MexicoDocumentProcessor` |
| 🇦🇷 Argentina | `ArgentinaDocumentProcessor` |
| 🇨🇱 Chile | `ChileDocumentProcessor` |

---

## 🧰 Tech Stack

| Technology | Version | Role |
|---|---:|---|
| ☕ Java | `21` | Backend / Core Logic |
| 🎨 JavaFX | `21.0.2` | Desktop UI |
| 📦 Maven | — | Build & Dependencies |
| 🎨 CSS | — | UI Styling |
| 🏭 Factory Method | — | Design Pattern |

---

## 🏗️ Architecture

```text
GlobalDocs Solutions
│
├── 🖥️ JavaFX Interface
│
├── ⚙️ Business Logic
│
├── 🏭 Factory Method
│   │
│   ├── Colombia Factory
│   ├── Mexico Factory
│   ├── Argentina Factory
│   └── Chile Factory
│
├── 📄 Document Processors
│
├── 🔐 Regulatory Validation
│
└── 📊 Processing Results
````

---

## 🏭 Factory Method Pattern

The main design pattern implemented in this project is:

```text
                    DocumentProcessor
                           │
                           │
                 ┌─────────┴─────────┐
                 │                   │
        DocumentProcessor      createProcessor()
             Factory
                 │
        ┌────────┼────────┬──────────┐
        ▼        ▼        ▼          ▼
    Colombia   Mexico  Argentina   Chile
     Factory   Factory   Factory    Factory
        │        │        │          │
        ▼        ▼        ▼          ▼
   Colombia   Mexico  Argentina    Chile
   Processor  Processor Processor  Processor
```

### Product

```java
DocumentProcessor
```

Defines the common behavior for document processors.

### Concrete Products

```java
ColombiaDocumentProcessor
MexicoDocumentProcessor
ArgentinaDocumentProcessor
ChileDocumentProcessor
```

Each implementation contains the processing logic for its country.

### Creator

```java
DocumentProcessorFactory
```

Responsible for defining the Factory Method.

### Concrete Creators

```java
ColombiaProcessorFactory
MexicoProcessorFactory
ArgentinaProcessorFactory
ChileProcessorFactory
```

Each factory creates its corresponding processor.

---

## 📂 Project Structure

```text
Factory-Method/
│
├── 📁 src/
│   └── 📁 main/
│       │
│       ├── 📁 java/
│       │   └── 📁 com/
│       │       └── 📁 globaldocs/
│       │           └── 📁 factorymethod/
│       │
│       │               ├── MainApp.java
│       │               │
│       │               ├── 📁 factory/
│       │               │   ├── DocumentProcessor.java
│       │               │   ├── DocumentProcessorFactory.java
│       │               │   ├── ColombiaProcessorFactory.java
│       │               │   ├── MexicoProcessorFactory.java
│       │               │   ├── ArgentinaProcessorFactory.java
│       │               │   ├── ChileProcessorFactory.java
│       │               │   │
│       │               │   └── 📁 impl/
│       │               │       ├── AbstractDocumentProcessor.java
│       │               │       ├── ColombiaDocumentProcessor.java
│       │               │       ├── MexicoDocumentProcessor.java
│       │               │       ├── ArgentinaDocumentProcessor.java
│       │               │       └── ChileDocumentProcessor.java
│       │               │
│       │               ├── 📁 model/
│       │               │   ├── Country.java
│       │               │   ├── DocumentType.java
│       │               │   ├── DocumentFormat.java
│       │               │   └── ProcessingResult.java
│       │               │
│       │               ├── 📁 service/
│       │               │   └── BatchProcessingService.java
│       │               │
│       │               └── 📁 exception/
│       │                   ├── RegulatoryValidationException.java
│       │                   └── UnsupportedFormatException.java
│       │
│       └── 📁 resources/
│           ├── styles.css
│           └── styles-light.css
│
├── 📄 pom.xml
├── 📄 README.md
└── 📄 .gitignore
```

---

## ⚡ Application Flow

```text
┌──────────────────────┐
│     User Interface   │
│       JavaFX         │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│   Select Country     │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│  Processor Factory   │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│    Factory Method    │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Document Processor   │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Validation & Rules   │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Processing Result    │
└──────────────────────┘
```

---

## ✨ Features

* 🌎 Multi-country document processing
* 🏭 Factory Method implementation
* 📄 Multiple document types
* 🔐 Regulatory validation
* ⚠️ Custom exception handling
* 📊 Batch document processing
* 🎨 JavaFX graphical interface
* 🌙 Dark mode
* ☀️ Light mode
* 🧩 Modular architecture
* 📈 Easy country expansion

---

## ▶️ Getting Started

### Requirements

Make sure you have installed:

```text
Java 21+
Maven
```

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

---

## 📥 Clone the Repository

```bash
git clone https://github.com/ssantivr/Factory-Method.git
```

```bash
cd Factory-Method
```

---

## ▶️ Run the Application

```bash
mvn clean javafx:run
```

---

## 📦 Build the Project

```bash
mvn clean package
```

The generated files will be available inside:

```text
target/
```

---

## 🌱 Extensibility

One of the main advantages of the architecture is that new countries
can be added without modifying the existing processors.

For example:

```text
New Country
     │
     ▼
NewProcessorFactory
     │
     ▼
NewDocumentProcessor
```

This makes the system easier to maintain and extend.

---

## 🎯 Project Goals

This project demonstrates:

```text
✓ Object-Oriented Programming
✓ Design Patterns
✓ Factory Method
✓ Encapsulation
✓ Abstraction
✓ Polymorphism
✓ Exception Handling
✓ Modular Architecture
✓ JavaFX Development
✓ Maven Project Management
```

---

## 👨‍💻 Author

**Santi**

🎓 Academic Project
💻 Java Development
🏭 Factory Method Pattern

---

<div align="center">

### ⭐ If you find this project useful, consider giving it a star!

**GlobalDocs Solutions · Factory Method**

</div>
```

Con eso el README renovado aparecerá directamente en tu repositorio. 🚀
