package model;

import java.time.LocalDate;

public class Grade implements java.io.Serializable {
    private final String courseCode; // e.g., MAT101
    private final int score;         // 0-100
    private final LocalDate recordedAt; // when the grade was recorded

    public Grade(String courseCode, int score, LocalDate recordedAt) {
        this.courseCode = courseCode;
        this.score = score;
        this.recordedAt = recordedAt;
    }

    public String getCourseCode() { return courseCode; }
    public int getScore() { return score; }
    public LocalDate getRecordedAt() { return recordedAt; }
}
