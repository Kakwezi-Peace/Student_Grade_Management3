package console;

import model.Student;
import service.StudentService;

import java.util.List;
import java.util.Scanner;

public class SearchStudentsConsole {
    private final StudentService studentService;

    public SearchStudentsConsole(StudentService studentService) {
        this.studentService = studentService;
    }

    public void run(Scanner sc) {
        System.out.println("\nSEARCH STUDENTS (Advanced)");
        System.out.println("Choose filter:");
        System.out.println("  1) By ID contains");
        System.out.println("  2) By Name contains");
        System.out.println("  3) By Email domain (e.g., university.edu)");
        System.out.println("  4) By Type (Regular/Honors)");
        System.out.print("Enter choice: ");
        String choice = sc.nextLine().trim();

        List<Student> results;

        switch (choice) {
            case "1" -> {
                System.out.print("Enter ID fragment: ");
                String idFrag = sc.nextLine().trim();
                results = studentService.searchByIdContains(idFrag);
            }
            case "2" -> {
                System.out.print("Enter name fragment: ");
                String nameFrag = sc.nextLine().trim();
                results = studentService.searchByNameContains(nameFrag);
            }
            case "3" -> {
                System.out.print("Enter email domain (e.g., university.edu): ");
                String domain = sc.nextLine().trim();
                results = studentService.searchByEmailDomain(domain);
            }
            case "4" -> {
                System.out.print("Enter type (Regular/Honors): ");
                String type = sc.nextLine().trim();
                results = studentService.searchByType(type);
            }
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        if (results.isEmpty()) {
            System.out.println("✗ No matching students.");
        } else {
            System.out.println("\n✓ Matches:");
            for (Student s : results) {
                System.out.printf("ID: %s | Name: %s | Email: %s | Phone: %s | Type: %s%n",
                        s.getId(), s.getName(), s.getEmail(), s.getPhone(), s.getType());
            }
        }
    }
}
