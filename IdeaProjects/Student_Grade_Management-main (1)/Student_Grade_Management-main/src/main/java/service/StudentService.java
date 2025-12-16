package service;

import model.Student;
import utils.ValidationUtils;

public class StudentService {

    public Student createStudent(String id, String firstName, String lastName,
                                 String email, String phone,
                                 String courseCode, String enrollmentDate,
                                 int grade) {

        // Validate Student ID
        if (!ValidationUtils.isValidStudentId(id)) {
            System.out.println("Invalid Student ID (Example: ST301)");
            return null;
        }

        // Validate First Name
        if (ValidationUtils.isValidName(firstName)) {
            System.out.println("Invalid First Name (Letters only)");
            return null;
        }

        // Validate Last Name
        if (ValidationUtils.isValidName(lastName)) {
            System.out.println("Invalid Last Name (Letters only)");
            return null;
        }

        // Validate Email
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println("Invalid Email (Example: peace@gmail.com)");
            return null;
        }

        // Validate Phone
        if (!ValidationUtils.isValidPhone(phone)) {
            System.out.println("Invalid Phone Number (Example: 0781234567)");
            return null;
        }

        // Validate Course Code
        if (!ValidationUtils.isValidCourseCode(courseCode)) {
            System.out.println("Invalid Course Code (Use format: CSC101)");
            return null;
        }

        // Validate Enrollment Date
        if (!ValidationUtils.isValidDate(enrollmentDate)) {
            System.out.println("Invalid Enrollment Date (YYYY-MM-DD)");
            return null;
        }

        // Validate Grade
        if (!ValidationUtils.isValidGrade(grade)) {
            System.out.println("Invalid Grade (0 - 100)");
            return null;
        }

        // ✅ ALL VALID → CREATE STUDENT
        System.out.println("Student created successfully");

        return new Student(
                id,
                firstName,
                lastName,
                email,
                phone,
                courseCode,
                enrollmentDate,
                grade
        );
    }
}
