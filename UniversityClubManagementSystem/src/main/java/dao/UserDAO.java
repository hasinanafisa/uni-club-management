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
import java.sql.*;

public class UserDAO {

    private String jdbcURL = "jdbc:derby://localhost:1527/uniClub";
    private String jdbcUsername = "app";
    private String jdbcPassword = "app";

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
}
