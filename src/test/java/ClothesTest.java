import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("T-shirt", "M", 499.99, "Black", "Cotton");

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
    }

    @Test
    void shouldThrowExceptionWhenStringFieldIsNullOrEmptyInSetter() {
        Clothes clothes = new Clothes("T-shirt", "M", 499.99, "Black", "Cotton");

        assertThrows(IllegalArgumentException.class, () -> clothes.setName(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSize(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setColor(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setMaterial(null));
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clothes("", "M", 499.99, "Black", "Cotton"));
    }

    @Test
    void shouldCreateClothesWhenDataIsValid() {
        Clothes clothes = assertDoesNotThrow(
                () -> new Clothes("Jacket", "L", 1999.50, "Blue", "Denim"));

        assertEquals("Jacket", clothes.getName());
        assertEquals("L", clothes.getSize());
        assertEquals(1999.50, clothes.getPrice());
        assertEquals("Blue", clothes.getColor());
        assertEquals("Denim", clothes.getMaterial());
    }
}
