package console;

import model.Student;
import search.PatternSearchService;
import search.SearchResult;
import service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class PatternSearchConsole {
    private final StudentService studentService;
    private final PatternSearchService searchService = new PatternSearchService();

    public PatternSearchConsole(StudentService studentService) { this.studentService = studentService; }

    private static void accept(Student s) {
        System.out.printf("%s | %s | %s%n", s.getId(), s.getName(), s.getEmail());
    }
//
    public void run(Scanner sc) {
        List<Student> all = new ArrayList<Student>((Collection<? extends Student>) studentService.listAll());
        System.out.println("\nPATTERN-BASED SEARCH");
        System.out.println("1. Email Domain Pattern (e.g., @gmail.com)");
        System.out.println("2. Phone Area Code Pattern (e.g., 555)");
        System.out.println("3. Student ID Pattern (e.g., STU***)");
        System.out.println("4. Name Pattern (regex)");
        System.out.println("5. Custom Regex Pattern");
        System.out.print("Select type (1-5): ");
        int type = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Enter pattern: ");
        String pat = sc.nextLine().trim();

        SearchResult result = switch (type) {
            case 1 -> searchService.searchByEmailDomain(all, pat);
            case 3 -> {
                yield searchService.searchByIdPattern(all, pat);
            }
            case 4, 5 -> searchService.searchByNameRegex(all, pat);
            default -> new SearchResult(List.of(), all.size(), 0);
        };

        System.out.printf("%nSEARCH RESULTS (%d found)%n%n", result.matches().size());
        System.out.println("STU ID | NAME | EMAIL");
        result.matches().forEach(PatternSearchConsole::accept);
        System.out.printf("%nPattern Match Statistics:%nTotal Students Scanned: %d%nMatches Found: %d%nSearch Time: %dms%n",
                result.scanned(), result.matches().size(), result.timeMs());
    }
}
