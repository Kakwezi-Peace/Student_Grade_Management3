package model;

import java.util.List;

public class Report implements java.io.Serializable {
    private final Student student;
    private final List<Grade> grades;
    private final double gpa;

    public Report(Student student, List<Grade> grades, double gpa) {
        this.student = student;
        this.grades = grades;
        this.gpa = gpa;
    }

    public Student getStudent() { return student; }
    public List<Grade> getGrades() { return grades; }
    public double getGpa() { return gpa; }
}
