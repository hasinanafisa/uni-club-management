/**
 * @izyanie
 * @28/01/2026
 */

package dao;

import util.DBUtil;

import java.sql.*;

public class ClubMemberDAO {
    // Does user belong to ANY club?
    public boolean hasClubMembership(int userId) {
        String sql = """
            SELECT 1 FROM club_member
            WHERE user_id = ? AND role = 'Advisor'
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if at least one row
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get role
    public String getUserRole(int userId) {
        String sql = "SELECT role FROM club_member WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("role");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert advisor after club creation
    public void addAdvisor(int userId, int clubId) throws SQLException {
        String sql = """
            INSERT INTO club_member (user_id, club_id, role)
            VALUES (?, ?, 'Advisor')
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            ps.executeUpdate();
        }
    }
}
