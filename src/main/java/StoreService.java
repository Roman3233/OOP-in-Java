import java.util.List;

/**
 * Сервісний шар для роботи з магазином: завантаження/збереження одягу та операції пошуку.
 * <p>
 * Делегує доменну логіку класу {@link Store}, а роботу з файловим сховищем — {@link ClothesFileStorage}.
 */
public class StoreService {
    private final Store store;
    private final ClothesFileStorage storage;

    /**
     * Створює сервіс з потрібними залежностями.
     *
     * @param store магазин
     * @param storage файлове сховище
     * @throws IllegalArgumentException якщо будь-яка залежність дорівнює {@code null}
     */
    public StoreService(Store store, ClothesFileStorage storage) {
        if (store == null) {
            throw new IllegalArgumentException("Store cannot be null.");
        }
        if (storage == null) {
            throw new IllegalArgumentException("Storage cannot be null.");
        }

        this.store = store;
        this.storage = storage;
    }

    /**
     * Завантажує одяг зі сховища та додає його до магазину.
     */
    public void loadFromStorage() {
        List<Clothes> clothesList = storage.loadClothes();
        for (Clothes clothes : clothesList) {
            store.addNewClothes(clothes, 1);
        }
    }

    /**
     * Додає одяг до магазину та зберігає його у сховище.
     *
     * @param clothes одяг для додавання
     */
    public void addClothes(Clothes clothes) {
        store.addNewClothes(clothes, 1);
        storage.appendClothes(clothes);
    }

    /**
     * Повертає всі унікальні позиції одягу у магазині.
     *
     * @return список одягу
     */
    public List<Clothes> getAllClothes() {
        return store.getClothes();
    }

    /**
     * Пошук одягу за назвою (частковий збіг, без урахування регістру).
     *
     * @param query рядок пошуку
     * @return знайдені позиції
     */
    public List<Clothes> findClothesByName(String query) {
        return store.findClothesByName(query);
    }

    /**
     * Пошук одягу за розміром.
     *
     * @param size розмір
     * @return знайдені позиції
     */
    public List<Clothes> findClothesBySize(Size size) {
        return store.findClothesBySize(size);
    }

    /**
     * Пошук одягу за матеріалом (частковий збіг, без урахування регістру).
     *
     * @param material матеріал або його частина
     * @return знайдені позиції
     */
    public List<Clothes> findClothesByMaterial(String material) {
        return store.findClothesByMaterial(material);
    }

    /**
     * Пошук одягу за максимальною ціною (включно).
     *
     * @param maximumPrice максимальна ціна
     * @return знайдені позиції
     */
    public List<Clothes> findClothesByMaximumPrice(double maximumPrice) {
        return store.findClothesByMaximumPrice(maximumPrice);
    }
}
