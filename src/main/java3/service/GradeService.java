package service;

import collections.CollectionsRegistry;
import model.Grade;
import model.Student;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GradeService {
    private final CollectionsRegistry registry;
    private final ThreadSafeCache cache;  // ✅ added cache

    public GradeService(CollectionsRegistry registry, ThreadSafeCache cache) {
        this.registry = registry;
        this.cache = cache;
    }

    public void recordGrade(Student s, String courseCode, int score) {
        Grade g = new Grade(courseCode, score, LocalDate.now());
        s.addGrade(g);

        // Track unique courses
        registry.uniqueCourses().add(courseCode);

        // Subject-wise grade list
        registry.subjectGrades()
                .computeIfAbsent(courseCode, k -> new ArrayList<>())
                .add(g);

        // Grade history per student (LinkedList for frequent insertions/deletions)
        registry.gradeHistory()
                .computeIfAbsent(s.getId(), k -> new LinkedList<>())
                .add(g);

        // ✅ Invalidate dependent caches
        cache.invalidate("report:" + s.getId());
        cache.invalidate("stats:global");
    }

    public List<Grade> getGradesForStudent(Student s) {
        // Return a defensive copy to avoid external mutation
        return new ArrayList<>(s.getGradeHistory());
    }

    // Example: compute and cache a grade report
    public GradeReport getReportForStudent(String id) {
        GradeReport cached = cache.get("report:" + id);
        if (cached != null) return cached;

        Student s = registry.studentMap().get(id);
        if (s == null) return null;

        List<Grade> grades = s.getGradeHistory();
        double avg = grades.stream().mapToInt(Grade::getScore).average().orElse(0.0);
        Map<String, Double> perSubject = grades.stream()
                .collect(Collectors.groupingBy(Grade::getCourseCode,
                        Collectors.averagingInt(Grade::getScore)));

        GradeReport report = new GradeReport(s.getId(), s.getName(), avg, perSubject);
        cache.put("report:" + id, report, 5 * 60 * 1000);
        return report;
    }

    // Stream helpers for US-10
    public double averageForCourse(String courseCode) {
        return registry.subjectGrades().getOrDefault(courseCode, Collections.emptyList())
                .stream()
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0.0);
    }

    public Map<String, Double> averagePerSubject() {
        return registry.subjectGrades().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().mapToInt(Grade::getScore).average().orElse(0.0)
                ));
    }

    public Set<String> uniqueCourseCodes() {
        return new TreeSet<>(registry.uniqueCourses());
    }

    public List<StudentAverage> topStudentsByAverage(List<Student> students, int limit) {
        return students.stream()
                .map(s -> new StudentAverage(s, s.getGradeHistory().stream()
                        .mapToInt(Grade::getScore)
                        .average().orElse(0.0)))
                .sorted(Comparator.comparingDouble(StudentAverage::average).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

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

    // Helper record for top students
    public static record StudentAverage(Student student, double average) {}

    // ✅ Simple DTO for reports
    public static class GradeReport {
        private final String studentId;
        private final String studentName;
        private final double average;
        private final Map<String, Double> subjectAverages;

        public GradeReport(String studentId, String studentName, double average, Map<String, Double> subjectAverages) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.average = average;
            this.subjectAverages = subjectAverages;
        }

        public String getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public double getAverage() { return average; }
        public Map<String, Double> getSubjectAverages() { return subjectAverages; }
    }
}
