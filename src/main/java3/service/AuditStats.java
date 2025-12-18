package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AuditStats {
    private final AtomicLong totalOps = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();
    private final ConcurrentHashMap<String, AtomicLong> opsPerType = new ConcurrentHashMap<>();

    public void record(String type, long timeMs) {
        totalOps.incrementAndGet();
        totalTime.addAndGet(timeMs);
        opsPerType.computeIfAbsent(type, k -> new AtomicLong()).incrementAndGet();
    }

    public long getTotalOps() { return totalOps.get(); }
    public double getAvgTimeMs() {
        long ops = totalOps.get();
        return ops == 0 ? 0.0 : totalTime.get() / (double) ops;
    }

    public Map<String, AtomicLong> getOpsPerType() { return opsPerType; }
}
