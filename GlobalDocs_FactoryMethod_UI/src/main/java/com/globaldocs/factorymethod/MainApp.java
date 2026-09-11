package com.globaldocs.factorymethod;

import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;
import com.globaldocs.factorymethod.service.BatchProcessingService;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;

/**
 * GUI de GlobalDocs Solutions — Taller Patrón Factory Method.
 * Permite elegir país, tipo de documento y formato, y simular el
 * procesamiento por lotes usando {@link com.globaldocs.factorymethod.factory.DocumentProcessorFactory}.
 */
public class MainApp extends Application {

    private final BatchProcessingService batchService = new BatchProcessingService();
    private final Map<Country, ToggleButton> countryButtons = new EnumMap<>(Country.class);
    private final ToggleGroup countryGroup = new ToggleGroup();

    private ComboBox<DocumentType> typeCombo;
    private ComboBox<DocumentFormat> formatCombo;
    private Spinner<Integer> batchSizeSpinner;
    private Button startButton;
    private ProgressBar progressBar;
    private Label percentLabel;
    private TextArea consoleArea;
    private Label totalValue;
    private Label successValue;
    private Label failedValue;
    private Label statusLabel;

    private int totalCount = 0;
    private int successCount = 0;
    private int failedCount = 0;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        root.setTop(buildHeader());
        root.setLeft(buildSidebar());
        root.setCenter(buildCenter());
        root.setBottom(buildStatusBar());

        Scene scene = new Scene(root, 1180, 760);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("GlobalDocs Solutions — Factory Method · Procesamiento de Documentos");
        stage.setScene(scene);
        stage.setMinWidth(980);
        stage.setMinHeight(640);
        stage.show();

        selectDefaultCountry();
        appendLog("Sistema GlobalDocs Solutions iniciado · Fábricas disponibles: Colombia, México, Argentina, Chile");
    }

    // ---------------------------------------------------------------- HEADER
    private HBox buildHeader() {
        Label title = new Label("GlobalDocs Solutions");
        title.getStyleClass().add("header-title");

        Label subtitle = new Label("Procesamiento multinacional de documentos · Patrón Factory Method (Java 21)");
        subtitle.getStyleClass().add("header-subtitle");

        VBox titleBox = new VBox(2, title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label badge = new Label("50,000+ documentos/día · 4 países");
        badge.getStyleClass().add("header-badge");

        HBox header = new HBox(12, titleBox, spacer, badge);
        header.getStyleClass().add("header-bar");
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    // --------------------------------------------------------------- SIDEBAR
    private VBox buildSidebar() {
        Label countryLabel = new Label("PAÍS DE ORIGEN");
        countryLabel.getStyleClass().add("section-label");

        VBox countryBox = new VBox(8);
        for (Country country : Country.values()) {
            countryBox.getChildren().add(buildCountryCard(country));
        }

        Label typeLabel = sectionLabel("TIPO DE DOCUMENTO");
        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(DocumentType.values());
        typeCombo.getSelectionModel().selectFirst();
        typeCombo.setMaxWidth(Double.MAX_VALUE);

        Label formatLabel = sectionLabel("FORMATO DE ARCHIVO");
        formatCombo = new ComboBox<>();
        formatCombo.getItems().addAll(DocumentFormat.values());
        formatCombo.getSelectionModel().selectFirst();
        formatCombo.setMaxWidth(Double.MAX_VALUE);

        Label batchLabel = sectionLabel("TAMAÑO DEL LOTE");
        batchSizeSpinner = new Spinner<>(10, 2000, 50, 10);
        batchSizeSpinner.setEditable(true);
        batchSizeSpinner.setMaxWidth(Double.MAX_VALUE);

        startButton = new Button("▶  Iniciar Procesamiento por Lotes");
        startButton.getStyleClass().add("primary-button");
        startButton.setMaxWidth(Double.MAX_VALUE);
        startButton.setOnAction(e -> startBatch());

        VBox sidebar = new VBox(10,
                countryLabel, countryBox,
                spacerLine(),
                typeLabel, typeCombo,
                formatLabel, formatCombo,
                batchLabel, batchSizeSpinner,
                spacerLine(),
                startButton);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(300);
        sidebar.setMinWidth(280);
        return sidebar;
    }

    private Label sectionLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("section-label");
        VBox.setMargin(label, new Insets(6, 0, 0, 0));
        return label;
    }

    private Region spacerLine() {
        Region region = new Region();
        region.setPrefHeight(1);
        return region;
    }

    private ToggleButton buildCountryCard(Country country) {
        Label flag = new Label(country.isoCode());
        flag.getStyleClass().add("flag-label");

        Label name = new Label(country.displayName());
        name.getStyleClass().add("country-name");

        Label regulation = new Label(country.regulation());
        regulation.getStyleClass().add("country-reg");
        regulation.setWrapText(true);

        VBox textBox = new VBox(1, name, regulation);

        HBox content = new HBox(10, flag, textBox);
        content.setAlignment(Pos.CENTER_LEFT);

        ToggleButton button = new ToggleButton();
        button.setGraphic(content);
        button.setToggleGroup(countryGroup);
        button.setUserData(country);
        button.getStyleClass().add("country-card");
        button.setMaxWidth(Double.MAX_VALUE);
        countryButtons.put(country, button);
        return button;
    }

    private void selectDefaultCountry() {
        countryGroup.selectToggle(countryButtons.get(Country.COLOMBIA));
        // Evita que el usuario deseleccione todas las tarjetas de país.
        countryGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null && oldToggle != null) {
                countryGroup.selectToggle(oldToggle);
            }
        });
    }

    private Country getSelectedCountry() {
        Toggle toggle = countryGroup.getSelectedToggle();
        return toggle != null ? (Country) toggle.getUserData() : Country.COLOMBIA;
    }

    // ---------------------------------------------------------------- CENTER
    private VBox buildCenter() {
        HBox metrics = new HBox(16,
                buildMetricCard("TOTAL DEL LOTE", totalValueLabel(), "metric-total"),
                buildMetricCard("EXITOSOS", successValueLabel(), "metric-success"),
                buildMetricCard("FALLIDOS", failedValueLabel(), "metric-failed"));
        for (var node : metrics.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }

        progressBar = new ProgressBar(0);
        progressBar.getStyleClass().add("batch-progress");
        progressBar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progressBar, Priority.ALWAYS);

        percentLabel = new Label("0%");
        percentLabel.getStyleClass().add("progress-percent");
        progressBar.progressProperty().addListener((obs, oldV, newV) ->
                percentLabel.setText(Math.round(newV.doubleValue() * 100) + "%"));

        HBox progressBox = new HBox(12, progressBar, percentLabel);
        progressBox.setAlignment(Pos.CENTER_LEFT);

        VBox consolePanel = buildConsole();
        VBox.setVgrow(consolePanel, Priority.ALWAYS);

        VBox center = new VBox(18, metrics, progressBox, consolePanel);
        center.setPadding(new Insets(24));
        VBox.setVgrow(consolePanel, Priority.ALWAYS);
        return center;
    }

    private Label totalValueLabel() {
        totalValue = new Label("0");
        totalValue.getStyleClass().add("metric-value");
        return totalValue;
    }

    private Label successValueLabel() {
        successValue = new Label("0");
        successValue.getStyleClass().add("metric-value");
        return successValue;
    }

    private Label failedValueLabel() {
        failedValue = new Label("0");
        failedValue.getStyleClass().add("metric-value");
        return failedValue;
    }

    private VBox buildMetricCard(String label, Label valueLabel, String styleClass) {
        Label caption = new Label(label);
        caption.getStyleClass().add("metric-label");
        VBox card = new VBox(6, caption, valueLabel);
        card.getStyleClass().addAll("metric-card", styleClass);
        return card;
    }

    private VBox buildConsole() {
        Circle red = new Circle(5, Color.web("#f87171"));
        Circle yellow = new Circle(5, Color.web("#fbbf24"));
        Circle green = new Circle(5, Color.web("#34d399"));
        HBox dots = new HBox(6, red, yellow, green);
        dots.setAlignment(Pos.CENTER_LEFT);

        Label consoleTitle = new Label("GLOBALDOCS · LOG DE VALIDACIÓN REGULATORIA");
        consoleTitle.getStyleClass().add("console-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox consoleHeader = new HBox(10, dots, spacer, consoleTitle);
        consoleHeader.getStyleClass().add("console-header");
        consoleHeader.setAlignment(Pos.CENTER_LEFT);

        consoleArea = new TextArea();
        consoleArea.getStyleClass().add("console-area");
        consoleArea.setEditable(false);
        consoleArea.setWrapText(false);
        consoleArea.setFont(Font.font("Consolas", 12));
        VBox.setVgrow(consoleArea, Priority.ALWAYS);

        VBox wrapper = new VBox(consoleHeader, consoleArea);
        wrapper.getStyleClass().add("console-wrapper");
        VBox.setVgrow(consoleArea, Priority.ALWAYS);
        return wrapper;
    }

    // ------------------------------------------------------------ STATUS BAR
    private HBox buildStatusBar() {
        statusLabel = new Label("Listo. Selecciona un país, tipo de documento y formato para iniciar un lote.");
        statusLabel.getStyleClass().add("status-text");
        HBox bar = new HBox(statusLabel);
        bar.getStyleClass().add("status-bar");
        return bar;
    }

    // ----------------------------------------------------------- PROCESAMIENTO
    private void startBatch() {
        Country country = getSelectedCountry();
        DocumentType type = typeCombo.getValue();
        DocumentFormat format = formatCombo.getValue();
        int batchSize = batchSizeSpinner.getValue();

        totalCount = 0;
        successCount = 0;
        failedCount = 0;
        refreshMetrics();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
        startButton.setDisable(true);

        appendLog("──────────────────────────────────────────────────────────────────");
        appendLog("Lote iniciado → País: %s | Tipo: %s | Formato: %s | Documentos: %d"
                .formatted(country.displayName(), type.displayName(), format.extension(), batchSize));
        appendLog("Normativa aplicable: " + country.regulation());
        statusLabel.setText("Procesando lote para " + country.displayName() + "…");

        Task<Void> task = batchService.createBatchTask(country, type, format, batchSize, this::onDocumentProcessed);
        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(e -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(1.0);
            startButton.setDisable(false);
            appendLog("Lote finalizado → %d exitosos, %d fallidos de %d documentos".formatted(
                    successCount, failedCount, totalCount));
            appendLog("──────────────────────────────────────────────────────────────────");
            statusLabel.setText("Último lote: %s · %s · %d documentos (%d OK / %d ERROR)"
                    .formatted(country.displayName(), type.displayName(), totalCount, successCount, failedCount));
        });

        task.setOnFailed(e -> {
            progressBar.progressProperty().unbind();
            startButton.setDisable(false);
            appendLog("El lote se interrumpió por un error inesperado: " + task.getException());
            statusLabel.setText("El lote finalizó con errores del sistema.");
        });

        Thread worker = new Thread(task, "globaldocs-batch-processing");
        worker.setDaemon(true);
        worker.start();
    }

    private void onDocumentProcessed(ProcessingResult result) {
        totalCount++;
        if (result.success()) {
            successCount++;
        } else {
            failedCount++;
        }
        refreshMetrics();
        appendLog(result.toLogLine());
    }

    private void refreshMetrics() {
        totalValue.setText(String.valueOf(totalCount));
        successValue.setText(String.valueOf(successCount));
        failedValue.setText(String.valueOf(failedCount));
    }

    private void appendLog(String line) {
        consoleArea.appendText(line + System.lineSeparator());
        consoleArea.setScrollTop(Double.MAX_VALUE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
