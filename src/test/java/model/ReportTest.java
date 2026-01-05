package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    public void testReportFields() {
        Student s = new Student("STU020", "Alice");
        Grade g1 = new Grade("MAT101", 85, LocalDate.now());
        Grade g2 = new Grade("ENG203", 75, LocalDate.now());

        Report report = new Report(s, List.of(g1, g2), 3.2);

        assertEquals("Alice", report.getStudent().getName());
        assertEquals(2, report.getGrades().size());
        assertEquals(3.2, report.getGpa(), 0.01);
    }

    @Test
    public void testReportToString() {
        Student s = new Student("STU021", "Bob");
        Grade g = new Grade("SCI202", 90, LocalDate.now());

        Report report = new Report(s, List.of(g), 3.6);
        String text = report.toString();

        assertTrue(text.contains("Bob"));
        assertTrue(text.contains("SCI202"));
    }
}
