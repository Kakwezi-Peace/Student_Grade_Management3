package service;

import model.Grade;
import model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;



public class StreamService {

    public List<Student> honorsWithGpaAbove(List<Student> students, double threshold) {
        return students.parallelStream()
                .filter(s -> "Honors".equalsIgnoreCase(s.getType()))
                .filter(s -> s.getGradeHistory().stream().mapToInt(Grade::getScore).average().orElse(0.0) / 25.0 > threshold)
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    public Map<String, Double> averagePerSubject(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getGradeHistory().stream())
                .collect(Collectors.groupingBy(Grade::getCourseCode, Collectors.averagingInt(Grade::getScore)));
    }

    public Set<String> uniqueCourseCodes(List<Student> students) {
        return students.stream().flatMap(s -> s.getGradeHistory().stream()).map(Grade::getCourseCode).collect(Collectors.toSet());
    }

    public long processCsvStreaming(Path path) throws Exception {
        try (var lines = Files.lines(path)) {
            return lines
                    .filter(l -> !l.isBlank())
                    .map(String::trim)
                    .count();
        }
    }
}
