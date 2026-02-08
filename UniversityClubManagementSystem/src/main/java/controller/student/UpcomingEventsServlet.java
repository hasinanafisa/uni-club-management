/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.EventDAO;
import dao.ClubMemberDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Event;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/upcomingEvents")
public class UpcomingEventsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        //get clubId from club_members table
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());

        List<Event> events = List.of();
        if (clubId != 0) {
            EventDAO eventDAO = new EventDAO();
            events = eventDAO.getEventsByClubId(clubId);
        }

        request.setAttribute("events", events);
        request.getRequestDispatcher("/student/upcomingEvents.jsp")
               .forward(request, response);
    }
}