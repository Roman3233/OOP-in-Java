import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Юніт-тести для {@link Manufacturer}.
 */
public class ManufacturerTest {
    /**
     * Перевіряє, що конструктор {@link Manufacturer} відхиляє некоректні вхідні дані.
     */
    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Manufacturer("", "USA", 1990));
        assertThrows(IllegalArgumentException.class,
                () -> new Manufacturer("Levi's", "", 1990));
        assertThrows(IllegalArgumentException.class,
                () -> new Manufacturer("Levi's", "USA", -1));
        assertThrows(IllegalArgumentException.class,
                () -> new Manufacturer("Levi's", "USA", 3000));
    }

    /**
     * Перевіряє, що коректні дані створюють правильний екземпляр {@link Manufacturer}.
     */
    @Test
    void shouldCreateManufacturerWhenDataIsValid() {
        Manufacturer manufacturer = assertDoesNotThrow(
                () -> new Manufacturer("Levi's", "USA", 1990));

        assertEquals("Levi's", manufacturer.getName());
        assertEquals("USA", manufacturer.getCountry());
        assertEquals(1990, manufacturer.getFoundedYear());
    }
}
