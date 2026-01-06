/**
 * @izyanie
 * @24/12/2025
 */

package dao;

import util.DBUtil;

import java.sql.*;

public class AdminDAO {

    public boolean login(String email, String password) {
        String sql = "SELECT * FROM admin WHERE admin_email=? AND admin_password=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) { }
        
        return false;
    }
}
