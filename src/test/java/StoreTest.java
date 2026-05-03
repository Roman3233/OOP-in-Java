import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void shouldRejectInvalidArguments() {
        Store store = new Store("My store");
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);

        assertThrows(IllegalArgumentException.class, () -> new Store(" "));
        assertThrows(IllegalArgumentException.class, () -> store.addNewClothes(null, 1));
        assertThrows(IllegalArgumentException.class, () -> store.addNewClothes(pants, 0));
        assertThrows(IllegalArgumentException.class, () -> store.findClothesByName(" "));
        assertThrows(IllegalArgumentException.class, () -> store.findClothesBySize(null));
        assertThrows(IllegalArgumentException.class, () -> store.findClothesByMaterial(null));
        assertThrows(IllegalArgumentException.class, () -> store.findClothesByMaximumPrice(0));
    }
}
