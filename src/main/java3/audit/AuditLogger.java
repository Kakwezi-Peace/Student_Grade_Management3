package audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    public synchronized void log(String operation, String status) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.printf("[%s] Operation: %s | Status: %s%n", timestamp, operation, status);
    }
}
