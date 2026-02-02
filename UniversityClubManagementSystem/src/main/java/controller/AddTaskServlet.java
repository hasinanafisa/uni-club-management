/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.TaskDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import java.io.IOException;

@WebServlet("/student/addTask")
public class AddTaskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        String title = request.getParameter("title");

        if (title == null || title.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/student/clubDashboard");
            return;
        }

        TaskDAO dao = new TaskDAO();

        try {
            dao.addTask(clubId, user.getUserId(), title);
            response.sendRedirect(request.getContextPath() + "/student/clubDashboard");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/student/clubDashboard");
        }
    }
}