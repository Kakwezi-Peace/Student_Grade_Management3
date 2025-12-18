package service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


 // US-4: Automated Scheduler

public class SchedulerService {

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private final int intervalSeconds = 10;
    private int countdown = intervalSeconds;

    public SchedulerService(StudentRepository repository, StudentProcessor processor, StudentReportService reportService) {

    }


    // Start automated task

    public void start() {

        // Countdown display
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Next automated report in: " + countdown + " seconds");
            countdown--;

            if (countdown < 0) {
                countdown = intervalSeconds;
            }
        }, 0, 1, TimeUnit.SECONDS);

        // Scheduled task
        scheduler.scheduleAtFixedRate(() -> {
            runTask();
        }, intervalSeconds, intervalSeconds, TimeUnit.SECONDS);
    }


     // Task logic

    private void runTask() {
        System.out.println("\n>>> Automated Report Task Started");

        // Simulated report generation
        System.out.println("Generating student grade report...");

        // Simulated email notification
        System.out.println("Email sent: Report generated successfully");

        System.out.println(">>> Automated Task Completed\n");
    }


     // Proper shutdown

    public void shutdown() {
        System.out.println("Scheduler shutting down safely...");
        scheduler.shutdown();
    }
}
