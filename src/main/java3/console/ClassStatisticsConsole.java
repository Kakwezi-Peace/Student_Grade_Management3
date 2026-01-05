package console;

import service.StudentService;
import service.GradeService;
import model.Student;
import java.util.Scanner;

public class ClassStatisticsConsole {
    private final StudentService studentService;
    private final GradeService gradeService;

    public ClassStatisticsConsole(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    public void run(Scanner sc) {
        System.out.println("\nCLASS STATISTICS");

        double overallAvg = studentService.listAll().stream()
                .flatMap(s -> s.getGradeHistory().stream())
                .mapToInt(g -> g.getScore())
                .average().orElse(0.0);

        System.out.printf("Overall class average: %.2f%n", overallAvg);

        // Show top student
        Student top = studentService.listAll().stream()
                .max((a, b) -> Double.compare(
                        a.getGradeHistory().stream().mapToInt(g -> g.getScore()).average().orElse(0.0),
                        b.getGradeHistory().stream().mapToInt(g -> g.getScore()).average().orElse(0.0)))
                .orElse(null);

        if (top != null) {
            double topAvg = top.getGradeHistory().stream().mapToInt(g -> g.getScore()).average().orElse(0.0);
            System.out.printf("Top student: %s (%s) with average %.2f%n", top.getName(), top.getId(), topAvg);
        }
    }
}
