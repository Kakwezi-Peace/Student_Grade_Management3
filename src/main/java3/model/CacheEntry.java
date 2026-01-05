package model;

import java.time.Instant;
import java.util.Objects;

public final class CacheEntry<V> {
    private final V value;
    private final Instant createdAt;
    private volatile Instant lastAccessed;
    private final long ttlMs; // time-to-live in milliseconds

    public CacheEntry(V value, long ttlMs) {
        this.value = Objects.requireNonNull(value);
        this.ttlMs = ttlMs;
        this.createdAt = Instant.now();
        this.lastAccessed = this.createdAt;
    }

    public V getValue() { return value; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getLastAccessed() { return lastAccessed; }
    public long getTtlMs() { return ttlMs; }

    public void touch() { this.lastAccessed = Instant.now(); }

    public boolean isStale() {
        long age = Instant.now().toEpochMilli() - createdAt.toEpochMilli();
        return ttlMs > 0 && age > ttlMs;
    }
}
