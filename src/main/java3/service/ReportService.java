package service;

import config.Config;
import model.Report;
import model.Student;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

public class ReportService {
    private final FileService fileService = new FileService();
    private final AuditService auditService;

    // Constructor with audit logging
    public ReportService(AuditService auditService) {
        this.auditService = Objects.requireNonNull(auditService);
    }

    // Optional no-arg constructor (no audit logging)
    public ReportService() {
        this.auditService = null;
    }

    // ✅ Public method returning a Report object
    public Report generateReport(Student student) {
        return buildReport(student);
    }

    // ✅ Public method returning a String summary
    public String generateReportAsString(Student student) {
        Report report = buildReport(student);
        return report.toString(); // relies on Report.toString()
    }

    // ✅ Build a simple report for a student
    public Report buildReport(Student student) {
        double avg = student.getGradeHistory().stream()
                .mapToInt(g -> g.getScore())
                .average().orElse(0.0);
        double gpa = avg / 25.0; // simple GPA proxy
        return new Report(student, student.getGradeHistory(), gpa);
    }

    // Build a more detailed report (currently same as buildReport)
    public Report buildDetailedReport(Student student) {
        return buildReport(student);
    }

    // Combined export method (CSV, JSON, Binary) with audit logging
    public void generateAllFormats(Student student, Report report, String baseFilename) throws Exception {
        long start = System.currentTimeMillis();
        boolean success = false;

        Path csv = fileService.exportCsv(baseFilename, report);
        Path json = fileService.exportJson(baseFilename, report);
        Path bin = fileService.exportBinary(baseFilename, report);
        success = true;

        long end = System.currentTimeMillis();

        if (auditService != null) {
            auditService.logAsync(new model.AuditEntry(
                    Instant.now(),
                    Thread.currentThread().getName(),
                    "GENERATE_REPORT",
                    "Generated all formats for " + student.getId(),
                    end - start,
                    success));
        }

        System.out.println("\n✓ Export completed:");
        System.out.println(" - CSV:  " + csv.toAbsolutePath());
        System.out.println(" - JSON: " + json.toAbsolutePath());
        System.out.println(" - BIN:  " + bin.toAbsolutePath());
    }

    // ✅ Proper exportAll method returning ExportResult
    public ExportResult exportAll(String base, Report report) throws Exception {
        Path csv = fileService.exportCsv(base, report);
        Path json = fileService.exportJson(base, report);
        Path bin = fileService.exportBinary(base, report);

        return new ExportResult(csv, json, bin);
    }

    // ✅ Record type to hold results
    public static record ExportResult(Path csv, Path json, Path bin) {}
}
