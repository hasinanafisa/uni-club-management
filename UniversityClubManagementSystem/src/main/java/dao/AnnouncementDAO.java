/**
 * @izyanie
 * @30/12/2025
 */

package dao;

import model.Announcement;
import util.DBUtil;

import java.sql.*;
import java.util.*;

public class AnnouncementDAO {
    // READ ALL BY CLUB
    public List<Announcement> getAnnouncementsByClubId(int clubId) {
        List<Announcement> list = new ArrayList<>();
        String sql = "SELECT announcement_id, club_id, event_id, title, content, category, " + 
                "image_path, attachment_path, posted_by, posted_at " + 
                "FROM announcement WHERE club_id = ? ORDER BY posted_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Announcement a = new Announcement();
                
                a.setAnnouncementId(rs.getInt("announcement_id"));
                a.setClubId(rs.getInt("club_id"));
                a.setEventId(rs.getInt("event_id"));
                a.setTitle(rs.getString("title"));
                a.setContent(rs.getString("content"));
                a.setCategory(rs.getString("category"));
                a.setImagePath(rs.getString("image_path"));
                a.setAttachmentPath(rs.getString("attachment_path"));
                a.setPostedBy(rs.getInt("posted_by"));
                a.setPostedAt(rs.getTimestamp("posted_at"));
                list.add(a);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    // READ ONE ANNOUNCEMENT
    public Announcement getAnnouncementById(int id) {
        Announcement a = null;
        String sql = "SELECT * FROM announcement WHERE announcement_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Announcement();
                a.setAnnouncementId(rs.getInt("announcement_id"));
                a.setClubId(rs.getInt("club_id"));
                a.setEventId(rs.getInt("event_id"));
                a.setTitle(rs.getString("title"));
                a.setContent(rs.getString("content"));
                a.setCategory(rs.getString("category"));
                a.setImagePath(rs.getString("image_path"));
                a.setAttachmentPath(rs.getString("attachment_path"));
                a.setPostedBy(rs.getInt("posted_by"));
                a.setPostedAt(rs.getTimestamp("posted_at"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return a;
    }

    // CREATE
    public int postAnnouncement(Announcement a) throws SQLException {
        int generatedAnnouncementId = -1;
        
        String sql =
            "INSERT INTO announcement(club_id, event_id, title, content, category, image_path, attachment_path, posted_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, a.getClubId());
            ps.setInt(2, a.getEventId());
            ps.setString(3, a.getTitle());
            ps.setString(4, a.getContent());
            ps.setString(5, a.getCategory());
            ps.setString(6, a.getImagePath());
            ps.setString(7, a.getAttachmentPath());
            ps.setInt(8, a.getPostedBy());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    generatedAnnouncementId = rs.getInt(1);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return generatedAnnouncementId;
    }
    
    // UPDATE
    public void updateAnnouncement(Announcement a) throws SQLException {
        String sql =
        "UPDATE announcement SET title=?, content=?, category=?, image_path=?, attachment_path=? "
                + "WHERE announcement_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getContent());
            ps.setString(3, a.getCategory());
            ps.setString(4, a.getImagePath());
            ps.setString(5, a.getAttachmentPath());
            ps.setInt(6, a.getAnnouncementId());
            ps.executeUpdate();
            
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public int getAnnouncementCount(int clubId) {
    String sql = "SELECT COUNT(*) FROM announcement WHERE club_id = ?";
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

    
    // DELETE
    public void deleteAnnouncement(int id) throws SQLException {
        String sql = "DELETE FROM announcement WHERE announcement_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (Exception e) { e.printStackTrace(); }
    }
}
