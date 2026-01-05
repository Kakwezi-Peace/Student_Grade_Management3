package service;

import model.Student;

import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StudentService {
    private final Map<String, Student> studentMap;
    private final AuditService auditService;
    private final ThreadSafeCache cache;  // ✅ added cache

    public StudentService(Map<String, Student> studentMap, AuditService auditService, ThreadSafeCache cache) {
        this.studentMap = studentMap;
        this.auditService = auditService;
        this.cache = cache;
    }

    public void addStudent(Student s) {
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            if (!studentMap.containsKey(s.getId())) {
                studentMap.put(s.getId(), s);
                success = true;
                // ✅ Invalidate caches
                cache.invalidate("student:" + s.getId());
                cache.invalidate("stats:global");
            }
        } finally {
            long end = System.currentTimeMillis();
            if (auditService != null) {
                auditService.logAsync(new model.AuditEntry(
                        Instant.now(),
                        Thread.currentThread().getName(),
                        "ADD_STUDENT",
                        "Added student " + s.getId(),
                        end - start,
                        success));
            }
        }
    }

    public Student getById(String id) {
        Student cached = cache.get("student:" + id);
        if (cached != null) return cached;

        Student s = studentMap.get(id);
        if (s != null) cache.put("student:" + id, s, 5 * 60 * 1000);
        return s;
    }

    public Collection<Student> listAll() {
        return studentMap.values();
    }

    // Stream-friendly accessors
    public Stream<Student> streamAll() {
        return studentMap.values().stream();
    }

    public Stream<Student> parallelStreamAll() {
        return studentMap.values().parallelStream();
    }

    // Advanced searches
    public List<Student> searchByIdContains(String fragment) {
        String f = fragment.toLowerCase();
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getId() != null && s.getId().toLowerCase().contains(f)) out.add(s);
        }
        return out;
    }

    public List<Student> searchByNameContains(String fragment) {
        String f = fragment.toLowerCase();
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getName() != null && s.getName().toLowerCase().contains(f)) out.add(s);
        }
        return out;
    }

    public List<Student> searchByEmailDomain(String domain) {
        String d = domain.toLowerCase();
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getEmail() != null && s.getEmail().toLowerCase().endsWith(d)) out.add(s);
        }
        return out;
    }

    public List<Student> searchByType(String type) {
        String t = type.toLowerCase();
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getType() != null && s.getType().toLowerCase().equals(t)) out.add(s);
        }
        return out;
    }

    // Regex searches
    public List<Student> searchByIdRegex(Pattern p) {
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getId() != null && p.matcher(s.getId()).matches()) out.add(s);
        }
        return out;
    }

    public List<Student> searchByNameRegex(Pattern p) {
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getName() != null && p.matcher(s.getName()).find()) out.add(s);
        }
        return out;
    }

    public List<Student> searchByEmailRegex(Pattern p) {
        List<Student> out = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getEmail() != null && p.matcher(s.getEmail()).matches()) out.add(s);
        }
        return out;
    }
}
