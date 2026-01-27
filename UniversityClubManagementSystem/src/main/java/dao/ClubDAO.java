/**
 * @izyanie
 * @28/01/2026
 */

package dao;

import model.Club;
import java.sql.*;

public class ClubDAO {

    private static final String JDBC_URL = "jdbc:derby://localhost:1527/uniClub";
    private static final String JDBC_USER = "app";
    private static final String JDBC_PASS = "app";

    // ✅ CREATE CLUB (ADVISOR ONLY)
    public void createClub(Club club) throws SQLException {
        String sql = "INSERT INTO club (club_name, description, logo, created_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, club.getClubName());
            ps.setString(2, club.getDescription());
            ps.setString(3, club.getLogo());
            ps.setInt(4, club.getCreatedBy());

            ps.executeUpdate();
        }      
    }

    // ✅ GET CLUB BY ADVISOR (used after login)
    public Club getClubByAdvisor(int advisorUserId) {

        Club club = null;
        String sql = "SELECT * FROM club WHERE created_by = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, advisorUserId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                club = new Club(
                    rs.getInt("club_id"),
                    rs.getString("club_name"),
                    rs.getString("description"),
                    rs.getString("logo"),
                    rs.getInt("created_by"),
                    rs.getTimestamp("created_at")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return club;
    }

    // ✅ UPDATE CLUB (Edit only)
    public void updateClub(Club club) throws SQLException {

        String sql = "UPDATE club SET club_name = ?, description = ?, logo = ? WHERE club_id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, club.getClubName());
            ps.setString(2, club.getDescription());
            ps.setString(3, club.getLogo());
            ps.setInt(4, club.getClubId());

            ps.executeUpdate();
        }
    }

    // ✅ SAFETY CHECK - Does advisor already have club?
    public boolean advisorHasClub(int advisorUserId) {
        String sql = "SELECT 1 FROM club WHERE created_by = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, advisorUserId);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
