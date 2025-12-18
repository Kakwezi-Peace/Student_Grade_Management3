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

    // Build a simple report for a student
    public Report buildReport(Student s) {
        double avg = s.getGradeHistory().stream()
                .mapToInt(g -> g.getScore())
                .average().orElse(0.0);
        double gpa = avg / 25.0;
        return new Report(s, s.getGradeHistory(), gpa);
    }

    // Build a more detailed report (if you want to distinguish)
    public Report buildDetailedReport(Student s) {
        return buildReport(s); // for now, same as buildReport
    }

    // ✅ Combined export method (CSV, JSON, Binary) with audit logging
    public void generateAllFormats(Student s, Report r, String baseFilename) throws Exception {
        long start = System.currentTimeMillis();
        boolean success = false;

        Path csv = fileService.exportCsv(baseFilename, r);
        Path json = fileService.exportJson(baseFilename, r);
        Path bin = fileService.exportBinary(baseFilename, r);
        success = true;

        long end = System.currentTimeMillis();

        if (auditService != null) {
            auditService.logAsync(new model.AuditEntry(
                    Instant.now(),
                    Thread.currentThread().getName(),
                    "GENERATE_REPORT",
                    "Generated all formats for " + s.getId(),
                    end - start,
                    success));
        }

        System.out.println("\n✓ Export completed:");
        System.out.println(" - CSV:  " + csv.toAbsolutePath());
        System.out.println(" - JSON: " + json.toAbsolutePath());
        System.out.println(" - BIN:  " + bin.toAbsolutePath());
    }

    // ✅ Proper exportAll method returning ExportResult
    public ExportResult exportAll(String baseFilename, Report report) throws Exception {
        Path csv = fileService.exportCsv(baseFilename, report);
        Path json = fileService.exportJson(baseFilename, report);
        Path bin = fileService.exportBinary(baseFilename, report);

        return new ExportResult(csv, json, bin);
    }

    // ✅ Record type to hold results
    public record ExportResult(Path csv, Path json, Path bin) {}
}
