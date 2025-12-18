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
        return new ValidationResult(false, message + "\nInput: " + input + "\nExpected: " + expected + "\nExamples: " + examples);
    }
}
