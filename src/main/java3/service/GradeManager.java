package service;

import model.Grade;

import java.util.List;

public class GradeManager {
    public static final double PASSING_GRADE = 60.0;

    /** Calculates average grade from a list of Grade objects. */
    public double calculateAverage(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) return 0.0;
        double sum = 0;
        for (Grade g : grades) {
            sum += g.getScore(); // ✅ use getScore(), not getValue()
        }
        return sum / grades.size();
    }

    /** Checks if a student is passing based on average grade. */
    public boolean isPassing(List<Grade> grades) {
        return calculateAverage(grades) >= PASSING_GRADE;
    }
}
