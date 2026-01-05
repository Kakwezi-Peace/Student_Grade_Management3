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
        switch (opt) {
            case 1 -> {
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
            case 2 -> {
                System.out.println("CONFIGURE: Weekly Grade Report Email");
                System.out.print("Enter day of week (1=Monday … 7=Sunday): ");
                int day = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter hour (0-23): ");
                int h = Integer.parseInt(sc.nextLine().trim());

                scheduler.scheduleWeeklyReport(() -> {
                    long start = System.currentTimeMillis();
                    studentService.listAll().forEach(reportService::buildDetailedReport);
                    long end = System.currentTimeMillis();
                    System.out.printf("Weekly grade reports emailed in %dms%n", (end - start));
                }, day, h);
                System.out.println("✓ Task scheduled successfully!");
            }
            case 3 -> {
                System.out.println("CONFIGURE: Monthly Performance Summary");
                System.out.print("Enter day of month (1-28): ");
                int day = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter hour (0-23): ");
                int h = Integer.parseInt(sc.nextLine().trim());

                scheduler.scheduleMonthlySummary(() -> {
                    long start = System.currentTimeMillis();
                    // Example: build summary for all students
                    studentService.listAll().forEach(reportService::buildDetailedReport);
                    long end = System.currentTimeMillis();
                    System.out.printf("Monthly performance summary generated in %dms%n", (end - start));
                }, day, h);
                System.out.println("✓ Task scheduled successfully!");
            }
            case 4 -> {
                System.out.println("CONFIGURE: Hourly Data Sync");
                scheduler.scheduleHourlySync(() -> {
                    long start = System.currentTimeMillis();
                    // Example: sync student data
                    System.out.println("Data sync started...");
                    long end = System.currentTimeMillis();
                    System.out.printf("Hourly data sync completed in %dms%n", (end - start));
                });
                System.out.println("✓ Task scheduled successfully!");
            }
            case 5 -> {
                System.out.println("CONFIGURE: Custom Schedule");
                System.out.print("Enter interval in minutes: ");
                int minutes = Integer.parseInt(sc.nextLine().trim());

                scheduler.scheduleCustom(() -> {
                    long start = System.currentTimeMillis();
                    System.out.println("Custom scheduled task executed.");
                    long end = System.currentTimeMillis();
                    System.out.printf("Custom task finished in %dms%n", (end - start));
                }, minutes);
                System.out.println("✓ Task scheduled successfully!");
            }
            case 6 -> System.out.println("Cancelled.");
            default -> System.out.println("Invalid choice.");
        }
    }
}
