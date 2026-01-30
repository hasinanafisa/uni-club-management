/**
 * @izyanie
 * @27/12/2025
 */

package dao;

import model.Event;
import util.DBUtil;

import java.sql.*;
import java.util.*;

public class EventDAO {
    // READ ALL
    public List<Event> getAllEvents(int clubId) {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT event_id, title, description, event_date, event_time, location, "
                   + "banner_image_path, qr_path "
                   + "FROM event WHERE club_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);   // ✅ IMPORTANT

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event();
                    e.setEventID(rs.getInt("event_id"));
                    e.setEventTitle(rs.getString("title"));
                    e.setEventDesc(rs.getString("description"));
                    e.setEventDate(rs.getDate("event_date"));
                    e.setEventTime(rs.getTime("event_time"));
                    e.setEventLoc(rs.getString("location"));
                    e.setBannerImagePath(rs.getString("banner_image_path"));
                    e.setQrPath(rs.getString("qr_path"));
                    list.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    // READ ONE (FOR EDIT)
    public Event getEventById(int clubID) {
        Event e = null;
        String sql = "SELECT * FROM event WHERE event_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubID);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    e = new Event();
                    e.setEventID(rs.getInt("event_id"));
                    e.setEventTitle(rs.getString("title"));
                    e.setEventDesc(rs.getString("description"));
                    e.setEventDate(rs.getDate("event_date"));
                    e.setEventTime(rs.getTime("event_time"));
                    e.setEventLoc(rs.getString("location"));
                    e.setBannerImagePath(rs.getString("banner_image_path"));
                    e.setQrPath(rs.getString("qr_path"));
                }
            }
        } catch (Exception ex) { }
        return e;
    }

    // CREATE
    public void createEvent(Event e) throws SQLException {
        String sql =
            "INSERT INTO event(club_id, title, description, event_date, event_time, location, banner_image_path, qr_path, created_by)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getClubID());
            ps.setString(2, e.getEventTitle());
            ps.setString(3, e.getEventDesc());
            ps.setDate(4, e.getEventDate());
            ps.setTime(5, e.getEventTime());
            ps.setString(6, e.getEventLoc());
            ps.setString(7, e.getBannerImagePath());
            ps.setString(8, e.getQrPath());
            ps.setInt(9, e.getCreatedBy());

            ps.executeUpdate();
        }
    }
    
    // UPDATE
    public void updateEvent(Event e) throws SQLException {
        String sql = "UPDATE event SET title=?, description=?, event_date=?, event_time=?, location=?, "
                + "banner_image_path=?, qr_path=? "
                + "WHERE event_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getEventTitle());
            ps.setString(2, e.getEventDesc());
            ps.setDate(3, e.getEventDate());
            ps.setTime(4, e.getEventTime());
            ps.setString(5, e.getEventLoc());
            ps.setString(6, e.getBannerImagePath());
            ps.setString(7, e.getQrPath());
            ps.setInt(8, e.getEventID());

            ps.executeUpdate();
        } catch (Exception ex) { }
    }
    
    // DELETE
    public void deleteEvent(int id) throws SQLException {
        String sql = "DELETE FROM event WHERE event_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) { }
    }
}
