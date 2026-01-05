package service;

import model.Grade;
import model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamService {

    // Honors students with GPA (derived from grades) above threshold
    // Note: If Student has a real GPA field, replace calculation with s.getGpa()
    public List<Student> honorsWithGpaAbove(List<Student> students, double threshold) {
        return students.parallelStream()
                .filter(s -> "Honors".equalsIgnoreCase(s.getType()))
                .filter(s -> s.getGradeHistory().stream()
                        .mapToInt(Grade::getScore)
                        .average().orElse(0.0) / 25.0 > threshold) // simple GPA proxy: score/25
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    // Average per subject using streams
    public Map<String, Double> averagePerSubject(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getGradeHistory().stream())
                .collect(Collectors.groupingBy(
                        Grade::getCourseCode,
                        Collectors.averagingInt(Grade::getScore)
                ));
    }

    // Unique course codes
    public Set<String> uniqueCourseCodes(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getGradeHistory().stream())
                .map(Grade::getCourseCode)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    // Top N students by average grade
    public List<Student> topNByAverage(List<Student> students, int n) {
        return students.stream()
                .sorted(Comparator.comparingDouble(s ->
                        -s.getGradeHistory().stream().mapToInt(Grade::getScore).average().orElse(0.0)))
                .limit(n)
                .collect(Collectors.toList());
    }

    // Group students by grade range (A/B/C/D/F)
    public Map<String, List<Student>> groupByGradeRange(List<Student> students) {
        return students.stream().collect(Collectors.groupingBy(s -> {
            double avg = s.getGradeHistory().stream().mapToInt(Grade::getScore).average().orElse(0.0);
            if (avg >= 90) return "A (90-100)";
            if (avg >= 80) return "B (80-89)";
            if (avg >= 70) return "C (70-79)";
            if (avg >= 60) return "D (60-69)";
            return "F (0-59)";
        }));
    }

    // Search helpers: anyMatch, allMatch, noneMatch, findFirst, findAny
    public boolean anyHonors(List<Student> students) {
        return students.stream().anyMatch(s -> "Honors".equalsIgnoreCase(s.getType()));
    }

    public boolean allHaveEmail(List<Student> students) {
        return students.stream().allMatch(s -> s.getEmail() != null && !s.getEmail().isBlank());
    }

    public boolean noneWithInvalidId(List<Student> students) {
        return students.stream().noneMatch(s -> s.getId() == null || s.getId().isBlank());
    }

    public Optional<Student> findFirstWithCourse(List<Student> students, String courseCode) {
        return students.stream()
                .filter(s -> s.getGradeHistory().stream().anyMatch(g -> courseCode.equalsIgnoreCase(g.getCourseCode())))
                .findFirst();
    }

    public Optional<Student> findAnyHonors(List<Student> students) {
        return students.parallelStream().filter(s -> "Honors".equalsIgnoreCase(s.getType())).findAny();
    }

    // CSV processing via Files.lines() (line-by-line streaming)
    public long processCsvStreaming(Path path) throws Exception {
        try (var lines = Files.lines(path)) {
            return lines
                    .filter(l -> !l.isBlank())
                    .map(String::trim)
                    .count();
        }
    }

    // Sequential vs parallel performance comparison (returns timings in ms)
    public Map<String, Long> compareSequentialVsParallel(List<Student> students, Predicate<Student> predicate) {
        long startSeq = System.nanoTime();
        long countSeq = students.stream().filter(predicate).count();
        long endSeq = System.nanoTime();

        long startPar = System.nanoTime();
        long countPar = students.parallelStream().filter(predicate).count();
        long endPar = System.nanoTime();

        Map<String, Long> result = new LinkedHashMap<>();
        result.put("sequentialCount", countSeq);
        result.put("sequentialTimeMs", (endSeq - startSeq) / 1_000_000);
        result.put("parallelCount", countPar);
        result.put("parallelTimeMs", (endPar - startPar) / 1_000_000);
        return result;
    }

    // Chained operations: filter -> map -> sort -> collect (emails of honors students)
    public List<String> honorsEmailsSorted(List<Student> students) {
        return students.stream()
                .filter(s -> "Honors".equalsIgnoreCase(s.getType()))
                .map(Student::getEmail)
                .filter(Objects::nonNull)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }
}
