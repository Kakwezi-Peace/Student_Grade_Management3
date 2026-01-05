package concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConcurrencyTest {

    @Test
    public void testFixedThreadPoolExecution() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<String> task = () -> {
            Thread.sleep(200);
            return "Task Completed";
        };

        Future<String> future = executor.submit(task);
        assertEquals("Task Completed", future.get());

        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
    }

    @Test
    public void testScheduledTaskExecution() throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable task = () -> System.out.println("Scheduled Task Executed");
        scheduler.schedule(task, 1, TimeUnit.SECONDS);

        scheduler.shutdown();
        assertTrue(scheduler.awaitTermination(2, TimeUnit.SECONDS));
    }
}
