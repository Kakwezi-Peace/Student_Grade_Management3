package utils;

import java.util.List;

public final class RegexExamples {
    private RegexExamples() {}
    public static List<String> studentId() { return List.of("STU001", "STU042", "STU999"); }
    public static List<String> name() { return List.of("Annet peggy", "bibi"); }
    public static List<String> email() { return List.of("peace@gmail.com", "kakwezi@gmail.com"); }
    public static List<String> phone() { return List.of("0789338293", "0783672723", "0783495748", "0784738293"); }
    public static List<String> date() { return List.of("2024-11-03"); }
    public static List<String> courseCode() { return List.of("MAT101", "ENG203"); }
    public static List<String> grade() { return List.of("0", "75", "100"); }
}
