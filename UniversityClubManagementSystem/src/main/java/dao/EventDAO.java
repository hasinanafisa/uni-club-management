package dao;

import model.Event;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // 🔹 READ ALL EVENTS BY CLUB
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
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setEventLoc(rs.getString("location"));
                e.setBannerImagePath(rs.getString("banner_image_path"));
                e.setQrPath(rs.getString("qr_path"));
                e.setCreatedBy(rs.getInt("created_by"));
                events.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    // 🔹 READ ONE EVENT
    public Event getEventById(int eventId) {
        Event e = null;

        String sql = "SELECT * FROM event WHERE event_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

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
                e.setCreatedBy(rs.getInt("created_by"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return e;
    }

    // 🔹 CREATE EVENT
    public int createEvent(Event e) throws SQLException {
        int generatedEventId = -1;

        String sql = """
            INSERT INTO event
            (club_id, title, description, event_date, event_time,
             location, banner_image_path, qr_path, created_by)
            VALUES (?,?,?,?,?,?,?,?,?)
        """;

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

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedEventId = rs.getInt(1);
            }
        }

        return generatedEventId;
    }

    // 🔹 UPDATE EVENT
    public void updateEvent(Event e) throws SQLException {
        String sql = """
            UPDATE event
            SET title=?, description=?, event_date=?, event_time=?,
                location=?, banner_image_path=?, qr_path=?
            WHERE event_id=?
        """;

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
        }
    }

    // 🔹 DELETE EVENT
    public void deleteEvent(int eventId) throws SQLException {
        String sql = "DELETE FROM event WHERE event_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.executeUpdate();
        }
    }

    // 🔹 DASHBOARD COUNT
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
    
    public int countEventsThisMonthByClub(int clubId) {
        String sql = """
        SELECT COUNT(*)
        FROM event
        WHERE club_id = ?
          AND MONTH(event_date) = MONTH(CURRENT_DATE)
          AND YEAR(event_date) = YEAR(CURRENT_DATE)
        """;

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getMostPopularEventByClub(int clubId) {
        String sql = """
        SELECT e.title, COUNT(er.registration_id) AS total
        FROM event e
        LEFT JOIN event_registration er
            ON e.event_id = er.event_id
        WHERE e.club_id = ?
        GROUP BY e.event_id, e.title
        ORDER BY total DESC
        FETCH FIRST 1 ROWS ONLY
        """;

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("title");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    
    public int getTotalParticipantsByClub(int clubId) {
        String sql = """
        SELECT COUNT(*)
        FROM event_registration er
        JOIN event e ON er.event_id = e.event_id
        WHERE e.club_id = ?
        """;

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
