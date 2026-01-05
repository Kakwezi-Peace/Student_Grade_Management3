package config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Central configuration class for the Student Grade Management System.
 */
public final class Config {
    private Config() {}

    // Character encoding
    public static final Charset UTF8 = StandardCharsets.UTF_8;

    // Data directories
    public static final String DATA_CSV    = "./data/csv";
    public static final String DATA_JSON   = "./data/json";
    public static final String DATA_BINARY = "./data/binary";
    public static final String IMPORTS_DIR = "./imports";
    public static final String CACHE_DIR   = "./cache";

    // Reports directories
    public static final String REPORTS_CSV     = "./reports/csv";
    public static final String REPORTS_JSON    = "./reports/json";
    public static final String REPORTS_BINARY  = "./reports/binary";

    // Audit directory (US-9)
    public static final String AUDIT_DIR       = "./reports/audit";

    // Rotate audit file when size exceeds 10MB
    public static final long AUDIT_ROTATE_BYTES = 10L * 1024L * 1024L;

    // ✅ Added constants expected by tests
    public static final String DEFAULT_FILE_PATH = "./data/students.csv";
    public static final int MAX_STUDENTS = 1000;

    // ✅ Allow overriding file path if needed
    private static String filePath = DEFAULT_FILE_PATH;

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String newPath) {
        filePath = newPath;
    }
}
