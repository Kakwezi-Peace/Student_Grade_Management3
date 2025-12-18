package utils;

import model.Report;

public final class JsonUtils {
    private JsonUtils() {}

    public static String reportToJson(Report report) {
        return "{\n" +
                "  \"studentId\": \"" + report.getStudent().getId() + "\",\n" +
                "  \"gpa\": " + String.format("%.2f", report.getGpa()) + ",\n" +
                "  \"gradeCount\": " + report.getGrades().size() + "\n" +
                "}\n";
    }
}
