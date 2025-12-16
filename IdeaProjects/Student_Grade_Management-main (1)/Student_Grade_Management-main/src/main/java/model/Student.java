package model;


public class Student {

    private final String studentId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String courseCode;
    private final String enrollmentDate;
    private final int grade;

    public Student(String studentId, String firstName, String lastName,
                   String email, String phoneNumber,
                   String courseCode, String enrollmentDate,
                   int grade) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.courseCode = courseCode;
        this.enrollmentDate = enrollmentDate;
        this.grade = grade;
    }

    public void display() {
        System.out.println("Student ID      : " + studentId);
        System.out.println("First Name      : " + firstName);
        System.out.println("Last Name       : " + lastName);
        System.out.println("Email           : " + email);
        System.out.println("Phone Number    : " + phoneNumber);
        System.out.println("Course Code     : " + courseCode);
        System.out.println("Enrollment Date : " + enrollmentDate);
        System.out.println("Grade           : " + grade);
    }

    public int getGrade() {
        return grade;
    }
}
