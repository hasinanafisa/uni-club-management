/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DBUtil;
import java.sql.*;

public class EventRegistrationDAO {

    public boolean joinEvent(int eventId, int userId) {

        String sql = """
            INSERT INTO event_registration (event_id, user_id)
            VALUES (?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            // UNIQUE constraint violation → already joined
            if (e.getSQLState().startsWith("23")) {
                return false;
            }
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasJoined(int eventId, int userId) {
        String sql = """
            SELECT 1 FROM event_registration
            WHERE event_id = ? AND user_id = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int getParticipantCountByEvent(int eventId) {
        String sql = """
            SELECT COUNT(*) 
            FROM event_registration
            WHERE event_id = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalParticipants() {
        String sql = """
            SELECT COUNT(*)
            FROM EVENT_REGISTRATION
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int countTotalParticipants() {
        String sql = "SELECT COUNT(*) FROM event_registration";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
