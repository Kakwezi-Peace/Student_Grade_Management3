package search;

import model.Student;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatternSearchService {

    public SearchResult searchByEmailDomain(List<Student> students, String domainRegex) {
        long start = System.currentTimeMillis();
        Pattern p = Pattern.compile(".*" + domainRegex.replace(".", "\\.") + "$");
        List<Student> matches = students.stream()
                .filter(s -> s.getEmail() != null && p.matcher(s.getEmail()).matches())
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        return new SearchResult(matches, students.size(), end - start);
    }

    public SearchResult searchByIdPattern(List<Student> students, String idRegex) {
        long start = System.currentTimeMillis();
        Pattern p = Pattern.compile(idRegex);
        List<Student> matches = students.stream()
                .filter(s -> p.matcher(s.getId()).matches())
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        return new SearchResult(matches, students.size(), end - start);
    }
    public SearchResult searchByNameRegex(List<Student> students, String nameRegex) {
        long start = System.currentTimeMillis();
        Pattern p = Pattern.compile(nameRegex, Pattern.CASE_INSENSITIVE);
        List<Student> matches = students.stream()
                .filter(s -> s.getName() != null && p.matcher(s.getName()).find())
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        return new SearchResult(matches, students.size(), end - start);
    }
}
