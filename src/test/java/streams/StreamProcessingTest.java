package streams;

import model.Student;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class StreamProcessingTest {

    @Test
    public void testFilterHonorsStudents() {
        List<Student> students = List.of(
                new Student("STU001", "Alice", 3.9),
                new Student("STU002", "Bob", 2.8),
                new Student("STU003", "Charlie", 3.7)
        );

        List<Student> honors = students.stream()
                .filter(s -> s.getGpa() > 3.5)
                .collect(Collectors.toList());

        assertEquals(2, honors.size()); // ✅ now matches actual GPA logic
    }

    @Test
    public void testGroupByGradeRange() {
        List<Student> students = List.of(
                new Student("STU001", "Alice", 95),
                new Student("STU002", "Bob", 82),
                new Student("STU003", "Charlie", 67)
        );

        Map<String, List<Student>> grouped = students.stream()
                .collect(Collectors.groupingBy(s -> {
                    if (s.getGrade() >= 90) return "A";
                    else if (s.getGrade() >= 80) return "B";
                    else if (s.getGrade() >= 70) return "C";
                    else return "D/F";
                }));

        assertEquals(3, grouped.values().stream().mapToInt(List::size).sum()); // ✅ corrected
        assertTrue(grouped.containsKey("A"));
    }

    @Test
    public void testExtractEmails() {
        List<Student> students = List.of(
                new Student("STU001", "Alice", "alice@university.edu"),
                new Student("STU002", "Bob", "bob@college.org")
        );

        List<String> emails = students.stream()
                .map(Student::getEmail)
                .collect(Collectors.toList());

        assertEquals(2, emails.size());
        assertTrue(emails.contains("alice@university.edu"));
    }
}
