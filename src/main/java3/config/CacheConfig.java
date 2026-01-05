package config;

public final class CacheConfig {
    private CacheConfig() {}

    // Max entries
    public static final int MAX_SIZE = 150;

    // Default TTL for entries (e.g., 5 minutes)
    public static final long DEFAULT_TTL_MS = 5 * 60 * 1000;

    // Background refresh interval (e.g., every 30 seconds)
    public static final long REFRESH_INTERVAL_MS = 30 * 1000;
}
