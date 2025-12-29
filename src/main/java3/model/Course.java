package model;

public class Course implements java.io.Serializable {
    private final String code; // AAA###
    private final String name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }
//getters
    public String getCode() { return code; }
    public String getName() { return name; }
}
