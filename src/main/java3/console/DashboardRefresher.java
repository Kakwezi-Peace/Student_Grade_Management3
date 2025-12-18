package console;



import model.Statistics;
import model.Student;
import service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DashboardRefresher implements Runnable {
    private final List<Student> students;
    private final StatisticsService statsService;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean paused = new AtomicBoolean(false);

    public DashboardRefresher(List<Student> students, StatisticsService statsService) {
        this.students = students;
        this.statsService = statsService;
    }

    @Override
    public void run() {
        while (running.get()) {
            if (!paused.get()) {
                System.out.println("\nREAL-TIME STATISTICS DASHBOARD");
                System.out.println("Auto-refresh: Enabled (5 sec) | Thread: RUNNING");
                System.out.println("Press 'Q' to quit | 'R' to refresh now | 'P' to pause");
                System.out.println("Last Updated: " + LocalDateTime.now());
                System.out.println("Loading ...");

                Statistics s = statsService.calculate(students);
                System.out.printf("Mean: %.1f%% | Median: %.1f%% | Std Dev: %.1f%%%n", s.getMean(), s.getMedian(), s.getStdDev());
                System.out.println("Grade Distribution: " + s.getGradeBuckets());
            }

            try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
        }
    }

    public void stop() { running.set(false); }
    public void pause() { paused.set(true); }
    public void resume() { paused.set(false); }
}
