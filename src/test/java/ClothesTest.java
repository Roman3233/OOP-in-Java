import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Shirts clothes = new Shirts("T-shirt", Size.M, 499.99, "Cotton", 64);
        Jacket jacket = new Jacket("Winter", Size.L, 3499.99, "Polyester", 8);
        Hat hat = new Hat("Panama", Size.S, 699.99, "Straw", 8);

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSleeveLength(0));
        assertThrows(IllegalArgumentException.class, () -> jacket.setPocketCount(-1));
        assertThrows(IllegalArgumentException.class, () -> hat.setBrimWidth(0));
    }

    @Test
    void shouldThrowExceptionWhenStringFieldIsNullOrEmptyInSetter() {
        Pants clothes = new Pants("Pants", Size.M, 499.99, "Cotton", 82);

        assertThrows(IllegalArgumentException.class, () -> clothes.setName(""));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSize(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setMaterial(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setWaistSize(-5));
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shirts("", Size.M, 499.99, "Cotton", 60));
    }

    @Test
    void shouldCreateClothesWhenDataIsValid() {
        Shirts clothes = assertDoesNotThrow(
                () -> new Shirts("Jacket", Size.L, 1999.50, "Denim", 68));
        Jacket jacket = assertDoesNotThrow(
                () -> new Jacket("Puffer", Size.XL, 4200.00, "Nylon", 9));
        Hat hat = assertDoesNotThrow(
                () -> new Hat("Fedora", Size.M, 1200.00, "Felt", 7.5));

        assertEquals("Jacket", clothes.getName());
        assertEquals(Size.L, clothes.getSize());
        assertEquals(1999.50, clothes.getPrice());
        assertEquals("Denim", clothes.getMaterial());
        assertEquals(68, clothes.getSleeveLength());
        assertEquals("Shirt", clothes.getType());
        assertEquals(9, jacket.getPocketCount());
        assertTrue(jacket.hasPracticalPockets());
        assertEquals(7.5, hat.getBrimWidth());
        assertTrue(hat.hasSunProtection());
    }

    @Test
    void shouldReturnReadableStringRepresentation() {
        Pants pants = new Pants("501", Size.M, 2499.99, "Denim", 82);
        Jacket jacket = new Jacket("Storm", Size.L, 3999.99, "Membrane", 7);
        Hat hat = new Hat("Safari", Size.M, 899.99, "Cotton", 9);

        String result = pants.toString();
        String jacketResult = jacket.toString();
        String hatResult = hat.toString();

        assertTrue(result.contains("Pants: name='501'"));
        assertTrue(result.contains("size=M"));
        assertTrue(result.contains("price=2499.99"));
        assertTrue(result.contains("material='Denim'"));
        assertTrue(result.contains("waistSize=82.0"));
        assertTrue(jacketResult.contains("Jacket: name='Storm'"));
        assertTrue(jacketResult.contains("pocketCount=7"));
        assertTrue(hatResult.contains("Hat: name='Safari'"));
        assertTrue(hatResult.contains("brimWidth=9.0"));
    }
}
