package service;

import model.Grade;
import model.Statistics;
import model.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {
    public Statistics calculate(List<Student> students) {
        List<Integer> allScores = students.stream()
                .flatMap(s -> s.getGradeHistory().stream())
                .map(Grade::getScore).sorted().toList();

        double mean = allScores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        double median = allScores.isEmpty() ? 0.0 :
                (allScores.size() % 2 == 1
                        ? allScores.get(allScores.size() / 2)
                        : (allScores.get(allScores.size() / 2 - 1) + allScores.get(allScores.size() / 2)) / 2.0);

        double stdDev = Math.sqrt(allScores.stream().mapToDouble(s -> Math.pow(s - mean, 2)).average().orElse(0.0));

        Map<String, Long> buckets = allScores.stream().collect(Collectors.groupingBy(this::bucket, Collectors.counting()));
        return new Statistics(mean, median, stdDev, buckets);
    }

    private String bucket(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}
