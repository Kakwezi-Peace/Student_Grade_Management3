import collections.CollectionsRegistry;
import concurrent.ExecutorsConfig;
import console.*;
import model.Student;
import service.*;
import utils.PathUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ensure all directories exist before exporting
        PathUtils.initAll();

        // Core infrastructure
        CollectionsRegistry registry = new CollectionsRegistry();
        ThreadSafeCache cache = new ThreadSafeCache();          // US-8 cache
        AuditService auditService = new AuditService();         // US-9 audit

        // Warm frequently accessed data (optional demo)
        Map<String, Object> warmData = new HashMap<>();
        cache.warm(warmData, 5 * 60 * 1000);

        // Services
        StudentService studentService = new StudentService(registry.studentMap(), auditService, cache);
        GradeService gradeService = new GradeService(registry, cache);
        ReportService reportService = new ReportService(auditService);
        StatisticsService statisticsService = new StatisticsService();
        SchedulerService schedulerService = new SchedulerService();
        StreamService streamService = new StreamService();
        WatchServiceRunner watcher = new WatchServiceRunner();
        PatternSearchService patternSearchService = new PatternSearchService(auditService);

        // Seed sample students and grades
        seedDemoStudents(studentService, gradeService);

        // Start file watcher in background (optional)
        Thread watcherThread = new Thread(watcher, "Watcher");
        watcherThread.setDaemon(true);
        watcherThread.start();

        // Consoles
        Scanner sc = new Scanner(System.in);
        MainMenu menu = new MainMenu();
        CacheManagementConsole cacheConsole = new CacheManagementConsole(cache);
        AuditViewerConsole auditConsole = new AuditViewerConsole(auditService);
        StreamAnalyticsConsole streamConsole = new StreamAnalyticsConsole(studentService, gradeService, streamService);

        // Main loop
        boolean running = true;
        while (running) {
            int choice;
            try {
                choice = menu.promptChoice(sc);
            } catch (Exception e) {
                choice = 19;
            }
            switch (choice) {
                case 1 -> new AddStudentConsole(studentService).run(sc);
                case 2 -> new ViewStudentsConsole(studentService).run(sc);
                case 3 -> {
                    System.out.print("Enter student ID: ");
                    String id = sc.nextLine().trim();
                    Student s = studentService.getById(id);
                    if (s == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.print("Enter course code: ");
                    String course = sc.nextLine().trim();
                    System.out.print("Enter score: ");
                    int score = Integer.parseInt(sc.nextLine().trim());
                    gradeService.recordGrade(s, course, score);
                    System.out.println("Grade recorded.");
                }
                case 4 -> {
                    System.out.print("Enter student ID: ");
                    String id = sc.nextLine().trim();
                    GradeService.GradeReport report = gradeService.getReportForStudent(id);
                    if (report == null) {
                        System.out.println("No report available.");
                    } else {
                        System.out.println("Report for " + report.getStudentName());
                        System.out.printf("Average: %.1f%n", report.getAverage());
                        report.getSubjectAverages().forEach((subj, avg) ->
                                System.out.printf(" - %s: %.1f%n", subj, avg));
                    }
                }
                case 5 -> new FileExportConsole(studentService, reportService).run(sc);
                case 6 -> new FileImportConsole(studentService, gradeService).run(sc);   // ✅ Import Data
                case 7 -> new BulkImportConsole(studentService, gradeService).run(sc);   // ✅ Bulk Import Grades
                case 8 -> new CalculateGpaConsole(studentService, gradeService).run(sc); // ✅ Calculate GPA
                case 9 -> new ClassStatisticsConsole(studentService, gradeService).run(sc); // ✅ View Class Statistics
                case 10 -> {
                    DashboardRefresher refresher = new DashboardRefresher(new ArrayList<>(studentService.listAll()), statisticsService);
                    Thread t = new Thread(refresher, "Dashboard");
                    t.setDaemon(true);
                    t.start();
                    System.out.println("Press ENTER to stop dashboard...");
                    sc.nextLine();
                    refresher.stop();
                }
                case 11 -> new BatchReportsConsole(studentService, reportService).run(sc);
                case 12 -> new SearchStudentsConsole(studentService).run(sc);
                case 13 -> new PatternSearchConsole(studentService).run(sc);
                case 14 -> new GradeHistoryConsole(studentService, gradeService).run(sc);
                case 15 -> new ScheduledTasksConsole(schedulerService, studentService, reportService).run(sc);
                case 16 -> new PerformanceMonitorConsole().showThreadPool(ExecutorsConfig.fixedPool(5));
                case 17 -> cacheConsole.run(sc);
                case 18 -> auditConsole.run(sc);
                case 20 -> streamConsole.run(sc);
                case 19 -> running = false;
                default -> System.out.println("Not implemented yet.");
            }
        }

        // Cleanup
        schedulerService.shutdown();
        auditService.shutdown();
        cache.shutdown();
        System.out.println("Exiting...");
    }

    private static void seedDemoStudents(StudentService studentService, GradeService gradeService) {
        Student s1 = new Student("STU001", "peace kakwezi");
        s1.setEmail("peace.peace@university.edu");
        s1.setPhone("+1-555-012-3456");
        s1.setType("Regular");
        s1.setEnrollmentDate(LocalDate.of(2024, 11, 3));

        Student s2 = new Student("STU002", "Germain Dan");
        s2.setEmail("dan.dan@university.edu");
        s2.setPhone("123-456-7890");
        s2.setType("Honors");
        s2.setEnrollmentDate(LocalDate.of(2024, 11, 3));

        Student s3 = new Student("STU003", "Emma Wilson");
        s3.setEmail("emma.w@college.org");
        s3.setPhone("(555) 123-4567");
        s3.setType("Honors");
        s3.setEnrollmentDate(LocalDate.of(2024, 11, 3));

        studentService.addStudent(s1);
        studentService.addStudent(s2);
        studentService.addStudent(s3);

        gradeService.recordGrade(s1, "MAT101", 95);
        gradeService.recordGrade(s1, "ENG203", 88);
        gradeService.recordGrade(s2, "MAT101", 92);
        gradeService.recordGrade(s2, "CSC305", 90);
        gradeService.recordGrade(s3, "ENG203", 84);
        gradeService.recordGrade(s3, "MAT101", 78);
    }
}
