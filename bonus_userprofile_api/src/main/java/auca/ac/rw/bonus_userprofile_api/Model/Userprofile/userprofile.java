package auca.ac.rw.bonus_userprofile_api.Model.Userprofile;

public class userprofile {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private int age;
    private String country;
    private String bio;
    private boolean active;

    public userprofile() {}

    public userprofile(Long userId, String username, String email, String fullName, 
                      int age, String country, String bio, boolean active) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.country = country;
        this.bio = bio;
        this.active = active;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}