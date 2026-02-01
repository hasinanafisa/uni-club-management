/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ClubMemberDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;

@WebServlet("/student/clubDashboard")
public class ClubDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        Integer clubId = cmDAO.getStudentClubId(user.getUserId());

        //Student not in any club
        if (clubId == null) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        //Student is member → allow dashboard
        request.setAttribute("clubId", clubId);
        request.getRequestDispatcher("/student/clubDashboard.jsp")
               .forward(request, response);
    }
}