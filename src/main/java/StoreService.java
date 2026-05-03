import java.util.List;

public class StoreService {
    private final Store store;
    private final ClothesFileStorage storage;

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

    public void loadFromStorage() {
        List<Clothes> clothesList = storage.loadClothes();
        for (Clothes clothes : clothesList) {
            store.addNewClothes(clothes, 1);
        }
    }

    public void addClothes(Clothes clothes) {
        store.addNewClothes(clothes, 1);
        storage.appendClothes(clothes);
    }

    public List<Clothes> getAllClothes() {
        return store.getClothes();
    }

    public List<Clothes> findClothesByName(String query) {
        return store.findClothesByName(query);
    }

    public List<Clothes> findClothesBySize(Size size) {
        return store.findClothesBySize(size);
    }

    public List<Clothes> findClothesByMaterial(String material) {
        return store.findClothesByMaterial(material);
    }

    public List<Clothes> findClothesByMaximumPrice(double maximumPrice) {
        return store.findClothesByMaximumPrice(maximumPrice);
    }
}
