package utils;

import model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Consumer;


 // Expected header: ID,Name,Email,Phone,Type,EnrollmentDate

public final class CsvStreamer {
    private CsvStreamer() {}

    public static void streamStudents(Path csvPath, Consumer<Student> onStudent) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String header = reader.readLine(); // skip header
            if (header == null) return;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;

                Student s = new Student(parts[0], parts[1]);
                s.setEmail(emptyToNull(parts[2]));
                s.setPhone(emptyToNull(parts[3]));
                s.setType(emptyToNull(parts[4]));
                if (!parts[5].isBlank()) {
                    s.setEnrollmentDate(LocalDate.parse(parts[5]));
                }
                onStudent.accept(s);
            }
        }
    }

    private static String emptyToNull(String s) {
        return s == null || s.isBlank() ? null : s;
    }
}
