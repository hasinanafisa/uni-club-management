/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Razan
 */

import model.User;
import model.Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private String jdbcURL = "jdbc:derby://localhost:1527/uniClub";
    private String jdbcUsername = "app";
    private String jdbcPassword = "app";

    // LOGIN METHOD
    public User login(String email, String password) {

        User user = null;
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            PreparedStatement ps = conn.prepareStatement(sql);
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
                        rs.getString("course")
                );
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // GET EVENTS JOINED BY STUDENT
    
    public List<Event> getJoinedEvents(int userId) {

        List<Event> list = new ArrayList<>();
        String sql =
                "SELECT e.event_name, e.event_date " +
                "FROM event e " +
                "JOIN event_participant p ON e.event_id = p.event_id " +
                "WHERE p.user_id = ?";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setName(rs.getString("event_name"));
                e.setDate(rs.getDate("event_date"));
                list.add(e);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}