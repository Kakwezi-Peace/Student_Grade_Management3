package concurrent;

import model.Student;
import service.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BatchReportGenerator {
    private final ThreadPoolExecutor executor;

    public BatchReportGenerator(int threads) {
        this.executor = ExecutorsConfig.fixedPool(threads);
    }
// generate reports methods
    public void generateReports(List<Student> students, ReportService reportService) {
        long start = System.currentTimeMillis();
        List<Future<String>> futures = new ArrayList<>();

        for (Student s : students) {
            futures.add(executor.submit(new ReportTask(s, reportService)));
        }

        int completed = 0;
        for (Future<String> future : futures) {
            try {
                String msg = future.get();
                completed++;
                System.out.println(msg);
                System.out.printf("Progress: %.0f%% (%d/%d completed)%n", (completed * 100.0 / students.size()), completed, students.size());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error generating report: " + e.getMessage());
            }
        }

        long elapsed = System.currentTimeMillis() - start;
        double avgMs = (double) elapsed / Math.max(1, students.size());
        double throughput = students.size() / (elapsed / 1000.0);

        System.out.println("\n✓ BATCH GENERATION COMPLETED!");
        System.out.println("\nEXECUTION SUMMARY");
        System.out.println("-----------------");
        System.out.printf("Total Reports: %d%n", students.size());
        System.out.printf("Successful: %d%n", completed);
        System.out.printf("Total Time: %.1f seconds%n", elapsed / 1000.0);
        System.out.printf("Avg Time per Report: %.0fms%n", avgMs);

        long sequential = (long) (avgMs * students.size());
        System.out.printf("%nSequential Processing (estimated): %.1f seconds%n", sequential / 1000.0);
        System.out.printf("Concurrent Processing (actual): %.1f seconds%n", elapsed / 1000.0);
        System.out.printf("Performance Gain: %.1fx faster%n", sequential / (double) Math.max(1, elapsed));
        System.out.printf("%nThread Pool Statistics:%n");
        System.out.printf("  Peak Thread Count: %d%n", executor.getLargestPoolSize());
        System.out.printf("  Total Tasks Executed: %d%n", executor.getCompletedTaskCount());
        System.out.printf("  Active Threads: %d%n", executor.getActiveCount());
        System.out.printf("  Queue Size: %d%n", executor.getQueue().size());
        System.out.printf("  Throughput: %.2f reports/sec%n", throughput);

        shutdown();
    }

    private void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
