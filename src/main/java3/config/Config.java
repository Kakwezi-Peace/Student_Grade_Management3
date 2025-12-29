package config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class Config {
    private Config() {}

    public static final Charset UTF8 = StandardCharsets.UTF_8;
// Data Json,csv,Binary
    public static final String DATA_CSV    = "./data/csv";
    public static final String DATA_JSON   = "./data/json";
    public static final String DATA_BINARY = "./data/binary";
    public static final String IMPORTS_DIR = "./imports";
    public static final String CACHE_DIR   = "./cache";

    public static final String REPORTS_CSV     = "./reports/csv";
    public static final String REPORTS_JSON    = "./reports/json";
    public static final String REPORTS_BINARY  = "./reports/binary";
    public static final String AUDIT_DIR       = "./reports/audit";

    public static final long AUDIT_ROTATE_BYTES = 10L * 1024L * 1024L;
}
