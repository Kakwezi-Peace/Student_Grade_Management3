package service;

import config.Config;
import concurrent.ExecutorsConfig;
import model.AuditEntry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Handles audit logging asynchronously.
 */
public class AuditService {
    private final ConcurrentLinkedQueue<AuditEntry> queue = new ConcurrentLinkedQueue<>();
    private final ExecutorService writer = ExecutorsConfig.singleWriter();
    private final AuditStats stats = new AuditStats();

    /**
     * Logs an audit entry asynchronously.
     *
     * @param entry the audit entry to log
     * @return a CompletableFuture that completes when the log is written
     */
    public CompletableFuture<Void> logAsync(AuditEntry entry) {
        queue.add(Objects.requireNonNull(entry));
        stats.record(entry.getOperation(), entry.getExecutionMs());
        // ✅ Return a CompletableFuture instead of void
        return CompletableFuture.runAsync(this::drain, writer);
    }

    private void drain() {
        Path dir = Path.of(Config.AUDIT_DIR);
        try { Files.createDirectories(dir); } catch (IOException ignored) {}

        Path file = dir.resolve("audit-" + LocalDate.now() + ".log");

        try {
            if (Files.exists(file) && Files.size(file) > Config.AUDIT_ROTATE_BYTES) {
                file = dir.resolve("audit-" + LocalDate.now() + "-" + System.currentTimeMillis() + ".log");
            }
        } catch (IOException ignored) {}

        try (BufferedWriter bw = Files.newBufferedWriter(file, Config.UTF8, CREATE, APPEND)) {
            AuditEntry e;
            while ((e = queue.poll()) != null) {
                String line = String.format("%s | %s | %s | %s | %dms | %s%n",
                        DateTimeFormatter.ISO_INSTANT.format(e.getTimestamp()),
                        e.getThreadId(),
                        e.getOperation(),
                        e.getUserAction(),
                        e.getExecutionMs(),
                        e.isSuccess() ? "SUCCESS" : "FAIL");
                bw.write(line);
            }
        } catch (IOException ex) {
            System.err.println("[Audit] Write error: " + ex.getMessage());
        }
    }

    public AuditStats getStats() { return stats; }

    public void shutdown() { writer.shutdown(); }
}
