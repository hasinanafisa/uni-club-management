/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.TaskDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import java.io.IOException;

@WebServlet("/student/taskAction")
public class TaskActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        String action = request.getParameter("action");
        String taskIdStr = request.getParameter("taskId");

        if (action == null || taskIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/student/clubDashboard");
            return;
        }

        int taskId = Integer.parseInt(taskIdStr);
        TaskDAO taskDAO = new TaskDAO();

        try {
            if ("done".equals(action)) {
                taskDAO.markTaskDone(taskId, user.getUserId());
            } else if ("delete".equals(action)) {
                taskDAO.deleteTask(taskId, user.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/student/clubDashboard");
    }
}