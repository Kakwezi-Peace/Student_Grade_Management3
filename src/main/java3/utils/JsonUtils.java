package utils;

import model.Student;

import java.time.LocalDate;
import java.util.List;

public final class JsonUtils {
    private JsonUtils() {}

    // Minimal JSON serialization (no external libs) for a Report-like student snapshot
    public static String studentsToJson(List<Student> students) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": ").append(str(s.getId())).append(",\n");
            sb.append("    \"name\": ").append(str(s.getName())).append(",\n");
            sb.append("    \"email\": ").append(str(s.getEmail())).append(",\n");
            sb.append("    \"phone\": ").append(str(s.getPhone())).append(",\n");
            sb.append("    \"type\": ").append(str(s.getType())).append(",\n");
            sb.append("    \"enrollmentDate\": ").append(str(s.getEnrollmentDate() != null ? s.getEnrollmentDate().toString() : null)).append("\n");
            sb.append("  }").append(i < students.size() - 1 ? "," : "").append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    public static String reportToJson(model.Report report) {
        // If you already have this method, keep your existing implementation.
        // Minimal example:
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"studentId\": ").append(str(report.getStudent().getId())).append(",\n");
        sb.append("  \"gpa\": ").append(report.getGpa()).append(",\n");
        sb.append("  \"gradeCount\": ").append(report.getGrades().size()).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String str(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
