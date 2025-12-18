package model;

import java.time.Instant;

public class AuditEntry implements java.io.Serializable {
    private final Instant timestamp;
    private final String threadId;
    private final String operation;
    private final String userAction;
    private final long executionMs;
    private final boolean success;

    public AuditEntry(Instant timestamp,
                      String threadId,
                      String operation,
                      String userAction,
                      long executionMs,
                      boolean success) {
        this.timestamp = timestamp;
        this.threadId = threadId;
        this.operation = operation;
        this.userAction = userAction;
        this.executionMs = executionMs;
        this.success = success;
    }

    public Instant getTimestamp() { return timestamp; }
    public String getThreadId() { return threadId; }
    public String getOperation() { return operation; }
    public String getUserAction() { return userAction; }
    public long getExecutionMs() { return executionMs; }
    public boolean isSuccess() { return success; }
}
