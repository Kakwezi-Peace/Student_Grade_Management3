package service;

import concurrent.ExecutorsConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerService {
    private final ScheduledExecutorService scheduler = ExecutorsConfig.scheduledPool(3);

    // Already working
    public void scheduleDailyGpaRecalc(Runnable task, int hour, int minute) {
        long initialDelay = computeInitialDelay(hour, minute);
        long period = TimeUnit.DAYS.toSeconds(1);
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    // Already working
    public void scheduleHourlyStatsCacheRefresh(Runnable task) {
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }

    // Already working
    public void scheduleWeeklyReports(Runnable task) {
        long oneWeek = TimeUnit.DAYS.toSeconds(7);
        scheduler.scheduleAtFixedRate(task, oneWeek, oneWeek, TimeUnit.SECONDS);
    }

    // ✅ New: weekly report with day-of-week and hour
    public void scheduleWeeklyReport(Runnable task, int dayOfWeek, int hour) {
        long initialDelay = computeDelayToDayAndHour(dayOfWeek, hour);
        long period = TimeUnit.DAYS.toSeconds(7);
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    // ✅ New: monthly summary with day-of-month and hour
    public void scheduleMonthlySummary(Runnable task, int dayOfMonth, int hour) {
        long initialDelay = computeDelayToDayOfMonth(dayOfMonth, hour);
        long period = TimeUnit.DAYS.toSeconds(30); // simple monthly period
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    // ✅ New: hourly sync
    public void scheduleHourlySync(Runnable task) {
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }

    // ✅ New: custom schedule with interval in minutes
    public void scheduleCustom(Runnable task, int intervalMinutes) {
        scheduler.scheduleAtFixedRate(task, 0, intervalMinutes, TimeUnit.MINUTES);
    }

    public void shutdown() { scheduler.shutdown(); }

    // --- Helpers ---
    private long computeInitialDelay(int hour, int minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.withHour(hour).withMinute(minute).withSecond(0);
        if (next.isBefore(now)) next = next.plusDays(1);
        return ChronoUnit.SECONDS.between(now, next);
    }

    private long computeDelayToDayAndHour(int dayOfWeek, int hour) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.withHour(hour).withMinute(0).withSecond(0);
        while (next.getDayOfWeek().getValue() != dayOfWeek || !next.isAfter(now)) {
            next = next.plusDays(1);
        }
        return ChronoUnit.SECONDS.between(now, next);
    }

    private long computeDelayToDayOfMonth(int dayOfMonth, int hour) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.withDayOfMonth(dayOfMonth).withHour(hour).withMinute(0).withSecond(0);
        if (!next.isAfter(now)) next = next.plusMonths(1);
        return ChronoUnit.SECONDS.between(now, next);
    }
}
