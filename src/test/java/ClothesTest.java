import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Юніт-тести для {@link Clothes}.
 */
class ClothesTest {

    /**
     * Перевіряє, що сеттери відхиляють некоректні значення.
     */
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("T-shirt", Size.M, 499.99, "Black", "Cotton");

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
    }

    /**
     * Перевіряє, що сеттери рядкових полів та об'єктів відхиляють {@code null} і порожні значення.
     */
    @Test
    void shouldThrowExceptionWhenStringFieldIsNullOrEmptyInSetter() {
        Clothes clothes = new Clothes("T-shirt", Size.M, 499.99, "Black", "Cotton");

        assertThrows(IllegalArgumentException.class, () -> clothes.setName(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSize(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setColor(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setMaterial(null));
    }

    /**
     * Перевіряє, що конструктор відхиляє некоректні вхідні дані.
     */
    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clothes("", Size.M, 499.99, "Black", "Cotton"));
    }

    /**
     * Перевіряє, що коректні дані створюють правильний екземпляр {@link Clothes}.
     */
    @Test
    void shouldCreateClothesWhenDataIsValid() {
        Clothes clothes = assertDoesNotThrow(
                () -> new Clothes("Jacket", Size.L, 1999.50, "Blue", "Denim"));

        assertEquals("Jacket", clothes.getName());
        assertEquals(Size.L, clothes.getSize());
        assertEquals(1999.50, clothes.getPrice());
        assertEquals("Blue", clothes.getColor());
        assertEquals("Denim", clothes.getMaterial());
    }
}
