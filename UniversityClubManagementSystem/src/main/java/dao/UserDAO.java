/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hasina
 */

package dao;

import model.User;
import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import util.DBConnection;


public class UserDAO {

    private String jdbcURL = "jdbc:derby://localhost:1527/uniClub";
    private String jdbcUsername = "app";
    private String jdbcPassword = "app";

    // Login
    public User login(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("faculty"),
                        rs.getString("course"),
                        rs.getString("profile_picture")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Get Events
    public List<Event> getJoinedEvents(int userId) {
        List<Event> events = new ArrayList<>();

        String sql = """
            SELECT e.event_id, e.event_name, e.event_date
            FROM events e
            JOIN event_participants ep ON e.event_id = ep.event_id
            WHERE ep.user_id = ?
        """;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Event event = new Event();
                    event.setEventID(rs.getInt("event_id"));
                    event.setEventTitle(rs.getString("event_name"));
                    event.setEventDate(rs.getDate("event_date"));
                    events.add(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
    
    public void updateProfileWithImage(User user, String fileName) {
        String sql = "UPDATE users SET full_name = ?, email = ?, profile_picture = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, fileName);
            ps.setInt(4, user.getUserId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateProfile(User user) {
        String sql = "UPDATE users SET full_name = ?, email = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getUserId());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}