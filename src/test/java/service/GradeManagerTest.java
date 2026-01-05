package service;

import model.Grade;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GradeManagerTest {

    @Test
    public void testCalculateAverage() {
        List<Grade> grades = List.of(
                new Grade("MAT101", 90, LocalDate.now()),
                new Grade("ENG203", 70, LocalDate.now())
        );

        GradeManager gm = new GradeManager();
        assertEquals(80.0, gm.calculateAverage(grades), 0.01);
    }

    @Test
    public void testIsPassing() {
        List<Grade> grades = List.of(
                new Grade("SCI202", 65, LocalDate.now()),
                new Grade("HIS101", 75, LocalDate.now())
        );

        GradeManager gm = new GradeManager();
        assertTrue(gm.isPassing(grades));
    }

    @Test
    public void testIsFailing() {
        List<Grade> grades = List.of(
                new Grade("MAT101", 40, LocalDate.now()),
                new Grade("ENG203", 50, LocalDate.now())
        );

        GradeManager gm = new GradeManager();
        assertFalse(gm.isPassing(grades));
    }
}
