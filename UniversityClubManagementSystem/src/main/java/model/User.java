package model;

import java.io.Serializable;

public class User implements Serializable {

    private int userId;
    private String fullName;
    private String email;
    private String userType;   // STUDENT / LECTURER
    private String role;
    private String faculty;
    private String course;
    private int clubId;
    private String password;
    private String profileImage;

    // Default constructor
    public User() {}

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getClubId() { return clubId;}
    public void setClubId(int clubId) { this.clubId = clubId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

}
