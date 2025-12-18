package collections;

import model.Student;

import java.util.Comparator;

public final class Comparators {
    private Comparators() {}

    public static Comparator<Student> byNameAsc() {
        return Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Student> byIdAsc() {
        return Comparator.comparing(Student::getId);
    }
}
