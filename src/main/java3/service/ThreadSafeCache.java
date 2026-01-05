package service;

import config.CacheConfig;
import concurrent.ExecutorsConfig;
import model.CacheEntry;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class ThreadSafeCache {
    // Primary store: thread-safe
    private final ConcurrentHashMap<String, CacheEntry<?>> store = new ConcurrentHashMap<>();

    // LRU eviction via access-order LinkedHashMap (synchronized)
    private final LinkedHashMap<String, Boolean> lru = new LinkedHashMap<>(16, 0.75f, true);
    private final Object lruLock = new Object();

    private final CacheStats stats = new CacheStats();
    private final ScheduledExecutorService refresher = ExecutorsConfig.scheduledPool(1);

    public ThreadSafeCache() {
        // Background refresher for stale entries
        refresher.scheduleAtFixedRate(this::refreshStaleEntries,
                CacheConfig.REFRESH_INTERVAL_MS,
                CacheConfig.REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS);
    }

    // Cache warming on startup
    public void warm(Map<String, ?> frequentlyAccessed, long ttlMs) {
        if (frequentlyAccessed == null || frequentlyAccessed.isEmpty()) return;
        frequentlyAccessed.forEach((k, v) -> put(k, v, ttlMs));
    }

    public CacheStats getStats() { return stats; }

    public int size() { return store.size(); }

    public long approximateMemoryUsageBytes() {
        MemoryMXBean mx = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mx.getHeapMemoryUsage();
        return heap.getUsed();
    }

    public void clear() {
        store.clear();
        synchronized (lruLock) { lru.clear(); }
    }

    public void invalidate(String key) {
        store.remove(key);
        synchronized (lruLock) { lru.remove(key); }
    }

    public void invalidateByPrefix(String prefix) {
        if (prefix == null) return;
        for (String key : store.keySet()) {
            if (key.startsWith(prefix)) invalidate(key);
        }
    }

    public <V> void put(String key, V value, long ttlMs) {
        long start = System.nanoTime();
        CacheEntry<V> entry = new CacheEntry<>(value, ttlMs <= 0 ? CacheConfig.DEFAULT_TTL_MS : ttlMs);
        store.put(key, entry);
        synchronized (lruLock) {
            lru.put(key, Boolean.TRUE);
            enforceMaxSize();
        }
        long end = System.nanoTime();
        // Treat put as miss (population)
        stats.recordMiss(TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        long start = System.nanoTime();
        CacheEntry<?> entry = store.get(key);
        if (entry == null) {
            long end = System.nanoTime();
            stats.recordMiss(TimeUnit.NANOSECONDS.toMillis(end - start));
            return null;
        }
        entry.touch();
        synchronized (lruLock) {
            // Touch LRU
            lru.get(key); // access-order moves it to tail
        }
        long end = System.nanoTime();
        stats.recordHit(TimeUnit.NANOSECONDS.toMillis(end - start));
        return (V) entry.getValue();
    }

    public Map<String, String> contentsWithAccessTimestamps() {
        Map<String, String> out = new TreeMap<>();
        for (Map.Entry<String, CacheEntry<?>> e : store.entrySet()) {
            Instant ts = e.getValue().getLastAccessed();
            out.put(e.getKey(), ts.toString());
        }
        return out;
    }

    private void enforceMaxSize() {
        while (lru.size() > CacheConfig.MAX_SIZE) {
            Iterator<Map.Entry<String, Boolean>> it = lru.entrySet().iterator();
            if (!it.hasNext()) break;
            Map.Entry<String, Boolean> eldest = it.next();
            String evictKey = eldest.getKey();
            it.remove();
            store.remove(evictKey);
            stats.recordEviction();
        }
    }

    private void refreshStaleEntries() {
        for (Map.Entry<String, CacheEntry<?>> e : store.entrySet()) {
            CacheEntry<?> ce = e.getValue();
            if (ce.isStale()) {
                // Simple strategy: invalidate stale entries; caller can repopulate on next get
                invalidate(e.getKey());
            }
        }
    }

    public void shutdown() {
        refresher.shutdown();
    }
}
