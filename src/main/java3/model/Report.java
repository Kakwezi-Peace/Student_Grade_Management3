package model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a generated report for a student.
 */
public class Report implements java.io.Serializable {
    private final Student student;
    private final List<Grade> grades;
    private final double gpa;

    public Report(Student student, List<Grade> grades, double gpa) {
        this.student = Objects.requireNonNull(student);
        this.grades = Objects.requireNonNull(grades);
        this.gpa = gpa;
    }

    // ✅ Accessors
    public Student getStudent() {
        return student;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public double getGpa() {
        return gpa;
    }

    // ✅ Nice string representation for printing reports
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Report for Student: ").append(student.getName())
                .append(" (ID: ").append(student.getId()).append(")\n");
        sb.append("Type: ").append(student.getStudentType()).append("\n");
        sb.append("Email: ").append(student.getEmail() != null ? student.getEmail() : "N/A").append("\n");
        sb.append("GPA: ").append(String.format("%.2f", gpa)).append("\n");
        sb.append("Grades:\n");

        if (grades.isEmpty()) {
            sb.append("  No grades recorded.\n");
        } else {
            for (Grade g : grades) {
                sb.append("  - ").append(g.getCourseCode())
                        .append(": ").append(g.getScore())
                        .append(" (Recorded: ").append(g.getRecordedAt()).append(")\n");
            }
        }
        return sb.toString();
    }
}
