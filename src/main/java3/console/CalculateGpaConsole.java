package console;

import service.StudentService;
import service.GradeService;
import model.Student;
import java.util.Scanner;

public class CalculateGpaConsole {
    private final StudentService studentService;
    private final GradeService gradeService;

    public CalculateGpaConsole(StudentService s, GradeService g) {
        this.studentService = s;
        this.gradeService = g;
    }

    public void run(Scanner sc) {
        System.out.println("Calculating GPA for all students...");
        for (Student stu : studentService.listAll()) {
            double avg = stu.getGradeHistory().stream()
                    .mapToInt(model.Grade::getScore)
                    .average().orElse(0.0);
            double gpa = avg / 25.0; // simple proxy
            System.out.printf("%s (%s): GPA %.2f%n", stu.getName(), stu.getId(), gpa);
        }
    }
}
