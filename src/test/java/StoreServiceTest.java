import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreServiceTest {

    @Test
    void shouldLoadClothesFromStorageIntoStoreAndAggregateDuplicates() throws IOException {
        Path storageFile = Files.createTempFile("store-service-load", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Pants;501;M;2499.99;Denim;82.0",
                        "Pants;501;M;2499.99;Denim;82.0",
                        "Hat;Safari;L;899.99;Cotton;9.0"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        Store store = new Store("My store");
        StoreService storeService = new StoreService(store, new ClothesFileStorage(storageFile.toString()));

        storeService.loadFromStorage();

        assertEquals(2, storeService.getAllClothes().size());
        assertEquals(2, store.getQuantity(new Pants("501", Size.M, 2499.99, "Denim", 82)));
    }

    @Test
    void shouldAddClothesToStoreAndPersistIt() throws IOException {
        Path storageFile = Files.createTempFile("store-service-add", ".txt");
        Store store = new Store("My store");
        StoreService storeService = new StoreService(store, new ClothesFileStorage(storageFile.toString()));
        Jacket jacket = new Jacket("Storm", Size.L, 3999.99, "Leather", 4);

        storeService.addClothes(jacket);

        assertEquals(List.of(jacket), storeService.getAllClothes());
        assertEquals(1, store.getQuantity(jacket));
        assertTrue(Files.readString(storageFile, StandardCharsets.UTF_8)
                .contains("Jacket;Storm;L;3999.99;Leather;4"));
    }

    @Test
    void shouldDelegateSearchesToStore() throws IOException {
        Path storageFile = Files.createTempFile("store-service-search", ".txt");
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Hat hat = new Hat("Safari", Size.L, 899.99, "Cotton", 9);
        Store store = new Store("My store", List.of(pants, hat));
        StoreService storeService = new StoreService(store, new ClothesFileStorage(storageFile.toString()));

        assertEquals(1, storeService.findClothesByName("501").size());
        assertEquals(1, storeService.findClothesBySize(Size.L).size());
        assertEquals(1, storeService.findClothesByMaterial("cot").size());
        assertEquals(1, storeService.findClothesByMaximumPrice(1000).size());
        assertEquals(hat, storeService.findClothesByUuid(hat.getUuid()));
        assertNull(storeService.findClothesByUuid(UUID.randomUUID()));
    }

    @Test
    void shouldReturnClothesSortedByNaturalOrder() throws IOException {
        Path storageFile = Files.createTempFile("store-service-sorted", ".txt");
        Hat alpha = new Hat("Alpha", Size.M, 899.99, "Cotton", 9);
        Pants bravo = new Pants("Bravo", Size.S, 2499.99, "Denim", 82);
        Jacket zulu = new Jacket("Zulu", Size.L, 3999.99, "Leather", 4);
        Store store = new Store("My store", List.of(zulu, alpha, bravo));
        StoreService storeService = new StoreService(store, new ClothesFileStorage(storageFile.toString()));

        List<Clothes> sortedClothes = storeService.getAllClothesSorted();

        assertIterableEquals(List.of(alpha, bravo, zulu), sortedClothes);
    }

    @Test
    void shouldRejectNullDependencies() throws IOException {
        Path storageFile = Files.createTempFile("store-service-invalid", ".txt");
        ClothesFileStorage storage = new ClothesFileStorage(storageFile.toString());
        Store store = new Store("My store");

        assertThrows(IllegalArgumentException.class, () -> new StoreService(null, storage));
        assertThrows(IllegalArgumentException.class, () -> new StoreService(store, null));
    }
}
