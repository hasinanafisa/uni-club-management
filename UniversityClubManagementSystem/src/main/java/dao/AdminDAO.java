/**
 * @izyanie
 * @24/12/2025
 */

package com.mycompany.universityclubmanagementsystem.dao;

import com.mycompany.universityclubmanagementsystem.util.DBConnection;

import java.sql.*;

public class AdminDAO {

    public boolean login(String email, String password) {
        String sql = "SELECT * FROM admin WHERE admin_email=? AND admin_password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
