package console;

import config.Config;
import service.AuditService;
import service.AuditStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class AuditViewerConsole {
    private final AuditService auditService;

    public AuditViewerConsole(AuditService auditService) {
        this.auditService = auditService;
    }

    public void run(Scanner sc) {
        Path dir = Path.of(Config.AUDIT_DIR);
        System.out.println("\n=== AUDIT TRAIL VIEWER ===");
        try {
            System.out.println("Available log files:");
            Files.createDirectories(dir);
            Files.list(dir)
                    .filter(p -> p.toString().endsWith(".log"))
                    .sorted()
                    .forEach(p -> System.out.println(" - " + p.getFileName()));
        } catch (IOException e) {
            System.err.println("Error reading audit directory: " + e.getMessage());
            return;
        }

        System.out.print("\nEnter log file to view (e.g., audit-YYYY-MM-DD.log): ");
        String fileName = sc.nextLine().trim();
        Path file = dir.resolve(fileName);
        if (!Files.exists(file)) {
            System.out.println("File does not exist: " + file.getFileName());
            return;
        }

        System.out.println("\n--- Recent Audit Entries (max 200) ---");
        try {
            Files.lines(file).limit(200).forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        System.out.println("\n--- Audit Statistics (live) ---");
        AuditStats stats = auditService.getStats();
        System.out.printf("Total operations: %d%n", stats.getTotalOps());
        System.out.printf("Average exec time: %.2f ms%n", stats.getAvgTimeMs());
        System.out.println("Operations per type:");
        for (Map.Entry<String, AtomicLong> entry : stats.getOpsPerType().entrySet()) {
            System.out.printf(" - %s: %d%n", entry.getKey(), entry.getValue().get());
        }
    }
}
