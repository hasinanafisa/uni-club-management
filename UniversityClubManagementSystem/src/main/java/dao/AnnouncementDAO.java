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
    // READ ALL
    public List<Announcement> getAllAnnouncements() {
        List<Announcement> list = new ArrayList<>();
        String sql = "SELECT announce_id,announce_title, announce_content, announce_category, "
                + "image_path, attachment_path, event_id "
                + "FROM announcement";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Announcement a = new Announcement();
                a.setAnnounceID(rs.getInt("announce_id"));
                a.setAnnounceTitle(rs.getString("announce_title"));
                a.setAnnounceContent(rs.getString("announce_content"));
                a.setAnnounceCategory(rs.getString("announce_category"));
                a.setImagePath(rs.getString("image_path"));
                a.setAttachmentPath(rs.getString("attachment_path"));
                a.setEventID(rs.getInt("event_id"));
                list.add(a);
            }
        } catch (Exception e) { }
        return list;
    }
    
    // READ ONE (FOR EDIT)
    public Announcement getAnnouncementById(int id) {
        Announcement a = null;
        String sql = "SELECT * FROM announcement WHERE announce_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Announcement();
                a.setAnnounceID(rs.getInt("announce_id"));
                a.setAnnounceTitle(rs.getString("announce_title"));
                a.setAnnounceContent(rs.getString("announce_content"));
                a.setAnnounceCategory(rs.getString("announce_category"));
                a.setImagePath(rs.getString("image_path"));
                a.setAttachmentPath(rs.getString("attachment_path"));
                a.setEventID(rs.getInt("event_id"));
            }
        } catch (Exception e) { }
        return a;
    }

    // CREATE
    public void postAnnouncement(Announcement a) throws SQLException {
        String sql =
            "INSERT INTO announcement(announce_title,announce_content,announce_category,image_path,attachment_path,event_id) "
                + "VALUES (?,?,?,?,?,?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getAnnounceTitle());
            ps.setString(2, a.getAnnounceContent());
            ps.setString(3, a.getAnnounceCategory());
            ps.setString(4, a.getImagePath());
            ps.setString(5, a.getAttachmentPath());
            ps.setInt(6, a.getEventID());

            ps.executeUpdate();
        } catch (Exception e) { }
    }
    
    // UPDATE
    public void updateAnnouncement(Announcement a) throws SQLException {
        String sql =
        "UPDATE announcement SET announce_title=?, announce_content=?, announce_category=?, image_path=?, attachment_path=?, event_id=? "
                + "WHERE announce_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getAnnounceTitle());
            ps.setString(2, a.getAnnounceContent());
            ps.setString(3, a.getAnnounceCategory());
            ps.setString(4, a.getImagePath());
            ps.setString(5, a.getAttachmentPath());
            ps.setInt(6, a.getEventID());
            ps.setInt(7, a.getAnnounceID());

            ps.executeUpdate();
        } catch (Exception e) { }
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
        String sql = "DELETE FROM announcement WHERE announce_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) { }
    }
}
