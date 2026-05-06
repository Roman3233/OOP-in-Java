import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void shouldPrintEmptyListMessageAndExit() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-empty", ".txt");
        String input = String.join(System.lineSeparator(),
                "5", // show all clothes (empty)
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("The clothes list is empty."));
        assertTrue(output.contains("Program finished."));
    }

    @Test
    void shouldReturnToMainMenuFromSortSubmenuWithoutSorting() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sort-return", ".txt");
        Files.writeString(
                storageFile,
                "Hat;Safari;M;899.99;Cotton;9.0" + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "6", // sort submenu
                "0", // return to main menu
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("Choose sorting criterion:"));
        assertTrue(output.contains("Returning to main menu."));
        assertTrue(output.contains("Program finished."));
    }

    @Test
    void shouldPrintEmptyListMessageWhenSortingEmptyList() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sort-empty", ".txt");
        String input = String.join(System.lineSeparator(),
                "6", // sort submenu
                "1", // sort by name
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("Choose sorting criterion:"));
        assertTrue(output.contains("The clothes list is empty."));
        assertTrue(output.contains("Program finished."));
    }

    /**
     * Перевіряє, що програма дозволяє створити об'єкт одягу та відображає його
     * у списку при виборі пункту "Show all clothes".
     */
    @Test
    void shouldCreatePantsAndPrintInList() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-create", ".txt");
        String input = String.join(System.lineSeparator(),
                "2", // create new object
                "1", // pants
                "501", // name
                "M", // size
                "2499.99", // price
                "Denim", // material
                "82", // waist size
                "5", // show all clothes
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("All clothes:"));
        assertTrue(output.contains("Pants: name='501'"));
        assertTrue(output.contains("waistSize=82.0"));
        assertTrue(output.contains("Program finished."));
        assertTrue(Files.readString(storageFile, StandardCharsets.UTF_8).contains("Pants;501;M;2499.99;Denim;82.0"));
    }

    @Test
    void shouldLoadClothesFromFileOnStartup() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-load", ".txt");
        Files.writeString(
                storageFile,
                "Hat;Safari;M;899.99;Cotton;9.0" + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "5",
                "7"
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("All clothes:"));
        assertTrue(output.contains("Hat: name='Safari'"));
        assertTrue(output.contains("brimWidth=9.0"));
    }

    @Test
    void shouldSearchClothesByMaterialWithoutChangingCollection() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-search-material", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Pants;501;M;2499.99;Denim;82.0",
                        "Hat;Safari;L;899.99;Cotton;9.0"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "1", // search object
                "3", // search by material
                "denim",
                "0", // return to main menu
                "5", // show all clothes
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("Search results:"));
        assertTrue(output.contains("Pants: name='501'"));
        assertTrue(output.contains("All clothes:"));
        assertTrue(output.contains("Hat: name='Safari'"));
    }

    @Test
    void shouldPrintMessageWhenSearchReturnsNoResults() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-search-empty", ".txt");
        Files.writeString(
                storageFile,
                "Jacket;Storm;XL;3999.99;Leather;4" + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "1", // search object
                "2", // search by size
                "XS",
                "0", // return to main menu
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("No clothes found for the selected size."));
        assertTrue(output.contains("Program finished."));
    }

    @Test
    void shouldPrintSortedClothesForSingleElement() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sorted-single", ".txt");
        Files.writeString(
                storageFile,
                "Hat;Safari;M;899.99;Cotton;9.0" + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "6",
                "1",
                "7"
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("Choose sorting criterion:"));
        assertTrue(output.contains("All clothes sorted by name:"));
        assertTrue(output.contains("Hat: name='Safari'"));
        assertTrue(output.contains("Program finished."));
    }

    @Test
    void shouldPrintSortedClothesForMultipleElementsInAscendingNameOrder() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sorted-multiple", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Jacket;Zulu;L;3999.99;Leather;4",
                        "Hat;Alpha;M;899.99;Cotton;9.0",
                        "Pants;Bravo;S;2499.99;Denim;82.0"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String output = runMainAndCaptureOutput(
                "6" + System.lineSeparator() +
                        "1" + System.lineSeparator() +
                        "7" + System.lineSeparator(),
                storageFile
        );

        int alphaIndex = output.indexOf("Hat: name='Alpha'");
        int bravoIndex = output.indexOf("Pants: name='Bravo'");
        int zuluIndex = output.indexOf("Jacket: name='Zulu'");

        assertTrue(output.contains("All clothes sorted by name:"));
        assertTrue(alphaIndex >= 0);
        assertTrue(bravoIndex > alphaIndex);
        assertTrue(zuluIndex > bravoIndex);
    }

    @Test
    void shouldSortByPriceFromSortSubmenu() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sorted-price", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Jacket;Storm;L;3999.99;Leather;4",
                        "Hat;Safari;M;899.99;Cotton;9.0",
                        "Pants;501;S;2499.99;Denim;82.0"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String output = runMainAndCaptureOutput(
                "6" + System.lineSeparator() +
                        "2" + System.lineSeparator() +
                        "7" + System.lineSeparator(),
                storageFile
        );

        int hatIndex = output.indexOf("Hat: name='Safari'");
        int pantsIndex = output.indexOf("Pants: name='501'");
        int jacketIndex = output.indexOf("Jacket: name='Storm'");

        assertTrue(output.contains("All clothes sorted by price:"));
        assertTrue(hatIndex >= 0);
        assertTrue(pantsIndex > hatIndex);
        assertTrue(jacketIndex > pantsIndex);
    }

    @Test
    void shouldSortBySizeFromSortSubmenu() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sorted-size", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Hat;Safari;XL;899.99;Cotton;9.0",
                        "Pants;501;S;2499.99;Denim;82.0",
                        "Jacket;Storm;M;3999.99;Leather;4"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String output = runMainAndCaptureOutput(
                "6" + System.lineSeparator() +
                        "3" + System.lineSeparator() +
                        "7" + System.lineSeparator(),
                storageFile
        );

        int sizeSIndex = output.indexOf("Pants: name='501'");
        int sizeMIndex = output.indexOf("Jacket: name='Storm'");
        int sizeXLIndex = output.indexOf("Hat: name='Safari'");

        assertTrue(output.contains("All clothes sorted by size:"));
        assertTrue(sizeSIndex >= 0);
        assertTrue(sizeMIndex > sizeSIndex);
        assertTrue(sizeXLIndex > sizeMIndex);
    }

    @Test
    void shouldKeepStableOrderWhenSortingByPriceWithEqualPrices() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-sorted-price-ties", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Hat;First;M;1000.0;Cotton;9.0",
                        "Pants;Second;M;1000.0;Denim;82.0",
                        "Jacket;Third;M;2000.0;Leather;4"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String output = runMainAndCaptureOutput(
                "6" + System.lineSeparator() +
                        "2" + System.lineSeparator() +
                        "7" + System.lineSeparator(),
                storageFile
        );

        int firstIndex = output.indexOf("Hat: name='First'");
        int secondIndex = output.indexOf("Pants: name='Second'");
        int thirdIndex = output.indexOf("Jacket: name='Third'");

        assertTrue(output.contains("All clothes sorted by price:"));
        assertTrue(firstIndex >= 0);
        assertTrue(secondIndex > firstIndex);
        assertTrue(thirdIndex > secondIndex);
    }

    @Test
    void shouldRecoverFromInvalidInputWithoutCrashing() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-invalid-input", ".txt");
        String input = String.join(System.lineSeparator(),
                "abc", // invalid main menu choice
                "2", // create new object
                "9", // invalid clothes type
                "1", // pants
                "", // invalid name
                "501",
                "wrong-size", // invalid size
                "M",
                "not-a-number", // invalid price
                "2499.99",
                "", // invalid material
                "Denim",
                "-1", // invalid waist size
                "82",
                "6", // print sorted list
                "9", // invalid sorting criterion
                "1",
                "7"  // exit
        ) + System.lineSeparator();

        String output = assertDoesNotThrow(() -> runMainAndCaptureOutput(input, storageFile));

        assertTrue(output.contains("Error: please enter a valid integer."));
        assertTrue(output.contains("Error: unknown menu option."));
        assertTrue(output.contains("Error: input cannot be empty."));
        assertTrue(output.contains("Error: Invalid size. Allowed: XS, S, M, L, XL, XXL."));
        assertTrue(output.contains("Error: please enter a valid number."));
        assertTrue(output.contains("Error: value must be greater than 0."));
        assertTrue(output.contains("Pants added successfully."));
        assertTrue(output.contains("Choose sorting criterion:"));
        assertTrue(output.contains("All clothes sorted by name:"));
        assertTrue(output.contains("Pants: name='501'"));
        assertTrue(output.contains("Program finished."));
    }

    @Test
    void shouldModifySelectedClothesInMemoryAndFile() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-modify", ".txt");
        Files.writeString(
                storageFile,
                "Pants;501;M;2499.99;Denim;82.0" + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "3", // modify object
                "1", // select first object
                "1", // modify name
                "502",
                "5", // show all clothes
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);

        assertTrue(output.contains("Choose clothes to modify:"));
        assertTrue(output.contains("Choose attribute to modify:"));
        assertTrue(output.contains("Clothes updated successfully."));
        assertTrue(output.contains("Pants: name='502'"));
        assertTrue(Files.readString(storageFile, StandardCharsets.UTF_8)
                .contains("Pants;502;M;2499.99;Denim;82.0"));
    }

    @Test
    void shouldDeleteSelectedClothesFromMemoryAndFile() throws IOException {
        Path storageFile = Files.createTempFile("clothes-main-delete", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Pants;501;M;2499.99;Denim;82.0",
                        "Hat;Safari;M;899.99;Cotton;9.0"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        String input = String.join(System.lineSeparator(),
                "4", // delete object
                "1", // select first object
                "5", // show all clothes
                "7"  // exit
        ) + System.lineSeparator();

        String output = runMainAndCaptureOutput(input, storageFile);
        int allClothesIndex = output.lastIndexOf("All clothes:");

        assertTrue(output.contains("Choose clothes to delete:"));
        assertTrue(output.contains("Clothes deleted successfully."));
        assertTrue(allClothesIndex >= 0);
        assertTrue(output.indexOf("Hat: name='Safari'", allClothesIndex) > allClothesIndex);
        assertTrue(output.indexOf("Pants: name='501'", allClothesIndex) < 0);
        assertTrue(Files.readString(storageFile, StandardCharsets.UTF_8)
                .contains("Hat;Safari;M;899.99;Cotton;9.0"));
        assertTrue(!Files.readString(storageFile, StandardCharsets.UTF_8)
                .contains("Pants;501;M;2499.99;Denim;82.0"));
    }

    private static String runMainAndCaptureOutput(String stdin, Path storageFile) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        String originalStoragePath = System.getProperty("clothes.storage.path");

        ByteArrayInputStream testIn = new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream testPrintStream = new PrintStream(testOut, true, StandardCharsets.UTF_8);

        try {
            System.setIn(testIn);
            System.setOut(testPrintStream);
            System.setProperty("clothes.storage.path", storageFile.toString());
            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            if (originalStoragePath == null) {
                System.clearProperty("clothes.storage.path");
            } else {
                System.setProperty("clothes.storage.path", originalStoragePath);
            }
            testPrintStream.close();
        }

        return testOut.toString(StandardCharsets.UTF_8);
    }
}

