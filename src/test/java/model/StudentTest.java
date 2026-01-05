package model;

import org.junit.jupiter.api.Test;
import service.GradeManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    public void testCalculateAverageGrade() {
        Student s = new Student("STU001", "Alice");
        s.addGrade(new Grade("MAT101", 85, LocalDate.now()));
        s.addGrade(new Grade("ENG203", 75, LocalDate.now()));

        GradeManager gm = new GradeManager();
        double avg = s.calculateAverageGrade(gm);

        assertEquals(80.0, avg, 0.01);
    }

    @Test
    public void testIsPassingTrue() {
        Student s = new Student("STU002", "Bob");
        s.addGrade(new Grade("SCI202", 70, LocalDate.now()));
        s.addGrade(new Grade("HIS101", 65, LocalDate.now()));

        GradeManager gm = new GradeManager();
        assertTrue(s.isPassing(gm));
    }

    @Test
    public void testIsPassingFalse() {
        Student s = new Student("STU003", "Charlie");
        s.addGrade(new Grade("MAT101", 40, LocalDate.now()));
        s.addGrade(new Grade("ENG203", 50, LocalDate.now()));

        GradeManager gm = new GradeManager();
        assertFalse(s.isPassing(gm));
    }
}
