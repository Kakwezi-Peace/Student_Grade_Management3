package console;

import model.Student;
import service.StudentService;
import utils.ValidationResult;
import utils.ValidationUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class AddStudentConsole {
    private final StudentService studentService;

    public AddStudentConsole(StudentService studentService) {
        this.studentService = studentService;
    }

    public void run(Scanner sc) {
        System.out.println("\nADD STUDENT (with validation)");

        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        ValidationResult idRes = ValidationUtils.validateStudentId(id);
        System.out.println(idRes);
        if (!idRes.isValid()) return;

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();
        ValidationResult nameRes = ValidationUtils.validateName(name);
        System.out.println(nameRes);
        if (!nameRes.isValid()) return;

        System.out.print("Enter Email Address: ");
        String email = sc.nextLine().trim();
        ValidationResult emailRes = ValidationUtils.validateEmail(email);
        System.out.println(emailRes);
        if (!emailRes.isValid()) return;

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine().trim();
        ValidationResult phoneRes = ValidationUtils.validatePhone(phone);
        System.out.println(phoneRes);
        if (!phoneRes.isValid()) return;

        System.out.print("Student Type (Regular/Honors): ");
        String type = sc.nextLine().trim();

        System.out.print("Enter Enrollment Date (YYYY-MM-DD): ");
        String dateStr = sc.nextLine().trim();
        ValidationResult dateRes = ValidationUtils.validateDate(dateStr);
        System.out.println(dateRes);
        if (!dateRes.isValid()) return;

        // ✅ Create student with validated data
        Student s = new Student(id, name);
        s.setEmail(email);
        s.setPhone(phone);
        s.setType(type);
        s.setEnrollmentDate(LocalDate.parse(dateStr));

        // ✅ Add the student to the service
        studentService.addStudent(s);

        // ✅ Confirmation output
        System.out.println("\n✓ Student added successfully!");
        System.out.println("All inputs validated with regex patterns");
        System.out.println("Student ID: " + s.getId());
        System.out.println("Name: " + s.getName());
        System.out.println("Email: " + s.getEmail());
        System.out.println("Phone: " + s.getPhone());
        System.out.println("Type: " + s.getType());
        System.out.println("Enrolled: " + s.getEnrollmentDate());
    }
}
