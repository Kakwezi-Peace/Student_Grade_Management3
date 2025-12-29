package service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheService<K, V> {
    private final int maxSize;
    private final Map<K, CacheEntry<V>> lru;

    public CacheService(int maxSize) {
        this.maxSize = maxSize;
        this.lru = new LinkedHashMap<>(16, 0.75f, true) {
            @Override protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                return size() > CacheService.this.maxSize;
            }
        };
    }
// synchronization
    public synchronized void put(K key, V value) {
        lru.put(key, new CacheEntry<>(value, Instant.now()));
    }

    public synchronized V get(K key) {
        CacheEntry<V> e = lru.get(key);
        return e == null ? null : e.value();
    }

    public synchronized void clear() { lru.clear(); }
    public synchronized int size() { return lru.size(); }

    public record CacheEntry<V>(V value, Instant lastAccess) {}
}
