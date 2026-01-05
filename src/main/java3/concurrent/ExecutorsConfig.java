package concurrent;

import java.util.concurrent.*;

public final class ExecutorsConfig {
    private ExecutorsConfig() {}

    public static ExecutorService singleWriter() {
        return Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "audit-writer");
            t.setDaemon(true);
            return t;
        });
    }

    public static ScheduledExecutorService scheduledPool(int threads) {
        return Executors.newScheduledThreadPool(threads, r -> {
            Thread t = new Thread(r, "scheduled-" + threads);
            t.setDaemon(true);
            return t;
        });
    }

    public static ThreadPoolExecutor fixedPool(int size) {
        return new ThreadPoolExecutor(
                size,
                size,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                r -> {
                    Thread t = new Thread(r, "fixed-pool");
                    t.setDaemon(true);
                    return t;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
