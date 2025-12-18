package model;

import grade.GradeManager;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;

public class Student implements java.io.Serializable {
    private final String id;                // STU###
    private String name;
    private String email;
    private String phone;
    private String type;                    // Regular | Honors
    private LocalDate enrollmentDate;
    private final LinkedList<Grade> gradeHistory = new LinkedList<>(); // US-1: LinkedList

    public Student(String id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    public Student(String id) {

        this.id = id;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getType() { return type; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public LinkedList<Grade> getGradeHistory() { return gradeHistory; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setType(String type) { this.type = type; }
    public void setEnrollmentDate(LocalDate date) { this.enrollmentDate = date; }

    public void addGrade(Grade g) { gradeHistory.addLast(g); } // O(1) end insert

    public String getStudentId() {

        return "";
    }

    public String getStudentType() {

        return "";
    }



    public double getPassingGrade() {

        return 0;
    }

    public double calculateAverageGrade(GradeManager gradeManager) {
    return 0;

    }

    public boolean isPassing(GradeManager gradeManager) {

        return false;
    }
}
