package audit;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AuditTrailTest {

    @Test
    public void testLogEntryCreation() throws IOException {
        Path logPath = Paths.get("audit.log");
        String entry = LocalDateTime.now() + " - Added Student STU001";

        Files.writeString(logPath, entry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        String content = Files.readString(logPath);

        assertTrue(content.contains("STU001"));

        Files.deleteIfExists(logPath);
    }
}
