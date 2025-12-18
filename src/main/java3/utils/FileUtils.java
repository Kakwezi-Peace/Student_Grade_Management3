package utils;

import java.nio.file.*;
import java.io.IOException;

public class FileUtils {
    public static void writeToFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }
}
