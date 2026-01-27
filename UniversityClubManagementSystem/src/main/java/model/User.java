/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String fullName;
    private String email;
    private String role;
    private String faculty;
    private String course;

    // Default constructor
    public User() {}

    // ✅ FULL constructor (keep this for future/club_member join)
    public User(int userId, String fullName, String email, String role, String faculty, String course) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.faculty = faculty;
        this.course = course;
    }
    
    // ✅ SIMPLE constructor (for login)
    public User(int userId, String fullName, String email, String faculty, String course) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.faculty = faculty;
        this.course = course;
        this.role = null; // role resolved later from club_member
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
}