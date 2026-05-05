import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

/**
 * JavaFX-застосунок для роботи з колекцією одягу.
 * Дозволяє додавати нові об'єкти, переглядати колекцію у скороченому вигляді
 * та шукати елементи за UUID.
 */
public class MainApp extends Application {
    private static final String STORAGE_PATH_PROPERTY = "clothes.storage.path";
    private static final String DEFAULT_STORAGE_PATH = "input.txt";

    private final StoreService storeService = new StoreService(
            new Store("Clothes store"),
            new ClothesFileStorage(resolveStoragePath())
    );

    private TextField nameField;
    private ComboBox<Size> sizeComboBox;
    private TextField priceField;
    private TextField materialField;
    private ComboBox<String> typeComboBox;
    private Label extraFieldLabel;
    private TextField extraField;
    private TextArea collectionArea;
    private TextField uuidField;
    private TextArea detailsArea;
    private Label statusLabel;

    /**
     * Створює та відображає головне вікно застосунку.
     *
     * @param stage головна сцена JavaFX
     */
    @Override
    public void start(Stage stage) {
        storeService.loadFromStorage();

        stage.setTitle("Clothes Store");
        stage.setScene(new Scene(createRoot(), 900, 620));
        stage.show();

        refreshCollectionView();
    }

    /**
     * Формує кореневий контейнер головного вікна.
     *
     * @return контейнер з усіма секціями інтерфейсу
     */
    private BorderPane createRoot() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        VBox content = new VBox(16, createAddSection(), createSearchSection());
        root.setTop(content);
        root.setCenter(createCollectionSection());

        return root;
    }

    /**
     * Створює секцію додавання нового одягу.
     *
     * @return контейнер з формою створення об'єкта
     */
    private VBox createAddSection() {
        Label title = new Label("Add clothes");

        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Pants", "Shirt", "Jacket", "Hat");
        typeComboBox.setValue("Pants");
        typeComboBox.setOnAction(event -> updateExtraFieldLabel());

        nameField = new TextField();
        sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(Size.values());
        sizeComboBox.setValue(Size.M);
        priceField = new TextField();
        materialField = new TextField();
        extraFieldLabel = new Label();
        extraField = new TextField();
        updateExtraFieldLabel();

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        addFormRow(formGrid, 0, "Type:", typeComboBox);
        addFormRow(formGrid, 1, "Name:", nameField);
        addFormRow(formGrid, 2, "Size:", sizeComboBox);
        addFormRow(formGrid, 3, "Price:", priceField);
        addFormRow(formGrid, 4, "Material:", materialField);
        addFormRow(formGrid, 5, extraFieldLabel, extraField);

        Button addButton = new Button("Додати");
        addButton.setOnAction(event -> handleAddClothes());

        statusLabel = new Label();

        VBox section = new VBox(10, title, formGrid, addButton, statusLabel);
        section.setPadding(new Insets(12));
        section.setStyle("-fx-border-color: lightgray; -fx-border-radius: 6; -fx-background-radius: 6;");
        return section;
    }

    /**
     * Створює секцію пошуку об'єкта за UUID.
     *
     * @return контейнер з полем пошуку та блоком результату
     */
    private VBox createSearchSection() {
        Label title = new Label("Search by UUID");

        uuidField = new TextField();
        uuidField.setPromptText("Enter UUID");

        Button findButton = new Button("Знайти");
        findButton.setOnAction(event -> handleFindByUuid());

        HBox searchBox = new HBox(10, new Label("UUID:"), uuidField, findButton);
        HBox.setHgrow(uuidField, Priority.ALWAYS);

        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefRowCount(5);

        VBox section = new VBox(10, title, searchBox, detailsArea);
        section.setPadding(new Insets(12));
        section.setStyle("-fx-border-color: lightgray; -fx-border-radius: 6; -fx-background-radius: 6;");
        return section;
    }

    /**
     * Створює секцію для відображення всієї колекції у короткому форматі.
     *
     * @return контейнер зі списком об'єктів
     */
    private VBox createCollectionSection() {
        Label title = new Label("Collection");

        collectionArea = new TextArea();
        collectionArea.setEditable(false);
        collectionArea.setWrapText(true);

        VBox section = new VBox(10, title, collectionArea);
        VBox.setVgrow(collectionArea, Priority.ALWAYS);
        section.setPadding(new Insets(12));
        section.setStyle("-fx-border-color: lightgray; -fx-border-radius: 6; -fx-background-radius: 6;");
        return section;
    }

    /**
     * Обробляє натискання кнопки додавання одягу.
     * Створює об'єкт з даних форми, зберігає його та оновлює список.
     */
    private void handleAddClothes() {
        try {
            Clothes clothes = createClothesFromForm();
            storeService.addClothes(clothes);
            refreshCollectionView();
            clearAddForm();
            statusLabel.setText("Added: " + formatShortClothes(clothes));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            statusLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Обробляє пошук об'єкта за введеним UUID.
     * Показує повну інформацію про знайдений елемент або повідомлення про помилку.
     */
    private void handleFindByUuid() {
        try {
            UUID uuid = UUID.fromString(uuidField.getText().trim());
            Clothes foundClothes = storeService.findClothesByUuid(uuid);

            if (foundClothes == null) {
                detailsArea.setText("не знайдено");
                return;
            }

            detailsArea.setText(foundClothes.toString());
        } catch (IllegalArgumentException exception) {
            detailsArea.setText("Помилка: некоректний UUID");
        }
    }

    /**
     * Створює екземпляр {@link Clothes} відповідного типу на основі значень форми.
     *
     * @return новий об'єкт одягу
     * @throws IllegalArgumentException якщо значення полів некоректні
     */
    private Clothes createClothesFromForm() {
        String name = nameField.getText();
        Size size = sizeComboBox.getValue();
        double price = Double.parseDouble(priceField.getText().trim());
        String material = materialField.getText();
        String extraValue = extraField.getText().trim();

        return switch (typeComboBox.getValue()) {
            case "Pants" -> new Pants(name, size, price, material, Double.parseDouble(extraValue));
            case "Shirt" -> new Shirts(name, size, price, material, Double.parseDouble(extraValue));
            case "Jacket" -> new Jacket(name, size, price, material, Integer.parseInt(extraValue));
            case "Hat" -> new Hat(name, size, price, material, Double.parseDouble(extraValue));
            default -> throw new IllegalArgumentException("Unsupported clothes type.");
        };
    }

    /**
     * Оновлює блок короткого відображення всієї колекції.
     */
    private void refreshCollectionView() {
        List<Clothes> clothesList = storeService.getAllClothes();
        if (clothesList.isEmpty()) {
            collectionArea.setText("Collection is empty.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (Clothes clothes : clothesList) {
            if (!builder.isEmpty()) {
                builder.append(System.lineSeparator());
            }
            builder.append(formatShortClothes(clothes));
        }

        collectionArea.setText(builder.toString());
    }

    /**
     * Формує короткий рядок для відображення об'єкта у списку колекції.
     *
     * @param clothes об'єкт одягу
     * @return рядок у форматі "тип: назва | UUID: ..."
     */
    private String formatShortClothes(Clothes clothes) {
        return clothes.getType() + ": " + clothes.getName() + " | UUID: " + clothes.getUuid();
    }

    /**
     * Очищає форму після успішного додавання нового елемента.
     */
    private void clearAddForm() {
        nameField.clear();
        sizeComboBox.setValue(Size.M);
        priceField.clear();
        materialField.clear();
        extraField.clear();
    }

    /**
     * Оновлює підпис специфічного поля залежно від обраного типу одягу.
     */
    private void updateExtraFieldLabel() {
        String extraLabelText = switch (typeComboBox.getValue()) {
            case "Pants" -> "Waist size:";
            case "Shirt" -> "Sleeve length:";
            case "Jacket" -> "Pocket count:";
            case "Hat" -> "Brim width:";
            default -> "Extra value:";
        };
        extraFieldLabel.setText(extraLabelText);
    }

    /**
     * Додає рядок форми, створюючи підпис з тексту.
     *
     * @param grid контейнер-сітка
     * @param rowIndex індекс рядка
     * @param labelText текст підпису
     * @param input елемент вводу
     */
    private void addFormRow(GridPane grid, int rowIndex, String labelText, javafx.scene.Node input) {
        addFormRow(grid, rowIndex, new Label(labelText), input);
    }

    /**
     * Додає готовий рядок форми до сітки.
     *
     * @param grid контейнер-сітка
     * @param rowIndex індекс рядка
     * @param label підпис поля
     * @param input елемент вводу
     */
    private void addFormRow(GridPane grid, int rowIndex, Label label, javafx.scene.Node input) {
        grid.add(label, 0, rowIndex);
        grid.add(input, 1, rowIndex);
        GridPane.setHgrow(input, Priority.ALWAYS);
    }

    /**
     * Визначає шлях до файлу сховища з системної властивості або використовує типовий.
     *
     * @return шлях до файлу сховища
     */
    private static String resolveStoragePath() {
        return System.getProperty(STORAGE_PATH_PROPERTY, DEFAULT_STORAGE_PATH);
    }
}
