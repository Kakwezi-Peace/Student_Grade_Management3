package utils;

import java.util.List;
import java.util.regex.Pattern;

public final class ValidationUtils {
    private ValidationUtils() {}

    // Precompiled regex patterns
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("^STU\\d{3}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}|\\+1-\\d{3}-\\d{3}-\\d{4}|\\d{10})$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+(['\\-\\s][a-zA-Z]+)*$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static ValidationResult validateStudentId(String id) {
        if (id == null || !STUDENT_ID_PATTERN.matcher(id).matches()) {
            return ValidationResult.error(id, "STU followed by 3 digits", "Invalid Student ID", List.of("STU001", "STU999"));
        }
        return ValidationResult.ok("Valid Student ID");
    }

    public static ValidationResult validateName(String name) {
        if (name == null || !NAME_PATTERN.matcher(name).matches()) {
            return ValidationResult.error(name, "Letters, spaces, hyphens, apostrophes", "Invalid Name", List.of("Emma Wilson", "Jean-Pierre", "O'Connor"));
        }
        return ValidationResult.ok("Valid Name");
    }

    public static ValidationResult validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return ValidationResult.error(email, "user@example.com", "Invalid Email", List.of("peace@university.edu", "emma.w@college.org"));
        }
        return ValidationResult.ok("Valid Email");
    }

    public static ValidationResult validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            return ValidationResult.error(phone, "Phone formats", "Invalid Phone", List.of("(123) 456-7890", "123-456-7890", "+1-123-456-7890", "1234567890"));
        }
        return ValidationResult.ok("Valid Phone");
    }

    public static ValidationResult validateDate(String date) {
        if (date == null || !DATE_PATTERN.matcher(date).matches()) {
            return ValidationResult.error(date, "YYYY-MM-DD", "Invalid Date", List.of("2024-11-03", "2025-01-01"));
        }
        return ValidationResult.ok("Valid Date");
    }
}
