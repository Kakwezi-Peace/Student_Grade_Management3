package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

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
        return Executors.newScheduledThreadPool(threads);
    }

    public static ThreadPoolExecutor fixedPool(int i) {

        return null;
    }
}
