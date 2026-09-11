package com.globaldocs.factorymethod;

import com.globaldocs.factorymethod.model.Country;
import com.globaldocs.factorymethod.model.DocumentFormat;
import com.globaldocs.factorymethod.model.DocumentType;
import com.globaldocs.factorymethod.model.ProcessingResult;
import com.globaldocs.factorymethod.service.BatchProcessingService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

/**
 * GlobalDocs Solutions GUI — Factory Method Pattern Workshop.
 * Lets the user pick a country, document type and format, and simulate
 * batch processing through {@link com.globaldocs.factorymethod.factory.DocumentProcessorFactory}.
 *
 * <p>Each processed document is rendered as a card in an activity feed
 * (dashboard style), not as plain-text console output.</p>
 */
public class MainApp extends Application {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final int MAX_ACTIVITY_ROWS = 300;

    private final BatchProcessingService batchService = new BatchProcessingService();
    private final Map<Country, ToggleButton> countryButtons = new EnumMap<>(Country.class);
    private final ToggleGroup countryGroup = new ToggleGroup();
    private final ObservableList<ProcessingResult> activityItems = FXCollections.observableArrayList();

    private ComboBox<DocumentType> typeCombo;
    private ComboBox<DocumentFormat> formatCombo;
    private Spinner<Integer> batchSizeSpinner;
    private Button startButton;
    private ProgressBar progressBar;
    private Label percentLabel;
    private Label progressSubtitle;
    private ListView<ProcessingResult> activityList;
    private Label totalValue;
    private Label successValue;
    private Label failedValue;
    private Label statusLabel;
    private Button themeToggleButton;
    private Scene scene;

    private int totalCount = 0;
    private int successCount = 0;
    private int failedCount = 0;
    private boolean darkMode = true;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        root.setTop(buildHeader());
        root.setLeft(buildSidebar());
        root.setCenter(buildCenter());
        root.setBottom(buildStatusBar());

        scene = new Scene(root, 1180, 760);
        applyTheme();

        stage.setTitle("GlobalDocs Solutions — Factory Method · Document Processing");
        stage.setScene(scene);
        stage.setMinWidth(980);
        stage.setMinHeight(640);
        stage.show();

        selectDefaultCountry();
    }

    // ----------------------------------------------------------------- THEME
    private void applyTheme() {
        String cssPath = darkMode ? "/styles.css" : "/styles-light.css";
        scene.getStylesheets().setAll(getClass().getResource(cssPath).toExternalForm());
        if (themeToggleButton != null) {
            themeToggleButton.setText(darkMode ? "☀️" : "🌙");
            themeToggleButton.setTooltip(new Tooltip(darkMode ? "Switch to light mode" : "Switch to dark mode"));
        }
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        applyTheme();
    }

    // ---------------------------------------------------------------- HEADER
    private HBox buildHeader() {
        StackPane brandMark = new StackPane();
        brandMark.getStyleClass().add("brand-mark");
        Label brandText = new Label("GD");
        brandText.getStyleClass().add("brand-mark-text");
        brandMark.getChildren().add(brandText);

        Label title = new Label("GlobalDocs Solutions");
        title.getStyleClass().add("header-title");

        Label subtitle = new Label("Multinational document processing · Factory Method Pattern (Java 21)");
        subtitle.getStyleClass().add("header-subtitle");

        VBox titleBox = new VBox(2, title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label badge = new Label("50,000+ documents/day · 4 countries");
        badge.getStyleClass().add("header-badge");

        themeToggleButton = new Button("☀️");
        themeToggleButton.getStyleClass().add("theme-toggle");
        themeToggleButton.setTooltip(new Tooltip("Switch to light mode"));
        themeToggleButton.setOnAction(e -> toggleTheme());

        HBox header = new HBox(14, brandMark, titleBox, spacer, badge, themeToggleButton);
        header.getStyleClass().add("header-bar");
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    // --------------------------------------------------------------- SIDEBAR
    private VBox buildSidebar() {
        Label countryLabel = new Label("COUNTRY OF ORIGIN");
        countryLabel.getStyleClass().add("section-label");

        VBox countryBox = new VBox(8);
        for (Country country : Country.values()) {
            countryBox.getChildren().add(buildCountryCard(country));
        }

        Label typeLabel = sectionLabel("DOCUMENT TYPE");
        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(DocumentType.values());
        typeCombo.getSelectionModel().selectFirst();
        typeCombo.setMaxWidth(Double.MAX_VALUE);

        Label formatLabel = sectionLabel("FILE FORMAT");
        formatCombo = new ComboBox<>();
        formatCombo.getItems().addAll(DocumentFormat.values());
        formatCombo.getSelectionModel().selectFirst();
        formatCombo.setMaxWidth(Double.MAX_VALUE);

        Label batchLabel = sectionLabel("BATCH SIZE");
        batchSizeSpinner = new Spinner<>(10, 2000, 50, 10);
        batchSizeSpinner.setEditable(true);
        batchSizeSpinner.setMaxWidth(Double.MAX_VALUE);

        startButton = new Button("▶  Start Batch Processing");
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
        Label flag = new Label(country.flagEmoji());
        flag.getStyleClass().add("flag-label");

        Label name = new Label(country.displayName());
        name.getStyleClass().add("country-name");

        Label regulation = new Label(country.regulation());
        regulation.getStyleClass().add("country-reg");
        regulation.setWrapText(true);

        VBox textBox = new VBox(1, name, regulation);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label checkBadge = new Label("✓");
        checkBadge.getStyleClass().add("check-badge");

        HBox content = new HBox(10, flag, textBox, spacer, checkBadge);
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
        // Prevents the user from deselecting every country card.
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
                buildMetricCard("BATCH TOTAL", "🗂", totalValueLabel(), "metric-total"),
                buildMetricCard("SUCCESSFUL", "✓", successValueLabel(), "metric-success"),
                buildMetricCard("FAILED", "✕", failedValueLabel(), "metric-failed"));
        for (var node : metrics.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }

        VBox progressCard = buildProgressCard();

        VBox activityFeed = buildActivityFeed();
        VBox.setVgrow(activityFeed, Priority.ALWAYS);

        VBox center = new VBox(18, metrics, progressCard, activityFeed);
        center.setPadding(new Insets(24));
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

    private VBox buildMetricCard(String label, String icon, Label valueLabel, String styleClass) {
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("metric-icon");

        Label caption = new Label(label);
        caption.getStyleClass().add("metric-label");

        VBox textBox = new VBox(4, caption, valueLabel);

        HBox content = new HBox(14, iconLabel, textBox);
        content.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(content);
        card.getStyleClass().addAll("metric-card", styleClass);
        return card;
    }

    private VBox buildProgressCard() {
        Label title = new Label("BATCH PROGRESS");
        title.getStyleClass().add("progress-title");

        progressSubtitle = new Label("Select a country, document type and format, then click Start.");
        progressSubtitle.getStyleClass().add("progress-subtitle");
        progressSubtitle.setWrapText(true);

        progressBar = new ProgressBar(0);
        progressBar.getStyleClass().add("batch-progress");
        progressBar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progressBar, Priority.ALWAYS);

        percentLabel = new Label("0%");
        percentLabel.getStyleClass().add("progress-chip");
        progressBar.progressProperty().addListener((obs, oldV, newV) ->
                percentLabel.setText(Math.round(newV.doubleValue() * 100) + "%"));

        HBox progressRow = new HBox(12, progressBar, percentLabel);
        progressRow.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(10, title, progressSubtitle, progressRow);
        card.getStyleClass().add("progress-card");
        return card;
    }

    private VBox buildActivityFeed() {
        Label title = new Label("Processing Activity");
        title.getStyleClass().add("activity-title");

        Region dot = new Region();
        dot.getStyleClass().add("activity-live-dot");

        Label liveLabel = new Label("LIVE");
        liveLabel.getStyleClass().add("log-meta-chip");

        HBox liveBox = new HBox(8, dot, liveLabel);
        liveBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, title, spacer, liveBox);
        header.getStyleClass().add("activity-header");
        header.setAlignment(Pos.CENTER_LEFT);

        activityList = new ListView<>(activityItems);
        activityList.getStyleClass().add("activity-list");
        activityList.setFocusTraversable(false);
        activityList.setCellFactory(list -> new ActivityCell());

        Label placeholder = new Label(
                "No documents processed yet.\nStart a batch to see live activity.");
        placeholder.getStyleClass().add("progress-subtitle");
        placeholder.setWrapText(true);
        placeholder.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        activityList.setPlaceholder(placeholder);
        VBox.setVgrow(activityList, Priority.ALWAYS);

        VBox wrapper = new VBox(header, activityList);
        wrapper.getStyleClass().add("activity-wrapper");
        return wrapper;
    }

    /** Cell that renders each result as an activity card (icon, title, detail, chip and time). */
    private class ActivityCell extends ListCell<ProcessingResult> {
        ActivityCell() {
            setMaxWidth(Double.MAX_VALUE);
            prefWidthProperty().bind(activityList.widthProperty().subtract(2));
        }

        @Override
        protected void updateItem(ProcessingResult result, boolean empty) {
            super.updateItem(result, empty);
            if (empty || result == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            boolean ok = result.success();

            Region accent = new Region();
            accent.getStyleClass().addAll("log-accent", ok ? "log-accent-success" : "log-accent-error");

            Label icon = new Label(ok ? "✓" : "✕");
            icon.getStyleClass().addAll("log-icon", ok ? "log-icon-success" : "log-icon-error");
            icon.setAlignment(Pos.CENTER);

            Label titleLabel = new Label(result.fileName());
            titleLabel.getStyleClass().add("log-title");

            String detail = ok
                    ? result.message() + " · Code: " + result.regulatoryCode()
                    : result.message();
            Label subtitleLabel = new Label(detail);
            subtitleLabel.getStyleClass().add("log-subtitle");
            subtitleLabel.setWrapText(true);

            VBox textBox = new VBox(2, titleLabel, subtitleLabel);
            HBox.setHgrow(textBox, Priority.ALWAYS);

            Label metaChip = new Label(result.type().displayName() + " · " + result.format().extension());
            metaChip.getStyleClass().add("log-meta-chip");

            Label timeLabel = new Label(result.timestamp().format(TIME_FORMAT));
            timeLabel.getStyleClass().add("log-time");

            VBox trailing = new VBox(6, metaChip, timeLabel);
            trailing.setAlignment(Pos.CENTER_RIGHT);

            HBox row = new HBox(12, accent, icon, textBox, trailing);
            row.getStyleClass().add("log-row");
            row.setAlignment(Pos.CENTER_LEFT);
            row.setMaxWidth(Double.MAX_VALUE);

            setGraphic(row);
            setText(null);
        }
    }

    // ------------------------------------------------------------ STATUS BAR
    private HBox buildStatusBar() {
        statusLabel = new Label("Ready. Select a country, document type and format to start a batch.");
        statusLabel.getStyleClass().add("status-text");
        HBox bar = new HBox(statusLabel);
        bar.getStyleClass().add("status-bar");
        return bar;
    }

    // ------------------------------------------------------------- PROCESSING
    private void startBatch() {
        Country country = getSelectedCountry();
        DocumentType type = typeCombo.getValue();
        DocumentFormat format = formatCombo.getValue();
        int batchSize = batchSizeSpinner.getValue();

        totalCount = 0;
        successCount = 0;
        failedCount = 0;
        refreshMetrics();
        activityItems.clear();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
        startButton.setDisable(true);

        progressSubtitle.setText("Processing %s (%s) for %s — %d documents queued…"
                .formatted(type.displayName(), format.extension(), country.displayName(), batchSize));
        statusLabel.setText("Processing batch for " + country.displayName() + "… · Regulation: " + country.regulation());

        Task<Void> task = batchService.createBatchTask(country, type, format, batchSize, this::onDocumentProcessed);
        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(e -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(1.0);
            startButton.setDisable(false);
            progressSubtitle.setText("Batch completed: %d successful, %d failed out of %d documents."
                    .formatted(successCount, failedCount, totalCount));
            statusLabel.setText("Last batch: %s · %s · %d documents (%d OK / %d ERROR)"
                    .formatted(country.displayName(), type.displayName(), totalCount, successCount, failedCount));
        });

        task.setOnFailed(e -> {
            progressBar.progressProperty().unbind();
            startButton.setDisable(false);
            progressSubtitle.setText("The batch was interrupted by an unexpected error: " + task.getException());
            statusLabel.setText("The batch finished with system errors.");
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

        activityItems.add(0, result);
        if (activityItems.size() > MAX_ACTIVITY_ROWS) {
            activityItems.remove(activityItems.size() - 1);
        }
    }

    private void refreshMetrics() {
        totalValue.setText(String.valueOf(totalCount));
        successValue.setText(String.valueOf(successCount));
        failedValue.setText(String.valueOf(failedCount));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
