package service;

import model.Student;
import java.util.ArrayList;
import java.util.List;

/*
 * US-4: Manages multiple student records
 */
public class StudentRepository {

    private final List<Student> students = new ArrayList<>();

    // Add a validated student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Return all students
    public List<Student> getAllStudents() {
        return students;
    }

    // Check if repository is empty
    public boolean isEmpty() {
        return students.isEmpty();
    }
}
