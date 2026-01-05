package integration;

import model.Student;
import model.Grade;
import service.GradeManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MenuIntegrationTest {

    @Test
    public void testAddGradeAndCalculateAverage() {
        Student s = new Student("STU010", "Peace");
        s.addGrade(new Grade("MAT101", 90, LocalDate.now()));
        s.addGrade(new Grade("ENG203", 80, LocalDate.now()));

        GradeManager gm = new GradeManager();
        double avg = s.calculateAverageGrade(gm);

        assertEquals(85.0, avg, 0.01);
    }

    @Test
    public void testIsPassingIntegration() {
        Student s = new Student("STU011", "Peace");
        s.addGrade(new Grade("SCI202", 40, LocalDate.now()));
        s.addGrade(new Grade("HIS101", 50, LocalDate.now()));

        GradeManager gm = new GradeManager();
        assertFalse(s.isPassing(gm));
    }
}
