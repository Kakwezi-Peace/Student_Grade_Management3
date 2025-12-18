package service;

import collections.CollectionsRegistry;
import model.Grade;
import model.Student;

import java.time.LocalDate;
import java.util.List;

public class GradeService {
    private final CollectionsRegistry registry;

    public GradeService(CollectionsRegistry registry) { this.registry = registry; }

    public void recordGrade(Student s, String courseCode, int score) {
        s.addGrade(new Grade(courseCode, score, LocalDate.now()));
        registry.uniqueCourses().add(courseCode);
        registry.subjectGrades().computeIfAbsent(courseCode, k -> new java.util.ArrayList<>()).add(new Grade(courseCode, score, LocalDate.now()));
    }

    public List<Grade> getGradesForStudent(Student s) {

        return List.of();
    }
}
