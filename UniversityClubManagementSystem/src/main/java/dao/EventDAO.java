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
    // READ ONE EVENT
    public Event getEventById(int eventID) {
        Event e = null;
        String sql = "SELECT * FROM event WHERE event_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventID);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    e = new Event();
                    e.setEventID(rs.getInt("event_id"));
                    e.setClubId(rs.getInt("club_id"));
                    e.setEventTitle(rs.getString("title"));
                    e.setEventDesc(rs.getString("description"));
                    e.setEventDate(rs.getDate("event_date"));
                    e.setEventTime(rs.getTime("event_time"));
                    e.setEventLoc(rs.getString("location"));
                    e.setBannerImagePath(rs.getString("banner_image_path"));
                    e.setQrPath(rs.getString("qr_path"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
    
    // READ ALL
    public List<Event> getEventsByClubId(int clubId) {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE club_id = ? ORDER BY event_date";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("event_id"));
                e.setClubId(rs.getInt("club_id"));
                e.setEventTitle(rs.getString("title"));
                e.setEventDesc(rs.getString("description"));
                e.setEventLoc(rs.getString("location"));
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setCreatedBy(rs.getInt("created_by"));

                events.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return events;
    }

    // CREATE
    public int createEvent(Event e) throws SQLException {
        int generatedEventId = -1;
        
        String sql =
            "INSERT INTO event(club_id, title, description, event_date, event_time, location, banner_image_path, qr_path, created_by)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, e.getClubId());
            ps.setString(2, e.getEventTitle());
            ps.setString(3, e.getEventDesc());
            ps.setDate(4, e.getEventDate());
            ps.setTime(5, e.getEventTime());
            ps.setString(6, e.getEventLoc());
            ps.setString(7, e.getBannerImagePath());
            ps.setString(8, e.getQrPath());
            ps.setInt(9, e.getCreatedBy());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    generatedEventId = rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return generatedEventId;
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
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // DELETE
    public void deleteEvent(int id) throws SQLException {
        String sql = "DELETE FROM event WHERE event_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
        public int getUpcomingEventCount(int clubId) {
        String sql = """
            SELECT COUNT(*) FROM event
            WHERE club_id = ? AND event_date >= CURRENT_DATE
        """;
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
