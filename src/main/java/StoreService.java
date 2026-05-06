import java.util.ArrayList;
import java.util.Collections;
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
            throw new InvalidFieldValueException("Store cannot be null.");
        }
        if (storage == null) {
            throw new InvalidFieldValueException("Storage cannot be null.");
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
     * Оновлює об'єкт одягу в магазині лише в пам'яті.
     *
     * @param existingClothes поточний об'єкт
     * @param newClothes новий стан об'єкта
     * @return {@code true}, якщо оновлення виконано; інакше {@code false}
     */
    /**
     * Оновлює об'єкт одягу в магазині та синхронізує зміну з файловим сховищем.
     *
     * @param existingClothes поточний об'єкт
     * @param newClothes новий стан об'єкта
     * @return {@code true}, якщо оновлення виконано; інакше {@code false}
     */
    public boolean updateClothes(Clothes existingClothes, Clothes newClothes) {
        store.update(existingClothes, newClothes);

        boolean updatedInStorage;
        try {
            updatedInStorage = storage.updateClothes(existingClothes, newClothes);
        } catch (RuntimeException e) {
            store.update(newClothes, existingClothes);
            throw e;
        }

        if (!updatedInStorage) {
            store.update(newClothes, existingClothes);
            throw new ObjectNotFoundException("Clothes object to update was not found in file storage.");
        }

        return true;
    }

    /**
     * Видаляє об'єкт одягу з магазину та файлового сховища.
     *
     * @param existingClothes об'єкт, який потрібно видалити
     * @return {@code true}, якщо видалення виконано; інакше {@code false}
     */
    public boolean deleteClothes(Clothes existingClothes) {
        int quantityBeforeDeletion = store.getQuantity(existingClothes);
        store.delete(existingClothes);

        boolean deletedFromStorage;
        try {
            deletedFromStorage = storage.deleteClothes(existingClothes);
        } catch (RuntimeException e) {
            store.addNewClothes(existingClothes, quantityBeforeDeletion);
            throw e;
        }

        if (!deletedFromStorage) {
            store.addNewClothes(existingClothes, quantityBeforeDeletion);
            throw new ObjectNotFoundException("Clothes object to delete was not found in file storage.");
        }

        return true;
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
     * Повертає всі позиції одягу, відсортовані за природним порядком.
     *
     * @return відсортований список одягу
     */
    public List<Clothes> getAllClothesSorted() {
        List<Clothes> sortedClothes = new ArrayList<>(store.getClothes());
        Collections.sort(sortedClothes);
        return sortedClothes;
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
