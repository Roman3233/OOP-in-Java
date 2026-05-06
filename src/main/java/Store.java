import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Магазин одягу, який зберігає перелік товарів та їх кількість.
 * <p>
 * Для кожного об'єкта {@link Clothes} ведеться лічильник кількості у наявності.
 */
public class Store {
    private final String name;
    private final LinkedHashMap<Clothes, Integer> quantities;

    /**
     * Створює магазин з порожнім асортиментом.
     *
     * @param name назва магазину
     * @throws IllegalArgumentException якщо {@code name} є {@code null} або порожнім
     */
    public Store(String name) {
        this(name, Collections.emptyList());
    }

    /**
     * Створює магазин та ініціалізує асортимент.
     * Якщо у списку є дублікати, вони агрегуються (кількість збільшується).
     *
     * @param name назва магазину
     * @param clothes початковий список одягу (може бути {@code null})
     * @throws IllegalArgumentException якщо {@code name} є {@code null} або порожнім
     */
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

    /**
     * Повертає список унікальних позицій одягу, що є у магазині.
     *
     * @return незмінний список унікальних позицій
     */
    public List<Clothes> getClothes() {
        return Collections.unmodifiableList(new ArrayList<>(quantities.keySet()));
    }

    /**
     * Повертає незмінне відображення «одяг → кількість».
     *
     * @return незмінна мапа кількостей
     */
    public Map<Clothes, Integer> getQuantities() {
        return Collections.unmodifiableMap(quantities);
    }

    /**
     * Повертає кількість конкретної позиції одягу.
     *
     * @param item позиція одягу
     * @return кількість або {@code 0}, якщо позиції немає
     */
    public int getQuantity(Clothes item) {
        return quantities.getOrDefault(item, 0);
    }

    /**
     * Додає одяг у магазин, збільшуючи кількість для цієї позиції.
     *
     * @param item позиція одягу
     * @param quantity кількість, яку потрібно додати
     * @throws IllegalArgumentException якщо {@code item} є {@code null} або {@code quantity <= 0}
     */
    public void addNewClothes(Clothes item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Clothes item cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        quantities.put(item, quantities.getOrDefault(item, 0) + quantity);
    }

    /**
     * Оновлює наявний об'єкт одягу в колекції.
     *
     * @param existingObject об'єкт, який потрібно знайти
     * @param newObject новий стан об'єкта
     * @return {@code true}, якщо оновлення виконано; інакше {@code false}
     * @throws IllegalArgumentException якщо будь-який аргумент дорівнює {@code null}
     */
    public boolean update(Clothes existingObject, Clothes newObject) {
        if (existingObject == null) {
            throw new IllegalArgumentException("Existing clothes object cannot be null.");
        }
        if (newObject == null) {
            throw new IllegalArgumentException("New clothes object cannot be null.");
        }

        LinkedHashMap<Clothes, Integer> updatedQuantities = new LinkedHashMap<>();
        boolean updated = false;

        for (Map.Entry<Clothes, Integer> entry : quantities.entrySet()) {
            Clothes currentObject = entry.getKey();
            Integer quantity = entry.getValue();

            if (!updated && currentObject.equals(existingObject)) {
                updated = true;
                updatedQuantities.put(newObject, updatedQuantities.getOrDefault(newObject, 0) + quantity);
                continue;
            }

            updatedQuantities.put(currentObject, updatedQuantities.getOrDefault(currentObject, 0) + quantity);
        }

        if (!updated) {
            return false;
        }

        quantities.clear();
        quantities.putAll(updatedQuantities);
        return true;
    }

    public boolean delete(Clothes existingObject) {
        if (existingObject == null) {
            throw new IllegalArgumentException("Existing clothes object cannot be null.");
        }

        if (!quantities.containsKey(existingObject)) {
            return false;
        }

        quantities.remove(existingObject);
        return true;
    }

    /**
     * Пошук одягу за назвою (частковий збіг, без урахування регістру).
     *
     * @param query рядок пошуку
     * @return список знайдених позицій
     * @throws IllegalArgumentException якщо {@code query} є {@code null} або порожнім
     */
    public List<Clothes> findClothesByName(String query) {
        String normalizedQuery = normalizeText(query, "Search query");
        return findClothes(item -> item.getName().toLowerCase().contains(normalizedQuery));
    }

    /**
     * Пошук одягу за розміром.
     *
     * @param size розмір
     * @return список знайдених позицій
     * @throws IllegalArgumentException якщо {@code size} є {@code null}
     */
    public List<Clothes> findClothesBySize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }

        return findClothes(item -> item.getSize() == size);
    }

    /**
     * Пошук одягу за матеріалом (частковий збіг, без урахування регістру).
     *
     * @param material матеріал або його частина
     * @return список знайдених позицій
     * @throws IllegalArgumentException якщо {@code material} є {@code null} або порожнім
     */
    public List<Clothes> findClothesByMaterial(String material) {
        String normalizedMaterial = normalizeText(material, "Material");
        return findClothes(item -> item.getMaterial().toLowerCase().contains(normalizedMaterial));
    }

    /**
     * Пошук одягу за максимальною ціною (включно).
     *
     * @param maximumPrice максимальна ціна
     * @return список знайдених позицій
     * @throws IllegalArgumentException якщо {@code maximumPrice <= 0}
     */
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
