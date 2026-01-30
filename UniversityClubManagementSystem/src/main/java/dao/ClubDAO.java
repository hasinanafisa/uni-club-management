/**
 * @izyanie
 * @28/01/2026
 */

package dao;

import util.DBUtil;
import model.Club;

import java.sql.*;

public class ClubDAO {
    public int createClubAndReturnId(Club club) throws SQLException {
        int generatedClubId = -1;

        String sql = "INSERT INTO club (club_name, description, logo_path, created_by) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, club.getClubName());
            ps.setString(2, club.getDescription());
            ps.setString(3, club.getLogoPath()); //can be null
            ps.setInt(4, club.getCreatedBy());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                generatedClubId = rs.getInt(1);
            }
        }
        return generatedClubId;
    }
    
    public Club getClubById(int clubId) {
        Club club = null;

        String sql = "SELECT * FROM club WHERE club_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clubId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    club = new Club();
                    club.setClubID(rs.getInt("club_id"));
                    club.setClubName(rs.getString("club_name"));
                    club.setDescription(rs.getString("description"));
                    club.setLogoPath(rs.getString("logo_path"));
                    club.setCreatedBy(rs.getInt("created_by"));
                    club.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return club;
    }
}