/**
 * @izyanie
 * @28/01/2026
 */

package dao;

import java.sql.*;

public class ClubMemberDAO {

    private static final String JDBC_URL = "jdbc:derby://localhost:1527/uniClub";
    private static final String JDBC_USER = "app";
    private static final String JDBC_PASS = "app";

    // ✅ ADD USER TO CLUB
    public void addMember(int userId, int clubId, String role) throws SQLException {
        String sql = """
            INSERT INTO club_member (user_id, club_id, role)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            ps.setString(3, role);
            ps.executeUpdate();
        }
    }

    // ✅ GET USER ROLE IN CLUB
    public String getUserRole(int userId) throws SQLException {
        String sql = """
            SELECT role FROM club_member
            WHERE user_id = ?
        """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }
        }
        return null; // user not in any club
    }

    // ✅ GET CLUB ID FOR USER
    public Integer getClubIdByUser(int userId) throws SQLException {
        String sql = """
            SELECT club_id FROM club_member
            WHERE user_id = ?
        """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("club_id");
            }
        }
        return null;
    }

    // ✅ CHECK IF USER IS PRESIDENT OR ADVISOR
    public boolean isAdminRole(int userId) throws SQLException {
        String sql = """
            SELECT role FROM club_member
            WHERE user_id = ? AND role IN ('ADVISOR', 'PRESIDENT')
        """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }
}
