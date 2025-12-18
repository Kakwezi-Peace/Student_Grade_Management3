

import collections.CollectionsRegistry;
import concurrent.ExecutorsConfig;
import console.*;   // includes GradeHistoryConsole, ViewStudentsConsole, etc.
import model.Student;
import service.*;
import utils.PathUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ensure all directories exist before exporting
        PathUtils.initAll();

        // Single AuditService instance
        AuditService auditService = new AuditService();

        // Registry and services wired with audit
        CollectionsRegistry registry = new CollectionsRegistry();
        StudentService studentService = new StudentService(registry.studentMap(), auditService);
        GradeService gradeService = new GradeService(registry);
        ReportService reportService = new ReportService(auditService);
        StatisticsService statisticsService = new StatisticsService();
        SchedulerService schedulerService = new SchedulerService();
        StreamService streamService = new StreamService();
        WatchServiceRunner watcher = new WatchServiceRunner();
        PatternSearchService patternSearchService = new PatternSearchService(auditService);

        // Seed sample students
        seedDemoStudents(studentService, gradeService);

        // Start file watcher in background (optional)
        Thread watcherThread = new Thread(watcher, "Watcher");
        watcherThread.setDaemon(true);
        watcherThread.start();

        Scanner sc = new Scanner(System.in);
        MainMenu menu = new MainMenu();

        boolean running = true;
        while (running) {
            int choice;
            try { choice = menu.promptChoice(sc); } catch (Exception e) { choice = 19; }
            switch (choice) {
                case 1 -> new AddStudentConsole(studentService).run(sc);
                case 2 -> new ViewStudentsConsole(studentService).run(sc);
                case 5 -> new FileExportConsole(studentService, reportService).run(sc);
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
                case 16 -> {
                    new PerformanceMonitorConsole().showThreadPool(ExecutorsConfig.fixedPool(5));
                }
                case 18 -> new AuditViewerConsole(auditService).run(sc);
                case 19 -> running = false;
                default -> System.out.println("Not implemented yet.");
            }
        }

        // Cleanup
        schedulerService.shutdown();
        auditService.shutdown();
        System.out.println("Exiting...");
    }

    private static void seedDemoStudents(StudentService studentService, GradeService gradeService) {
        Student s1 = new Student("STU001", "peace kakwezi");
        s1.setEmail("peace.peace@university.edu");
        s1.setPhone("+1-555-012-3456");
        s1.setType("Regular");
        s1.setEnrollmentDate(LocalDate.of(2024,11,3));

        Student s2 = new Student("STU002", "Germain Dan");
        s2.setEmail("dan.dan@university.edu");
        s2.setPhone("123-456-7890");
        s2.setType("Honors");
        s2.setEnrollmentDate(LocalDate.of(2024,11,3));

        Student s3 = new Student("STU003", "Emma Wilson");
        s3.setEmail("emma.w@college.org");
        s3.setPhone("(555) 123-4567");
        s3.setType("Honors");
        s3.setEnrollmentDate(LocalDate.of(2024,11,3));

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
