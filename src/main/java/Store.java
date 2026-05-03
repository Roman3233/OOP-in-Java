import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Store {
    private final String name;
    private final LinkedHashMap<Clothes, Integer> quantities;

    public Store(String name) {
        this(name, Collections.emptyList());
    }

    public Store(String name, List<Clothes> clothes) {
        this.name = validateName(name);
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
        return Collections.unmodifiableList(new ArrayList<>(quantities.keySet()));
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

        quantities.put(item, quantities.getOrDefault(item, 0) + quantity);
    }

    public List<Clothes> findClothesByName(String query) {
        String normalizedQuery = normalizeText(query, "Search query");
        return findClothes(item -> item.getName().toLowerCase().contains(normalizedQuery));
    }

    public List<Clothes> findClothesBySize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }

        return findClothes(item -> item.getSize() == size);
    }

    public List<Clothes> findClothesByMaterial(String material) {
        String normalizedMaterial = normalizeText(material, "Material");
        return findClothes(item -> item.getMaterial().toLowerCase().contains(normalizedMaterial));
    }

    public List<Clothes> findClothesByMaximumPrice(double maximumPrice) {
        if (maximumPrice <= 0) {
            throw new IllegalArgumentException("Maximum price must be greater than 0.");
        }

        return findClothes(item -> item.getPrice() <= maximumPrice);
    }

    private List<Clothes> findClothes(Predicate<Clothes> filter) {
        List<Clothes> foundClothes = new ArrayList<>();

        for (Clothes item : quantities.keySet()) {
            if (filter.test(item)) {
                foundClothes.add(item);
            }
        }

        return foundClothes;
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
