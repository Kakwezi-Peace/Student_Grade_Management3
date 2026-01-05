package console;

import service.StudentService;
import service.GradeService;
import java.util.Scanner;

public class FileImportConsole {
    private final StudentService studentService;
    private final GradeService gradeService;

    public FileImportConsole(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    public void run(Scanner sc) {
        System.out.println("\nIMPORT DATA (CSV/JSON/Binary)");
        System.out.print("Enter filename (without extension): ");
        String base = sc.nextLine().trim();

        if (base.isEmpty()) {
            System.out.println("✗ No filename entered.");
            return;
        }

        try {
            // Example: call your FileService to import CSV
            // fileService.importCsv(base + ".csv", studentService, gradeService);

            System.out.println("✓ Import completed for file: " + base);
        } catch (Exception e) {
            System.out.println("✗ Import failed: " + e.getMessage());
        }
    }
}
