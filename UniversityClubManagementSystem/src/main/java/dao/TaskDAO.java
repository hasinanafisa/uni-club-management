package dao;

import util.DBUtil;
import model.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> getTasksByUser(int clubId, int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = """
            SELECT * FROM task
            WHERE club_id = ? AND user_id = ?
            ORDER BY created_at DESC
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("task_id"));
                t.setClubId(rs.getInt("club_id"));
                t.setUserId(rs.getInt("user_id"));
                t.setTitle(rs.getString("title"));
                t.setStatus(rs.getString("status"));
                t.setCreatedAt(rs.getTimestamp("created_at"));
                tasks.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void addTask(int clubId, int userId, String title) throws SQLException {
        String sql = """
            INSERT INTO task (club_id, user_id, title)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clubId);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.executeUpdate();
        }
    }
    
        public void markTaskDone(int taskId, int userId) throws SQLException {
        String sql = """
            UPDATE task
            SET status = 'Done'
            WHERE task_id = ? AND user_id = ?
        """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

        public void deleteTask(int taskId, int userId) throws SQLException {
        String sql = """
            DELETE FROM task
            WHERE task_id = ? AND user_id = ?
        """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}