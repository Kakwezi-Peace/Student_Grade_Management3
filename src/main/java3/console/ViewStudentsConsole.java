package console;

import service.StudentService;
import model.Student;

import java.util.Scanner;

public class ViewStudentsConsole {
    private final StudentService studentService;

    public ViewStudentsConsole(StudentService studentService) {
        this.studentService = studentService;
    }

    public void run(Scanner sc) {
        System.out.println("\nALL STUDENTS:");
        if (studentService.listAll().isEmpty()) {
            System.out.println("✗ No students found.");
            return;
        }

        for (Student s : studentService.listAll()) {
            System.out.printf("ID: %s | Name: %s | Email: %s | Phone: %s | Type: %s | Enrolled: %s%n",
                    s.getId(),
                    s.getName(),
                    s.getEmail(),
                    s.getPhone(),
                    s.getType(),
                    s.getEnrollmentDate());
        }
    }
}
