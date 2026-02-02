package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBUtil;

public class EventRegistrationDAO {

    public boolean isRegistered(int eventId, int userId) {
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

    public void register(int eventId, int userId) {
        String sql = """
            INSERT INTO event_registration (event_id, user_id)
            VALUES (?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            // duplicate registration is safely blocked by UNIQUE constraint
            e.printStackTrace();
        }
    }
}
