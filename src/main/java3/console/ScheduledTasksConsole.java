package console;


import model.Student;
import service.ReportService;
import service.SchedulerService;
import service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScheduledTasksConsole {
    private final SchedulerService scheduler;
    private final StudentService studentService;
    private final ReportService reportService;

    public ScheduledTasksConsole(SchedulerService scheduler, StudentService studentService, ReportService reportService) {
        this.scheduler = scheduler;
        this.studentService = studentService;
        this.reportService = reportService;
    }

    public void run(Scanner sc) {
        System.out.println("\nSCHEDULE AUTOMATED TASKS");
        System.out.println("1. Daily GPA Recalculation");
        System.out.println("2. Weekly Grade Report Email");
        System.out.println("3. Monthly Performance Summary");
        System.out.println("4. Hourly Data Sync");
        System.out.println("5. Custom Schedule");
        System.out.println("6. Cancel");
        System.out.print("Select option (1-6): ");
        int opt = Integer.parseInt(sc.nextLine().trim());
        if (opt == 1) {
            System.out.println("CONFIGURE: Daily GPA Recalculation");
            System.out.print("Enter hour (0-23): ");
            int h = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter minute (0-59): ");
            int m = Integer.parseInt(sc.nextLine().trim());

            List<Student> targets = new ArrayList<>(studentService.listAll());
            scheduler.scheduleDailyGpaRecalc(() -> {
                long start = System.currentTimeMillis();
                targets.forEach(reportService::buildDetailedReport);
                long end = System.currentTimeMillis();
                System.out.printf("Daily GPA recalculation done in %dms%n", (end - start));
            }, h, m);
            System.out.println("✓ Task scheduled successfully!");
        }
    }
}
