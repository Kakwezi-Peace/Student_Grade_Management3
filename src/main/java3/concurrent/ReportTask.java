package concurrent;

import model.Report;
import model.Student;
import service.ReportService;

import java.util.concurrent.Callable;

public class ReportTask implements Callable<String> {
    private final Student student;
    private final ReportService reportService;

    public ReportTask(Student student, ReportService reportService) {
        this.student = student;
        this.reportService = reportService;
    }
// methods
    @Override
    public String call() throws Exception {
        long start = System.currentTimeMillis();
        Report r = reportService.buildDetailedReport(student);
        String base = student.getId().toLowerCase() + "_detailed";
        reportService.generateAllFormats(student, r, base);  // ✅ now exists
        long end = System.currentTimeMillis();
        return String.format("STU %s ✓ (%dms)", student.getId(), (end - start));
    }
}
