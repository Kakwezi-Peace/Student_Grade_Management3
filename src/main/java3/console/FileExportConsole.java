package console;

import model.Report;
import model.Student;
import service.ReportService;
import service.StudentService;

import java.util.Scanner;

public class FileExportConsole {
    private final StudentService studentService;
    private final ReportService reportService;

    public FileExportConsole(StudentService studentService, ReportService reportService) {
        this.studentService = studentService;
        this.reportService = reportService;
    }

    public void run(Scanner sc) {
        System.out.println("\nEXPORT GRADE REPORT (CSV/JSON/Binary)");

        // Ask for student ID
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();

        Student student = studentService.getById(id);
        if (student == null) {
            System.out.println("✗ Student not found: " + id);
            return;
        }

        // Ask for base filename
        System.out.print("Enter base filename (no extension): ");
        String base = sc.nextLine().trim();
        if (base.isEmpty()) {
            base = "report-" + id;
        }

        try {
            // Build report for the student
            Report report = reportService.buildReport(student);

            // Export report in all formats
            ReportService.ExportResult result = reportService.exportAll(base, report);

            // Print results
            System.out.println("\n✓ Export completed:");
            System.out.println(" - CSV:  " + result.csv().toAbsolutePath());
            System.out.println(" - JSON: " + result.json().toAbsolutePath());
            System.out.println(" - BIN:  " + result.bin().toAbsolutePath());
        } catch (Exception e) {
            System.out.println("✗ Export failed: " + e.getMessage());
        }
    }
}
