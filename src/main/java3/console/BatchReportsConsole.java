package console;

import concurrent.BatchReportGenerator;
import model.Student;
import service.ReportService;
import service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class BatchReportsConsole {
    private final StudentService studentService;
    private final ReportService reportService;

    public BatchReportsConsole(StudentService studentService, ReportService reportService) {
        this.studentService = studentService;
        this.reportService = reportService;
    }

    public void run(Scanner sc) {
        List<Student> students = new ArrayList<Student>((Collection<? extends Student>) studentService.listAll());
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("\nGENERATE BATCH REPORTS");
        System.out.printf("Available Processors: %d%n", processors);
        System.out.println("Recommended Threads: 4-8");
        System.out.print("Enter number of threads (1-8): ");
        int threads = Integer.parseInt(sc.nextLine().trim());
        BatchReportGenerator generator = new BatchReportGenerator(Math.max(1, Math.min(8, threads)));
        generator.generateReports(students, reportService);
    }
}
