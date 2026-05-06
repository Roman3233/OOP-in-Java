import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Зберігає об'єкти одягу у текстовому файлі та відновлює їх під час запуску програми.
 */
public class ClothesFileStorage {
    private static final String SEPARATOR = ";";

    private final Path storagePath;

    /**
     * Ініціалізує сховище та перевіряє коректність шляху до файлу.
     *
     * @param filePath шлях до файлу, де будуть зберігатися записи про одяг
     */
    public ClothesFileStorage(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Storage path cannot be null or empty.");
        }

        try {
            this.storagePath = Paths.get(filePath.trim());
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Invalid storage path: " + filePath, e);
        }
    }

    /**
     * Зчитує всі записи з файлу, перетворює кожен рядок на об'єкт {@code Clothes}
     * та повертає повний список одягу.
     *
     * @return список одягу з файлу або порожній список, якщо файл ще не існує
     */
    public List<Clothes> loadClothes() {
        if (Files.notExists(storagePath)) {
            return new ArrayList<>();
        }

        try {
            List<String> lines = Files.readAllLines(storagePath, StandardCharsets.UTF_8);
            List<Clothes> clothesList = new ArrayList<>();

            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                clothesList.add(parseLine(line));
            }

            return clothesList;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read clothes from file: " + storagePath, e);
        }
    }

    /**
     * Додає один об'єкт одягу в кінець файлу збереження.
     * Якщо батьківської директорії не існує, вона створюється автоматично.
     *
     * @param clothes об'єкт одягу, який потрібно зберегти
     */
    public void appendClothes(Clothes clothes) {
        if (clothes == null) {
            throw new IllegalArgumentException("Clothes cannot be null.");
        }

        try {
            Path parent = storagePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.write(
                    storagePath,
                    Collections.singletonList(serialize(clothes)),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save clothes to file: " + storagePath, e);
        }
    }

    public boolean updateClothes(Clothes existingClothes, Clothes newClothes) {
        if (existingClothes == null) {
            throw new IllegalArgumentException("Existing clothes cannot be null.");
        }
        if (newClothes == null) {
            throw new IllegalArgumentException("New clothes cannot be null.");
        }

        List<Clothes> clothesList = loadClothes();
        boolean updated = false;

        for (int index = 0; index < clothesList.size(); index++) {
            Clothes currentClothes = clothesList.get(index);
            if (currentClothes.equals(existingClothes)) {
                clothesList.set(index, newClothes);
                updated = true;
            }
        }

        if (!updated) {
            return false;
        }

        overwriteClothes(clothesList);
        return true;
    }

    public boolean deleteClothes(Clothes existingClothes) {
        if (existingClothes == null) {
            throw new IllegalArgumentException("Existing clothes cannot be null.");
        }

        List<Clothes> clothesList = loadClothes();
        List<Clothes> remainingClothes = new ArrayList<>();
        boolean deleted = false;

        for (Clothes clothes : clothesList) {
            if (clothes.equals(existingClothes)) {
                deleted = true;
                continue;
            }

            remainingClothes.add(clothes);
        }

        if (!deleted) {
            return false;
        }

        overwriteClothes(remainingClothes);
        return true;
    }

    /**
     * Розбирає один рядок із файлу та створює відповідний об'єкт конкретного типу одягу.
     *
     * @param line рядок із серіалізованими даними
     * @return створений об'єкт одягу
     */
    private Clothes parseLine(String line) {
        String[] parts = line.split(SEPARATOR, -1);
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid record format: " + line);
        }

        String type = parts[0];
        String name = parts[1];
        Size size = Size.fromString(parts[2]);
        double price = Double.parseDouble(parts[3]);
        String material = parts[4];
        String extraValue = parts[5];

        return switch (type) {
            case "Pants" -> new Pants(name, size, price, material, Double.parseDouble(extraValue));
            case "Shirt" -> new Shirts(name, size, price, material, Double.parseDouble(extraValue));
            case "Jacket" -> new Jacket(name, size, price, material, Integer.parseInt(extraValue));
            case "Hat" -> new Hat(name, size, price, material, Double.parseDouble(extraValue));
            default -> throw new IllegalArgumentException("Unknown clothes type: " + type);
        };
    }

    /**
     * Перетворює об'єкт одягу на рядок для збереження у файлі.
     * У результат додаються як спільні поля, так і специфічна характеристика типу.
     *
     * @param clothes об'єкт одягу для серіалізації
     * @return текстове представлення об'єкта для запису у файл
     */
    private String serialize(Clothes clothes) {
        List<String> parts = new ArrayList<>();
        parts.add(clothes.getType());
        parts.add(clothes.getName());
        parts.add(clothes.getSize().name());
        parts.add(String.valueOf(clothes.getPrice()));
        parts.add(clothes.getMaterial());

        if (clothes instanceof Pants pants) {
            parts.add(String.valueOf(pants.getWaistSize()));
        } else if (clothes instanceof Shirts shirts) {
            parts.add(String.valueOf(shirts.getSleeveLength()));
        } else if (clothes instanceof Jacket jacket) {
            parts.add(String.valueOf(jacket.getPocketCount()));
        } else if (clothes instanceof Hat hat) {
            parts.add(String.valueOf(hat.getBrimWidth()));
        } else {
            throw new IllegalArgumentException("Unsupported clothes type: " + clothes.getClass().getSimpleName());
        }

        return String.join(SEPARATOR, parts);
    }

    private void overwriteClothes(List<Clothes> clothesList) {
        try {
            Path parent = storagePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> serializedClothes = new ArrayList<>();
            for (Clothes clothes : clothesList) {
                serializedClothes.add(serialize(clothes));
            }

            Files.write(
                    storagePath,
                    serializedClothes,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to update clothes in file: " + storagePath, e);
        }
    }
}
