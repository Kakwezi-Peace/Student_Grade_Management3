package utils;

import java.util.regex.Pattern;

public class ValidationUtils {
//This is where Regex is Implemented

    // Student ID → ST followed by 3 digits (ST301)

    private static final Pattern STUDENT_ID_PATTERN =
            Pattern.compile("^ST\\d{3}$");


    // Name → letters only, min 2 chars

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z]{2,}$");


    // Email → basic email validation

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    // Phone → Rwanda format (07XXXXXXXX)

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^07\\d{8}$");


    // Course Code → 3 letters + 3 digits (CSC101)

    private static final Pattern COURSE_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}\\d{3}$");


    // Enrollment Date → YYYY-MM-DD

    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");


    // Grade → 0–100

    public static boolean isValidGrade(int grade) {
        return grade >= 0 && grade <= 100;
    }

    // ===== STATIC VALIDATION METHODS =====

    public static boolean isValidStudentId(String studentId) {
        return STUDENT_ID_PATTERN.matcher(studentId).matches();
    }

    public static boolean isValidName(String name) {
        return !NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidCourseCode(String courseCode) {
        return COURSE_CODE_PATTERN.matcher(courseCode).matches();
    }

    public static boolean isValidDate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
