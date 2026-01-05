package console;

import service.ThreadSafeCache;
import service.CacheStats;

import java.util.Map;
import java.util.Scanner;

public class CacheViewerConsole {
    private final ThreadSafeCache cache;

    public CacheViewerConsole(ThreadSafeCache cache) {
        this.cache = cache;
    }

    public void run(Scanner sc) {
        System.out.println("\n=== CACHE VIEWER ===");

        CacheStats stats = cache.getStats();
        System.out.printf("Total entries: %d%n", cache.size());
        System.out.printf("Hit rate: %.2f%%%n", stats.getHitRate());
        System.out.printf("Miss rate: %.2f%%%n", stats.getMissRate());
        System.out.printf("Avg hit time: %.2f ms%n", stats.getAvgHitTimeMs());
        System.out.printf("Avg miss time: %.2f ms%n", stats.getAvgMissTimeMs());
        System.out.printf("Evictions: %d%n", stats.getEvictions());
        System.out.printf("Approx memory usage: %d bytes%n", cache.approximateMemoryUsageBytes());

        System.out.println("\n--- Contents (last accessed timestamps) ---");
        for (Map.Entry<String, String> e : cache.contentsWithAccessTimestamps().entrySet()) {
            System.out.println(" - " + e.getKey() + " | lastAccessed=" + e.getValue());
        }

        System.out.println("\nOptions:");
        System.out.println("  1. Clear cache");
        System.out.println("  2. Invalidate by prefix");
        System.out.println("  3. Back");

        System.out.print("Enter choice: ");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1" -> {
                cache.clear();
                System.out.println("Cache cleared.");
            }
            case "2" -> {
                System.out.print("Enter prefix: ");
                String prefix = sc.nextLine().trim();
                cache.invalidateByPrefix(prefix);
                System.out.println("Invalidated entries with prefix: " + prefix);
            }
            default -> System.out.println("Returning...");
        }
    }
}
