package service;



import concurrent.ExecutorsConfig;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerService {
    private final ScheduledExecutorService scheduler = ExecutorsConfig.scheduledPool(3);

    public void scheduleDailyGpaRecalc(Runnable task, int hour, int minute) {
        long initialDelay = computeInitialDelay(hour, minute);
        long period = TimeUnit.DAYS.toSeconds(1);
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    public void scheduleHourlyStatsCacheRefresh(Runnable task) {
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }

    public void scheduleWeeklyReports(Runnable task) {
        long oneWeek = TimeUnit.DAYS.toSeconds(7);
        scheduler.scheduleAtFixedRate(task, oneWeek, oneWeek, TimeUnit.SECONDS);
    }
// shutdown
    public void shutdown() { scheduler.shutdown(); }

    private long computeInitialDelay(int hour, int minute) {
        // simple placeholder: start in 60 seconds
        return 60;
    }
}
