import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Shirts clothes = new Shirts("T-shirt", Size.M, 499.99, "Black", "Cotton", 64);

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSleeveLength(0));
    }

    @Test
    void shouldThrowExceptionWhenStringFieldIsNullOrEmptyInSetter() {
        Pants clothes = new Pants("Pants", Size.M, 499.99, "Black", "Cotton", 82);

        assertThrows(IllegalArgumentException.class, () -> clothes.setName(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSize(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setColor(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setMaterial(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setWaistSize(-5));
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shirts("", Size.M, 499.99, "Black", "Cotton", 60));
    }

    @Test
    void shouldCreateClothesWhenDataIsValid() {
        Shirts clothes = assertDoesNotThrow(
                () -> new Shirts("Jacket", Size.L, 1999.50, "Blue", "Denim", 68));

        assertEquals("Jacket", clothes.getName());
        assertEquals(Size.L, clothes.getSize());
        assertEquals(1999.50, clothes.getPrice());
        assertEquals("Blue", clothes.getColor());
        assertEquals("Denim", clothes.getMaterial());
        assertEquals(68, clothes.getSleeveLength());
    }
}
