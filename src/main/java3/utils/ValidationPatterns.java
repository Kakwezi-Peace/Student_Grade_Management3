package utils;

import java.util.regex.Pattern;

public final class ValidationPatterns {
    private ValidationPatterns() {}

    public static final Pattern STUDENT_ID = Pattern.compile("STU\\d{3}");
    public static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public static final Pattern PHONE_PAREN = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}");
    public static final Pattern PHONE_DASH  = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
    public static final Pattern PHONE_INTL  = Pattern.compile("\\+1-\\d{3}-\\d{3}-\\d{4}");
    public static final Pattern PHONE_FLAT  = Pattern.compile("\\d{10}");

    public static final Pattern NAME = Pattern.compile("^[a-zA-Z]+(['\\s-][a-zA-Z]+)*$");
    public static final Pattern DATE = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    public static final Pattern COURSE_CODE = Pattern.compile("[A-Z]{3}\\d{3}");
    public static final Pattern GRADE = Pattern.compile("(100|[1-9]?\\d)");
}
