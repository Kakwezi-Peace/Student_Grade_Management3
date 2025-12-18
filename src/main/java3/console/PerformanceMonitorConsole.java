package console;

import java.util.concurrent.ThreadPoolExecutor;

public class PerformanceMonitorConsole {
    public void showThreadPool(ThreadPoolExecutor exec) {
        System.out.println("THREAD POOL PERFORMANCE");
        System.out.printf("Active: %d | Max: %d | Queue: %d | Completed: %d%n",
                exec.getActiveCount(), exec.getMaximumPoolSize(), exec.getQueue().size(), exec.getCompletedTaskCount());
    }
}
