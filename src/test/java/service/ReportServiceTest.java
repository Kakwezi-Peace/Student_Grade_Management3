package service;

import model.Student;
import model.Grade;
import model.Report;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    @Test
    public void testGenerateReportObject() {
        Student s = new Student("STU001", "Alice");
        s.addGrade(new Grade("MAT101", 85, LocalDate.now()));
        s.addGrade(new Grade("ENG203", 72, LocalDate.now()));

        ReportService rs = new ReportService();
        Report report = rs.generateReport(s);

        assertNotNull(report);
        assertEquals("STU001", report.getStudent().getId());   // ✅ fixed
        assertTrue(report.getGrades().size() > 0);
    }

    @Test
    public void testGenerateReportAsString() {
        Student s = new Student("STU001", "Alice");
        s.addGrade(new Grade("MAT101", 85, LocalDate.now()));

        ReportService rs = new ReportService();
        String reportText = rs.generateReportAsString(s);

        assertTrue(reportText.contains("Alice"));
        assertTrue(reportText.contains("MAT101"));
    }
}
