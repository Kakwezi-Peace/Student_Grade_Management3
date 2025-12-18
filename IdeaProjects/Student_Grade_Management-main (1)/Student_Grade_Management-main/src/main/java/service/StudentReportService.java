package service;



import model.Student;
import java.util.List;

/*
 * US-4: Responsible for formatted output and reporting
 */
public class StudentReportService {

    public void printStudentReport(List<Student> students, StudentProcessor processor) {

        System.out.println("\n===== STUDENT REPORT =====");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        for (Student s : students) {
            s.display();
            System.out.println("Status : " +
                    (processor.isPassing(s) ? "PASS" : "FAIL"));
            System.out.println("--------------------------");
        }

        double avg = processor.calculateAverageGrade(students);
        int pass = processor.countPassing(students);
        int fail = processor.countFailing(students);

        System.out.println("\n===== SUMMARY =====");
        System.out.println("Total Students : " + students.size());
        System.out.println("Average Grade  : " + avg);
        System.out.println("Passed         : " + pass);
        System.out.println("Failed         : " + fail);
    }
}
