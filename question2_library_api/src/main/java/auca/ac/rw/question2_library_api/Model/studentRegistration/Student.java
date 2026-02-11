package auca.ac.rw.question2_library_api.Model.studentRegistration;

public class Student {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private Double gpa;

    public Student() {}
    
    public Student(Long studentId, String firstName, String lastName, 
                   String email, String major, Double gpa) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.gpa = gpa;
    }

    // Getters and setters for all 6 fields
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}