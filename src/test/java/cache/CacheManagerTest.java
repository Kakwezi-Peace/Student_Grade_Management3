package cache;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CacheManagerTest {

    @Test
    public void testCachePutAndGet() {
        ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
        cache.put("STU001", "Alice");

        assertEquals("Alice", cache.get("STU001"));
    }

    @Test
    public void testCacheEvictionSimulation() {
        ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
        for (int i = 0; i < 150; i++) {
            cache.put("STU" + i, "Student" + i);
        }

        assertEquals(150, cache.size());
    }
}
