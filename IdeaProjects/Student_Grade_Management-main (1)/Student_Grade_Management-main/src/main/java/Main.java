import model.Student;
import service.*;

public class Main {

    public static void main(String[] args) {


        // US-3: Validation & Creation

        StudentService studentService = new StudentService();
        StudentRepository repository = new StudentRepository();
        StudentProcessor processor = new StudentProcessor();
        StudentReportService reportService = new StudentReportService();

        // Create students (US-3)
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


        // US-4: Store Students

        if (s1 != null) repository.addStudent(s1);
        if (s2 != null) repository.addStudent(s2);


        // US-4: Manual Report

        reportService.printStudentReport(
                repository.getAllStudents(),
                processor
        );


        // US-4 ADDITION: AUTOMATED SCHEDULER (NEW FEATURE)

        SchedulerService scheduler = new SchedulerService(
                repository,
                processor,
                reportService
        );

        scheduler.start();

        // US-4: Proper shutdown on application exit

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
        }));
    }
}
