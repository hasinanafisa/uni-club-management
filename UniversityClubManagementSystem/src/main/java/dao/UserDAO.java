/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hasina
 */

package dao;

import util.DBUtil;
import model.User;
import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import util.DBConnection;

public class UserDAO {
    public User login(String email, String password) {
        User user = null;
        String sql = "SELECT user_id, full_name, email, user_type, faculty, course FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type")); // STUDENT / LECTURER
                user.setFaculty(rs.getString("faculty"));
                user.setCourse(rs.getString("course"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public void updateUserType(int userId, String role) {
        String sql = "UPDATE users SET user_type = ? WHERE user_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, role);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<User> getMembersByClubId(int clubId) {
    List<User> members = new ArrayList<>();

    String sql = "SELECT user_id, full_name, email, user_type FROM users WHERE club_id = ?";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, clubId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                    u.setUserType(rs.getString("user_type"));

                    members.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return members;
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
            try (Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

                ps.setInt(1, userId);

                while (rs.next()) {
                    Event event = new Event();
                    event.setEventID(rs.getInt("event_id"));
                    event.setEventTitle(rs.getString("event_name"));
                    event.setEventDate(rs.getDate("event_date"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
    
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setUserType(rs.getString("user_type"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
