import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void shouldCreateDifferentClothesTypesAndPrintThemFromSingleList() {
        String input = String.join(System.lineSeparator(),
                "1",
                "Jeans",
                "M",
                "1999.99",
                "Denim",
                "82",
                "2",
                "Polo",
                "L",
                "1299.50",
                "Cotton",
                "65",
                "3",
                "Puffer",
                "XL",
                "4200",
                "Nylon",
                "9",
                "4",
                "Panama",
                "S",
                "750",
                "Straw",
                "8",
                "5",
                "0") + System.lineSeparator();

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String console = output.toString(StandardCharsets.UTF_8);

        assertTrue(console.contains("Pants added successfully."));
        assertTrue(console.contains("Shirt added successfully."));
        assertTrue(console.contains("Jacket added successfully."));
        assertTrue(console.contains("Hat added successfully."));
        assertTrue(console.contains("All clothes:"));
        assertTrue(console.contains("Pants -> Pants: name='Jeans'"));
        assertTrue(console.contains("Shirt -> Shirt: name='Polo'"));
        assertTrue(console.contains("Jacket -> Jacket: name='Puffer'"));
        assertTrue(console.contains("Hat -> Hat: name='Panama'"));
        assertTrue(console.contains("Program finished."));
    }
}
