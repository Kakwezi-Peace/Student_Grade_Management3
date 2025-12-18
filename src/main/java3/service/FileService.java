package service;

import config.Config;
import model.Report;
import utils.JsonUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileService {

    public Path exportCsv(String filename, Report report) throws IOException {
        Path dir = Path.of(Config.REPORTS_CSV);
        Files.createDirectories(dir);
        Path file = dir.resolve(filename + ".csv");
        try (BufferedWriter bw = Files.newBufferedWriter(file, Config.UTF8, CREATE, TRUNCATE_EXISTING)) {
            bw.write("student_id,gpa,grade_count,exported_at\n");
            bw.write(String.format("%s,%.2f,%d,%s%n",
                    report.getStudent().getId(),
                    report.getGpa(),
                    report.getGrades().size(),
                    Instant.now()));
        }
        return file;
    }

    public Path exportJson(String filename, Report report) throws IOException {
        Path dir = Path.of(Config.REPORTS_JSON);
        Files.createDirectories(dir);
        Path file = dir.resolve(filename + ".json");
        try (BufferedWriter bw = Files.newBufferedWriter(file, Config.UTF8, CREATE, TRUNCATE_EXISTING)) {
            bw.write(JsonUtils.reportToJson(report));
        }
        return file;
    }

    public Path exportBinary(String filename, Report report) throws IOException {
        Path dir = Path.of(Config.REPORTS_BINARY);
        Files.createDirectories(dir);
        Path file = dir.resolve(filename + ".dat");
        try (OutputStream os = Files.newOutputStream(file, CREATE, TRUNCATE_EXISTING);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(report);
        }
        return file;
    }

    public long streamCsvCountLines(Path p) throws IOException {
        try (var lines = Files.lines(p, Config.UTF8)) {
            return lines.count();
        }
    }
}
