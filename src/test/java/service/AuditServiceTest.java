package service;

import model.AuditEntry;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class AuditServiceTest {

    @Test
    public void testLogAsync() {
        AuditService auditService = new AuditService();

        AuditEntry entry = new AuditEntry(
                Instant.now(),
                Thread.currentThread().getName(),
                "TEST_ACTION",
                "Testing audit log",
                100,
                true
        );

        CompletableFuture<Void> future = auditService.logAsync(entry);
        assertNotNull(future);
        future.join(); // ✅ wait for async completion
    }
}
