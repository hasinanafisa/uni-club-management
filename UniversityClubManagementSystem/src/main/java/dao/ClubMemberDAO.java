package dao;

import util.DBUtil;
import java.sql.*;

public class ClubMemberDAO {

    //Lecturer: does user advise any club?
    public boolean hasClubMembership(int userId) {
        String sql = """
            SELECT 1 FROM club_member
            WHERE user_id = ? AND role = 'Advisor'
        """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //is user member of specific club?
    public boolean isMember(int userId, int clubId) {
        String sql = """
            SELECT 1 FROM club_member
            WHERE user_id = ? AND club_id = ?
        """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Get user role in club
    public String getUserRole(int userId) {
        String sql = "SELECT role FROM club_member WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("role");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Add advisor
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

    //Student joins club
    public void addStudent(int userId, int clubId) throws SQLException {
        String sql = """
            INSERT INTO club_member (user_id, club_id, role)
            VALUES (?, ?, 'Member')
        """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            ps.executeUpdate();
        }
    }

    //Get student's club
    public Integer getStudentClubId(int userId) {
        String sql = "SELECT club_id FROM club_member WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("club_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
        public int getMemberCount(int clubId) {
        String sql = "SELECT COUNT(*) FROM club_member WHERE club_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}