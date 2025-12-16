import model.Student;
import service.*;

public class Main {

    public static void main(String[] args) {

        StudentService studentService = new StudentService();
        StudentRepository repository = new StudentRepository();
        StudentProcessor processor = new StudentProcessor();
        StudentReportService reportService = new StudentReportService();

        // Create students (US-3 validation)
        Student s1 = studentService.createStudent(
                "ST301", "Peace", "Kakwezi",
                "peace@gmail.com", "0781234567",
                "CSC101", "2024-11-30", 95
        );

        Student s2 = studentService.createStudent(
                "ST303", "Mutoni", "Joy",
                "joy@gmail.com", "0789991111",
                "CSC102", "2024-10-15", 45
        );

        // Store students (US-4)
        if (s1 != null) repository.addStudent(s1);
        if (s2 != null) repository.addStudent(s2);

        // Generate report (US-4)
        reportService.printStudentReport(
                repository.getAllStudents(),
                processor
        );
    }
}
