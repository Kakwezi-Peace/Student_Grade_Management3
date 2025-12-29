package utils;

import java.util.List;

public class ValidationResult {
    private final boolean valid;
    private final String message;

    public ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() { return valid; }
    public String getMessage() { return message; }

    public static ValidationResult ok(String message) {
        return new ValidationResult(true, message);
    }

    public static ValidationResult error(String input, String expected, String message, List<String> examples) {
        StringBuilder sb = new StringBuilder();
        sb.append("❌ ").append(message).append("\n");
        sb.append("Input: ").append(input).append("\n");
        sb.append("Expected: ").append(expected).append("\n");
        sb.append("Examples: ").append(String.join(", ", examples));
        return new ValidationResult(false, sb.toString());
    }

    @Override
    public String toString() {
        return valid ? "✓ " + message : message;
    }
}
