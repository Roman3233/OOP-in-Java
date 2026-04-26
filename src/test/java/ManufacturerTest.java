import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManufacturerTest {
    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Manufacturer("", "USA"));
        assertThrows(IllegalArgumentException.class,
                () -> new Manufacturer("Levi's", ""));
    }

    @Test
    void shouldCreateManufacturerWhenDataIsValid() {
        Manufacturer manufacturer = assertDoesNotThrow(
                () -> new Manufacturer("Levi's", "USA"));

        assertEquals("Levi's", manufacturer.getName());
        assertEquals("USA", manufacturer.getCountry());
    }
}
