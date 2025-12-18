package concurrent;

import java.util.concurrent.*;

public class SchedulerService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public void scheduleTask(Runnable task, long delay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, delay, period, unit);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
