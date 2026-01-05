package regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RegexValidationTest {

    @Test
    public void testValidStudentID() {
        Pattern studentIdPattern = Pattern.compile("^STU\\d{3}$");
        assertTrue(studentIdPattern.matcher("STU123").matches());
        assertFalse(studentIdPattern.matcher("stu-001").matches());
    }

    @Test
    public void testValidEmail() {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        assertTrue(emailPattern.matcher("john.smith@university.edu").matches());
        assertFalse(emailPattern.matcher("john.smith@uni").matches());
    }

    @Test
    public void testValidPhoneNumber() {
        Pattern phonePattern = Pattern.compile("^(\\+1-\\d{3}-\\d{3}-\\d{4}|\\d{10}|\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\)\\d{3}-\\d{4})$");
        assertTrue(phonePattern.matcher("+1-555-012-3456").matches());
        assertFalse(phonePattern.matcher("555-0123").matches());
    }
}
