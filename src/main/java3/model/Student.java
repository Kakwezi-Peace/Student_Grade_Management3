package model;

import service.GradeManager;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;

public class Student implements java.io.Serializable {
    private final String id;
    private String name;
    private String email;
    private String phone;
    private String type;
    private LocalDate enrollmentDate;
    private final LinkedList<Grade> gradeHistory = new LinkedList<>();

    // Constructors
    public Student(String id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    public Student(String id) {
        this.id = Objects.requireNonNull(id);
    }

    // ✅ GPA constructor stores as double properly
    public Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.type = "Regular";
        // store GPA as a grade scaled to 100
        this.gradeHistory.add(new Grade("GPA", (int) Math.round(gpa * 25), LocalDate.now()));
    }

    public Student(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.type = "Regular";
        this.gradeHistory.add(new Grade("General", grade, LocalDate.now()));
    }

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getType() { return type; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public LinkedList<Grade> getGradeHistory() { return gradeHistory; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setType(String type) { this.type = type; }
    public void setEnrollmentDate(LocalDate date) { this.enrollmentDate = date; }

    // Grade operations
    public void addGrade(Grade g) { gradeHistory.addLast(g); }

    // Helper methods
    public double getGpa() {
        if (gradeHistory.isEmpty()) return 0.0;
        // convert score back to GPA scale (score/25)
        return gradeHistory.stream().mapToInt(Grade::getScore).average().orElse(0.0) / 25.0;
    }

    public int getGrade() {
        if (gradeHistory.isEmpty()) return 0;
        return gradeHistory.getLast().getScore();
    }

    public String getStudentId() { return id; }
    public String getStudentType() { return type != null ? type : "Regular"; }
    public double getPassingGrade() { return GradeManager.PASSING_GRADE; }

    public double calculateAverageGrade(GradeManager gradeManager) {
        if (gradeHistory.isEmpty()) return 0.0;
        return gradeManager.calculateAverage(gradeHistory);
    }

    public boolean isPassing(GradeManager gradeManager) {
        return calculateAverageGrade(gradeManager) >= getPassingGrade();
    }

    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s', type='%s', email='%s'}",
                id, name, getStudentType(), email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
