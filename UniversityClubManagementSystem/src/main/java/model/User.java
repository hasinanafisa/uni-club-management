/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Razan
 */

public class User {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String faculty;
    private String course;

    public User(int userId, String fullName, String email, String role, String faculty, String course) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.faculty = faculty;
        this.course = course;
    }

    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getFaculty() { return faculty; }
    public String getCourse() { return course; }
}
