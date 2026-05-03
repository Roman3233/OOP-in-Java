import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Store {
    private final String name;
    private final ArrayList<Clothes> clothes;
    private final Map<Clothes, Integer> quantities;

    public Store(String name) {
        this(name, Collections.emptyList());
    }

    public Store(String name, List<Clothes> clothes) {
        this.name = validateName(name);
        this.clothes = new ArrayList<>();
        this.quantities = new LinkedHashMap<>();

        if (clothes != null) {
            for (Clothes item : clothes) {
                addNewClothes(item, 1);
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<Clothes> getClothes() {
        return Collections.unmodifiableList(clothes);
    }

    public Map<Clothes, Integer> getQuantities() {
        return Collections.unmodifiableMap(quantities);
    }

    public int getQuantity(Clothes item) {
        return quantities.getOrDefault(item, 0);
    }

    public void addNewClothes(Clothes item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Clothes item cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        Clothes existingItem = findExistingClothes(item);
        if (existingItem == null) {
            clothes.add(item);
            quantities.put(item, quantity);
            return;
        }

        quantities.put(existingItem, quantities.get(existingItem) + quantity);
    }

    public List<Clothes> findClothesByName(String query) {
        String normalizedQuery = normalizeText(query, "Search query");
        List<Clothes> foundClothes = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getName().toLowerCase().contains(normalizedQuery)) {
                foundClothes.add(item);
            }
        }

        return foundClothes;
    }

    public List<Clothes> findClothesBySize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }

        List<Clothes> foundClothes = new ArrayList<>();
        for (Clothes item : clothes) {
            if (item.getSize() == size) {
                foundClothes.add(item);
            }
        }

        return foundClothes;
    }

    public List<Clothes> findClothesByMaterial(String material) {
        String normalizedMaterial = normalizeText(material, "Material");
        List<Clothes> foundClothes = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getMaterial().toLowerCase().contains(normalizedMaterial)) {
                foundClothes.add(item);
            }
        }

        return foundClothes;
    }

    private Clothes findExistingClothes(Clothes item) {
        for (Clothes currentItem : clothes) {
            if (Objects.equals(currentItem, item)) {
                return currentItem;
            }
        }

        return null;
    }

    private String normalizeText(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }

        String normalizedValue = value.trim().toLowerCase();
        if (normalizedValue.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }

        return normalizedValue;
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name cannot be null or empty.");
        }
        return name.trim();
    }
}
