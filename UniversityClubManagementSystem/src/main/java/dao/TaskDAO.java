/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Hasina
 */
import model.Task;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // Get tasks for logged-in user
    public List<Task> getTasksByUser(int userId) {
        List<Task> list = new ArrayList<>();

        String sql = "SELECT * FROM task WHERE assigned_to = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("task_id"));
                t.setClubId(rs.getInt("club_id"));
                t.setAssignedTo(rs.getInt("assigned_to"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setDueDate(rs.getDate("due_date"));
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update task status
    public void updateStatus(int taskId, String status) {
        String sql = "UPDATE task SET status = ? WHERE task_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, taskId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}