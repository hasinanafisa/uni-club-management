package com.mycompany.universityclubmanagementsystem.dao;

import com.mycompany.universityclubmanagementsystem.model.Event;
import com.mycompany.universityclubmanagementsystem.util.DBConnection;

import java.sql.*;
import java.util.*;

public class EventDAO {

    // READ ALL
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT event_id, event_title, event_desc, event_date, event_time, event_loc, "
                + "banner_image_path, attendance_qr_path "
                + "FROM event";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("event_id"));
                e.setEventTitle(rs.getString("event_title"));
                e.setEventDesc(rs.getString("event_desc"));
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setEventLoc(rs.getString("event_loc"));
                e.setBannerImagePath(rs.getString("banner_image_path"));
                e.setQrPath(rs.getString("attendance_qr_path"));
                list.add(e);
            }
        } catch (Exception e) { }
        return list;
    }
    
    // READ ONE (FOR EDIT)
    public Event getEventById(int id) {
        Event e = null;
        String sql = "SELECT * FROM event WHERE event_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    e = new Event();
                    e.setEventID(rs.getInt("event_id"));
                    e.setEventTitle(rs.getString("event_title"));
                    e.setEventDesc(rs.getString("event_desc"));
                    e.setEventDate(rs.getDate("event_date"));
                    e.setEventTime(rs.getTime("event_time"));
                    e.setEventLoc(rs.getString("event_loc"));
                    e.setBannerImagePath(rs.getString("banner_image_path"));
                    e.setQrPath(rs.getString("attendance_qr_path"));
                }
            }
        } catch (Exception ex) { }
        return e;
    }

    // CREATE
    public void createEvent(Event e) throws SQLException {
        String sql =
            "INSERT INTO event(event_title,event_desc,event_date,event_time,event_loc,banner_image_path,attendance_qr_path) "
                + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getEventTitle());
            ps.setString(2, e.getEventDesc());
            ps.setDate(3, e.getEventDate());
            ps.setTime(4, e.getEventTime());
            ps.setString(5, e.getEventLoc());
            ps.setString(6, e.getBannerImagePath());
            ps.setString(7, e.getQrPath());

            ps.executeUpdate();
        }
    }
    
    // UPDATE
    public void updateEvent(Event e) throws SQLException {
        String sql = "UPDATE event SET event_title=?, event_desc=?, event_date=?, event_time=?, event_loc=?, "
                + "banner_image_path=?, attendance_qr_path=? "
                + "WHERE event_id=?";

        try (Connection con = DBConnection.getConnection();
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

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) { }
    }
}
