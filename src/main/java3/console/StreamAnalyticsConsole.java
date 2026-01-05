package console;

import model.Grade;
import model.Student;
import service.GradeService;
import service.StreamService;
import service.StudentService;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StreamAnalyticsConsole {
    private final StudentService studentService;
    private final GradeService gradeService;
    private final StreamService streamService;

    public StreamAnalyticsConsole(StudentService studentService, GradeService gradeService, StreamService streamService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.streamService = streamService;
    }

    public void run(Scanner sc) {
        List<Student> students = List.copyOf(studentService.listAll());

        System.out.println("=== STREAM ANALYTICS ===");

        // Honors students above GPA threshold
        System.out.println("Honors students with GPA > 3.5:");
        streamService.honorsWithGpaAbove(students, 3.5).forEach(s ->
                System.out.println(" - " + s.getId() + " " + s.getName()));

        // Average per subject
        System.out.println("\nAverage per subject:");
        Map<String, Double> avg = streamService.averagePerSubject(students);
        avg.forEach((subj, val) -> System.out.printf("%s: %.1f%n", subj, val));

        // Unique course codes
        System.out.println("\nUnique course codes:");
        streamService.uniqueCourseCodes(students).forEach(System.out::println);

        // Top 5 students
        System.out.println("\nTop 5 students by average grade:");
        streamService.topNByAverage(students, 5).forEach(s -> {
            double average = s.getGradeHistory().stream()
                    .mapToInt(Grade::getScore)
                    .average()
                    .orElse(0.0);
            System.out.printf("%s %s (avg=%.1f)%n", s.getId(), s.getName(), average);
        });

        // Group by grade range
        System.out.println("\nGrouped by grade range:");
        streamService.groupByGradeRange(students).forEach((range, list) -> {
            System.out.println(range + ": " + list.size() + " students");
        });

        // Search helpers
        System.out.println("\nAny honors? " + streamService.anyHonors(students));
        System.out.println("All have email? " + streamService.allHaveEmail(students));
        System.out.println("None with invalid ID? " + streamService.noneWithInvalidId(students));

        // Sequential vs parallel comparison
        System.out.println("\nSequential vs Parallel comparison (Honors filter):");
        Map<String, Long> perf = streamService.compareSequentialVsParallel(students, s -> "Honors".equalsIgnoreCase(s.getType()));
        perf.forEach((k, v) -> System.out.println(k + ": " + v));

        // CSV streaming demo (optional)
        try {
            Path path = Path.of("./data/csv/csv");
            long lines = streamService.processCsvStreaming(path);
            System.out.println("\nCSV streaming processed lines: " + lines);
        } catch (Exception e) {
            System.out.println("CSV streaming demo skipped (file not found).");
        }

        System.out.println("\n=== END STREAM ANALYTICS ===");
    }
}
