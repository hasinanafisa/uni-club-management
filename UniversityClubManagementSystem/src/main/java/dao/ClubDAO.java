/**
 * @izyanie
 * @28/01/2026
 */
package dao;

import util.DBUtil;
import model.Club;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ClubDAO {

    public int createClubAndReturnId(Club club) throws SQLException {
        int generatedClubId = -1;

        String sql = "INSERT INTO club " +
                     "(club_name, description, mission, achievements, logo_path, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, club.getClubName());
            ps.setString(2, club.getDescription());
            ps.setString(3, club.getMission());
            ps.setString(4, club.getAchievements());
            ps.setString(5, club.getLogoPath());
            ps.setInt(6, club.getCreatedBy());

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
                    club.setMission(rs.getString("mission"));
                    club.setAchievements(rs.getString("achievements"));
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

    public List<Club> getAllClubs() throws SQLException {
        List<Club> clubs = new ArrayList<>();

        String sql = "SELECT club_id, club_name, description, logo_path FROM club";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Club c = new Club();
                c.setClubID(rs.getInt("club_id"));
                c.setClubName(rs.getString("club_name"));
                c.setDescription(rs.getString("description"));
                c.setLogoPath(rs.getString("logo_path"));
                clubs.add(c);
            }
        }

        return clubs;
    }
}
