package service;

import java.util.concurrent.atomic.AtomicLong;

public class CacheStats {
    private final AtomicLong hits = new AtomicLong();
    private final AtomicLong misses = new AtomicLong();
    private final AtomicLong hitTimeTotalMs = new AtomicLong();
    private final AtomicLong missTimeTotalMs = new AtomicLong();
    private final AtomicLong evictions = new AtomicLong();

    public void recordHit(long timeMs) {
        hits.incrementAndGet();
        hitTimeTotalMs.addAndGet(timeMs);
    }

    public void recordMiss(long timeMs) {
        misses.incrementAndGet();
        missTimeTotalMs.addAndGet(timeMs);
    }

    public void recordEviction() { evictions.incrementAndGet(); }

    public long getHits() { return hits.get(); }
    public long getMisses() { return misses.get(); }
    public long getEvictions() { return evictions.get(); }

    public double getHitRate() {
        long h = hits.get(), m = misses.get();
        long total = h + m;
        return total == 0 ? 0.0 : (double) h * 100.0 / total;
    }

    public double getMissRate() {
        long h = hits.get(), m = misses.get();
        long total = h + m;
        return total == 0 ? 0.0 : (double) m * 100.0 / total;
    }

    public double getAvgHitTimeMs() {
        long h = hits.get();
        return h == 0 ? 0.0 : (double) hitTimeTotalMs.get() / h;
    }

    public double getAvgMissTimeMs() {
        long m = misses.get();
        return m == 0 ? 0.0 : (double) missTimeTotalMs.get() / m;
    }
}
