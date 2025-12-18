package search;

import model.Student;
import java.util.List;

public record SearchResult(List<Student> matches, long scanned, long timeMs) {
    // Extra constructor that takes int size instead of long scanned
    public SearchResult(List<Student> matches, int size, long timeMs) {
        this(matches, (long) size, timeMs);  // ✅ delegate to canonical constructor
    }
}
