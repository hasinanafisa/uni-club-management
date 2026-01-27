/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static util.DBUtil.getConnection;

public class UserDAO {

    private static final String jdbcURL = "jdbc:derby://localhost:1527/uniClub";
    private static final String jdbcUsername = "app";
    private static final String jdbcPassword = "app";

    // ✅ LOGIN (IDENTITY VERIFICATION ONLY)
    public User login(String email, String password) {
        User user = null;
        String sql = "SELECT user_id, full_name, email, faculty, course FROM users WHERE email = ? AND password = ?";

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
                        rs.getString("faculty"),
                        rs.getString("course")
                    ); // role intentionally NOT set here
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // ✅ CHECK FOR CLUB MEMBERSHIP
    public boolean hasClubMembership(int userId) {
        String sql = "SELECT 1 FROM club_member WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // ✅ GET USER ROLE (from club_member)
    public String getUserRole(int userId) {
        String role = null;
        String sql = "SELECT role FROM club_member WHERE user_id = ?";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    role = rs.getString("role");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }
    
    // ✅ GET CLUB ID of USER
    public Integer getUserClubId(int userId) {
        Integer clubId = null;

        String sql = "SELECT club_id FROM club_member WHERE user_id = ?";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    clubId = rs.getInt("club_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clubId;
    }

    // ✅ GET EVENTS JOINED BY STUDENT
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
}