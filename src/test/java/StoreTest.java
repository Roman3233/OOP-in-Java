import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreTest {

    @Test
    void shouldAggregateQuantityForEqualClothes() {
        Store store = new Store("My store");
        Pants first = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Pants second = new Pants("501", Size.M, 2499.99, "Denim", 82);

        store.addNewClothes(first, 2);
        store.addNewClothes(second, 3);

        assertEquals(1, store.getClothes().size());
        assertEquals(5, store.getQuantity(first));
        assertEquals(5, store.getQuantity(second));
    }

    @Test
    void shouldFindClothesByAllSupportedCriteria() {
        Store store = new Store("My store");
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Hat hat = new Hat("Safari", Size.L, 899.99, "Cotton", 9);
        Jacket jacket = new Jacket("Storm", Size.XL, 3999.99, "Leather", 4);

        store.addNewClothes(pants, 1);
        store.addNewClothes(hat, 1);
        store.addNewClothes(jacket, 1);

        List<Clothes> byName = store.findClothesByName("saf");
        List<Clothes> bySize = store.findClothesBySize(Size.M);
        List<Clothes> byMaterial = store.findClothesByMaterial("leat");
        List<Clothes> byPrice = store.findClothesByMaximumPrice(1000);

        assertEquals(List.of(hat), byName);
        assertEquals(List.of(pants), bySize);
        assertEquals(List.of(jacket), byMaterial);
        assertEquals(List.of(hat), byPrice);
    }

    @Test
    void shouldPopulateStoreFromConstructorCollection() {
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Pants samePants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Hat hat = new Hat("Safari", Size.L, 899.99, "Cotton", 9);

        Store store = new Store("My store", List.of(pants, samePants, hat));

        assertEquals(2, store.getClothes().size());
        assertEquals(2, store.getQuantity(pants));
        assertEquals(1, store.getQuantity(hat));
    }

    @Test
    void shouldUpdateExistingClothesAndKeepQuantity() {
        Store store = new Store("My store");
        Pants originalPants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Pants updatedPants = new Pants("502", Size.M, 2499.99, "Denim", 82);

        store.addNewClothes(originalPants, 3);

        boolean updated = store.update(originalPants, updatedPants);

        assertTrue(updated);
        assertEquals(0, store.getQuantity(originalPants));
        assertEquals(3, store.getQuantity(updatedPants));
        assertEquals(List.of(updatedPants), store.getClothes());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenUpdatingMissingClothes() {
        Store store = new Store("My store");
        Pants originalPants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Pants updatedPants = new Pants("502", Size.M, 2499.99, "Denim", 82);

        assertThrows(ObjectNotFoundException.class, () -> store.update(originalPants, updatedPants));
        assertTrue(store.getClothes().isEmpty());
    }

    @Test
    void shouldDeleteExistingClothes() {
        Store store = new Store("My store");
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Hat hat = new Hat("Safari", Size.L, 899.99, "Cotton", 9);

        store.addNewClothes(pants, 2);
        store.addNewClothes(hat, 1);

        boolean deleted = store.delete(pants);

        assertTrue(deleted);
        assertEquals(0, store.getQuantity(pants));
        assertEquals(List.of(hat), store.getClothes());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingMissingClothes() {
        Store store = new Store("My store");

        assertThrows(ObjectNotFoundException.class,
                () -> store.delete(new Pants("501", Size.M, 2499.99, "Denim", 82)));
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenArgumentsAreInvalid() {
        Store store = new Store("My store");
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);

        assertThrows(InvalidFieldValueException.class, () -> new Store(" "));
        assertThrows(InvalidFieldValueException.class, () -> store.addNewClothes(null, 1));
        assertThrows(InvalidFieldValueException.class, () -> store.addNewClothes(pants, 0));
        assertThrows(InvalidFieldValueException.class, () -> store.update(null, pants));
        assertThrows(InvalidFieldValueException.class, () -> store.update(pants, null));
        assertThrows(InvalidFieldValueException.class, () -> store.delete(null));
        assertThrows(InvalidFieldValueException.class, () -> store.findClothesByName(" "));
        assertThrows(InvalidFieldValueException.class, () -> store.findClothesBySize(null));
        assertThrows(InvalidFieldValueException.class, () -> store.findClothesByMaterial(null));
        assertThrows(InvalidFieldValueException.class, () -> store.findClothesByMaximumPrice(0));
    }
}
