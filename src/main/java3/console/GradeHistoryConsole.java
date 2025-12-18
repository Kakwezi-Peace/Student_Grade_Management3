package console;

import model.Grade;
import model.Student;
import service.GradeService;
import service.StudentService;

import java.util.List;
import java.util.Scanner;

public class GradeHistoryConsole {
    private final StudentService studentService;
    private final GradeService gradeService;

    public GradeHistoryConsole(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    public void run(Scanner sc) {
        System.out.println("\nQUERY GRADE HISTORY");
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();

        Student s = studentService.getById(id);
        if (s == null) {
            System.out.println("✗ Student not found: " + id);
            return;
        }

        List<Grade> grades = gradeService.getGradesForStudent(s);
        if (grades == null || grades.isEmpty()) {
            System.out.println("✗ No grades recorded for " + s.getId());
            return;
        }

        System.out.println("\n✓ Grade history for " + s.getName() + ":");
        for (Grade g : grades) {
            System.out.printf("Course: %s | Score: %d | Recorded: %s%n",
                    g.getCourseCode(), g.getScore(), g.getRecordedAt());
        }

        double avg = grades.stream().mapToInt(Grade::getScore).average().orElse(0.0);
        System.out.printf("Average Score: %.2f%n", avg);
    }
}
