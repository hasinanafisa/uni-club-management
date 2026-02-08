package dao;

import util.DBUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.ClubMember;

public class ClubMemberDAO {

    // Lecturer: does user advise any club?
    public boolean hasClubMembership(int userId) {
        String sql = "SELECT 1 FROM club_member WHERE user_id = ? AND role = 'Advisor'";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Is user member of specific club?
    public boolean isMember(int userId, int clubId) {
        String sql = "SELECT 1 FROM club_member WHERE user_id = ? AND club_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get user role
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

    // Add advisor
    public void addAdvisor(int userId, int clubId) throws SQLException {
        String sql = "INSERT INTO club_member (user_id, club_id, role) VALUES (?, ?, 'Advisor')";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            ps.executeUpdate();
        }
    }

    // Student joins club
    public void addStudent(int userId, int clubId) throws SQLException {
        String sql = "INSERT INTO club_member (user_id, club_id, role) VALUES (?, ?, 'Member')";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clubId);
            ps.executeUpdate();
        }
    }

    // Get student's club
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

    public Integer getClubIdByUser(int userId) {
        String sql = "SELECT club_id FROM club_member WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("club_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    public List<ClubMember> getMembersWithJoinDate(int clubId) {
        List<ClubMember> list = new ArrayList<>();

        String sql = """
            SELECT cm.club_member_id, cm.user_id, cm.club_id,
                   cm.role, cm.join_date
            FROM club_member cm
            WHERE cm.club_id = ?
            ORDER BY
                CASE cm.role
                    WHEN 'Advisor' THEN 1
                    WHEN 'President' THEN 2
                    WHEN 'Vice President' THEN 3
                    WHEN 'Secretary' THEN 4
                    WHEN 'Treasurer' THEN 5
                    WHEN 'Member' THEN 6
                END
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ClubMember cm = new ClubMember();
                cm.setClubMemberId(rs.getInt("club_member_id"));
                cm.setUserId(rs.getInt("user_id"));
                cm.setClubId(rs.getInt("club_id"));
                cm.setRole(rs.getString("role"));
                cm.setJoinDate(rs.getDate("join_date"));
                list.add(cm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean hasAnyClub(int userId) {
        String sql = "SELECT 1 FROM club_member WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
