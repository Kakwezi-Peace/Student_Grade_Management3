package file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileOperationsTest {

    @Test
    public void testCSVFileWriteAndRead() throws IOException {
        Path path = Paths.get("test_students.csv");
        List<String> lines = List.of("STU001,Alice,3.9", "STU002,Bob,3.5");

        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        List<String> readLines = Files.readAllLines(path);
        assertEquals(2, readLines.size());
        assertTrue(readLines.get(0).contains("Alice"));

        Files.deleteIfExists(path);
    }

    @Test
    public void testFileExists() throws IOException {
        Path path = Paths.get("test_file.txt");
        Files.writeString(path, "Hello World");

        assertTrue(Files.exists(path));
        Files.deleteIfExists(path);
    }
}
