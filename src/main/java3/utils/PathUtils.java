package utils;

import config.Config;

import java.io.IOException;
import java.nio.file.*;

public final class PathUtils {
    private PathUtils() {}

    public static Path ensureDir(String dir) {
        Path p = Path.of(dir);
        try { Files.createDirectories(p); } catch (IOException ignored) {}
        return p;
    }

    public static long size(Path p) throws IOException { return Files.size(p); }
    public static boolean readable(Path p) { return Files.isReadable(p); }
    public static boolean writable(Path p) { return Files.isWritable(p); }

    public static void initAll() {
        ensureDir(Config.DATA_CSV);
        ensureDir(Config.DATA_JSON);
        ensureDir(Config.DATA_BINARY);
        ensureDir(Config.IMPORTS_DIR);
        ensureDir(Config.REPORTS_CSV);
        ensureDir(Config.REPORTS_JSON);
        ensureDir(Config.REPORTS_BINARY);
        ensureDir(Config.AUDIT_DIR);
        ensureDir(Config.CACHE_DIR);
    }

    // Support relative or absolute paths
    public static Path resolve(Path baseDir, String userInput) {
        Path candidate = Path.of(userInput);
        return candidate.isAbsolute() ? candidate : baseDir.resolve(candidate).normalize();
    }
}
