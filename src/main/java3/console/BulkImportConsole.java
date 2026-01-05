package console;

import service.StudentService;
import service.GradeService;
import model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class BulkImportConsole {
    private final StudentService studentService;
    private final GradeService gradeService;

    public BulkImportConsole(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    public void run(Scanner sc) {
        System.out.println("\nBULK IMPORT GRADES");

        System.out.print("Enter CSV filename (e.g. grades.csv): ");
        String filename = sc.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("✗ No filename entered.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                // Expect format: studentId,courseCode,score
                String[] parts = line.split(",");
                if (parts.length != 3) continue;

                String studentId = parts[0].trim();
                String courseCode = parts[1].trim();
                int score = Integer.parseInt(parts[2].trim());

                Student s = studentService.getById(studentId);
                if (s != null) {
                    gradeService.recordGrade(s, courseCode, score);
                    count++;
                }
            }
            System.out.println("✓ Imported " + count + " grades from " + filename);
        } catch (Exception e) {
            System.out.println("✗ Bulk import failed: " + e.getMessage());
        }
    }
}
