package collections;

import model.Grade;
import model.Statistics;
import model.Student;

import java.util.*;

public class CollectionsRegistry {
    // Primary store: O(1) lookup by ID
    private final Map<String, Student> studentMap = new HashMap<>();

    // Sorted GPA rankings: descending (use comparator)
    private final NavigableMap<Double, List<Student>> gpaRankings =
            new TreeMap<>(Comparator.reverseOrder());

    // Maintain insertion order for display
    private final List<Student> studentList = new ArrayList<>();

    // Unique courses across system
    private final Set<String> uniqueCourses = new HashSet<>();

    // Subject grades per subject (sorted by subject name)
    private final NavigableMap<String, List<Grade>> subjectGrades = new TreeMap<>();

    // Thread-safe statistics cache
    private final Map<String, Statistics> statsCache =
            new java.util.concurrent.ConcurrentHashMap<>();

    // ✅ Grade history per student (ID → list of Grade objects)
    // Using LinkedList for frequent insertions/deletions
    private final Map<String, List<Grade>> gradeHistory = new HashMap<>();

    // --- Getters ---
    public Map<String, Student> studentMap() { return studentMap; }
    public NavigableMap<Double, List<Student>> gpaRankings() { return gpaRankings; }
    public List<Student> studentList() { return studentList; }
    public Set<String> uniqueCourses() { return uniqueCourses; }
    public NavigableMap<String, List<Grade>> subjectGrades() { return subjectGrades; }
    public Map<String, Statistics> statsCache() { return statsCache; }

    // ✅ Getter for grade history
    public Map<String, List<Grade>> gradeHistory() { return gradeHistory; }
}
