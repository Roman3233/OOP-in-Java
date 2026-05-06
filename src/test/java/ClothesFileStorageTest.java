import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClothesFileStorageTest {

    @Test
    void shouldAppendClothesToFile() throws IOException {
        Path storageFile = Files.createTempFile("clothes-storage-append", ".txt");
        ClothesFileStorage storage = new ClothesFileStorage(storageFile.toString());

        storage.appendClothes(new Pants("501", Size.M, 2499.99, "Denim", 82));
        storage.appendClothes(new Jacket("Storm", Size.L, 3999.99, "Membrane", 7));

        String content = Files.readString(storageFile, StandardCharsets.UTF_8);

        assertEquals(
                "Pants;501;M;2499.99;Denim;82.0" + System.lineSeparator()
                        + "Jacket;Storm;L;3999.99;Membrane;7" + System.lineSeparator(),
                content
        );
    }

    @Test
    void shouldLoadClothesFromFile() throws IOException {
        Path storageFile = Files.createTempFile("clothes-storage-load", ".txt");
        Files.writeString(
                storageFile,
                "Shirt;Oxford;L;1599.5;Cotton;67.0" + System.lineSeparator()
                        + "Hat;Safari;M;899.99;Cotton;9.0" + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        ClothesFileStorage storage = new ClothesFileStorage(storageFile.toString());
        List<Clothes> clothesList = storage.loadClothes();

        assertEquals(2, clothesList.size());
        assertInstanceOf(Shirts.class, clothesList.get(0));
        assertInstanceOf(Hat.class, clothesList.get(1));
        assertEquals("Oxford", clothesList.get(0).getName());
        assertEquals("Safari", clothesList.get(1).getName());
    }

    @Test
    void shouldUpdateMatchingClothesInFile() throws IOException {
        Path storageFile = Files.createTempFile("clothes-storage-update", ".txt");
        Files.writeString(
                storageFile,
                String.join(System.lineSeparator(),
                        "Pants;501;M;2499.99;Denim;82.0",
                        "Pants;501;M;2499.99;Denim;82.0",
                        "Hat;Safari;M;899.99;Cotton;9.0"
                ) + System.lineSeparator(),
                StandardCharsets.UTF_8
        );

        ClothesFileStorage storage = new ClothesFileStorage(storageFile.toString());
        boolean updated = storage.updateClothes(
                new Pants("501", Size.M, 2499.99, "Denim", 82),
                new Pants("502", Size.M, 2499.99, "Denim", 82)
        );

        String content = Files.readString(storageFile, StandardCharsets.UTF_8);

        assertTrue(updated);
        assertTrue(content.contains("Pants;502;M;2499.99;Denim;82.0"));
        assertTrue(!content.contains("Pants;501;M;2499.99;Denim;82.0"));
        assertTrue(content.contains("Hat;Safari;M;899.99;Cotton;9.0"));
    }
}
