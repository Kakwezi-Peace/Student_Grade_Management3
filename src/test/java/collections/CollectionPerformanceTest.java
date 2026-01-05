package collections;

import model.Student;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionPerformanceTest {

    @Test
    public void testHashMapLookupPerformance() {
        Map<String, Student> studentMap = new HashMap<>();
        studentMap.put("STU001", new Student("STU001", "Alice Johnson"));
        studentMap.put("STU002", new Student("STU002", "Bob Smith"));

        assertTrue(studentMap.containsKey("STU001"));
        assertEquals("Alice Johnson", studentMap.get("STU001").getName());
    }

    @Test
    public void testTreeMapSortingByGPA() {
        TreeMap<Double, List<Student>> gpaRankings = new TreeMap<>(Collections.reverseOrder());
        gpaRankings.put(3.9, List.of(new Student("STU001", "Alice")));
        gpaRankings.put(3.5, List.of(new Student("STU002", "Bob")));

        assertEquals(3.9, gpaRankings.firstKey());
        assertEquals("Alice", gpaRankings.get(3.9).get(0).getName());
    }

    @Test
    public void testHashSetUniqueCourses() {
        Set<String> courses = new HashSet<>();
        courses.add("MAT101");
        courses.add("MAT101"); // duplicate ignored
        courses.add("ENG203");

        assertEquals(2, courses.size());
        assertTrue(courses.contains("ENG203"));
    }
}
