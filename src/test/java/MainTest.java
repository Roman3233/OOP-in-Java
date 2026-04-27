import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тести для консольного застосунку {@link Main}.
 *
 * <p>Тести запускають {@link Main#main(String[])} з підставленими потоками введення/виведення,
 * щоб перевірити базові сценарії роботи меню без ручної взаємодії.</p>
 */
class MainTest {

    /**
     * Перевіряє, що програма коректно показує повідомлення про порожній список
     * та завершує роботу при виборі пункту виходу.
     */
    @Test
    void shouldPrintEmptyListMessageAndExit() {
        String input = String.join(System.lineSeparator(),
                "2", // show all clothes (empty)
                "3"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input);

        assertTrue(output.contains("The clothes list is empty."));
        assertTrue(output.contains("Program finished."));
    }

    /**
     * Перевіряє, що програма дозволяє створити об'єкт одягу та відображає його
     * у списку при виборі пункту "Show all clothes".
     */
    @Test
    void shouldCreatePantsAndPrintInList() {
        String input = String.join(System.lineSeparator(),
                "1", // create new object
                "1", // pants
                "501", // name
                "M", // size
                "2499.99", // price
                "Denim", // material
                "82", // waist size
                "2", // show all clothes
                "3"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input);

        assertTrue(output.contains("All clothes:"));
        assertTrue(output.contains("Pants: name='501'"));
        assertTrue(output.contains("waistSize=82.0"));
        assertTrue(output.contains("Program finished."));
    }

    private static String runMainAndCaptureOutput(String stdin) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream testIn = new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream testPrintStream = new PrintStream(testOut, true, StandardCharsets.UTF_8);

        try {
            System.setIn(testIn);
            System.setOut(testPrintStream);
            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            testPrintStream.close();
        }

        return testOut.toString(StandardCharsets.UTF_8);
    }
}

