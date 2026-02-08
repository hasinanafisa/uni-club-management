/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.EventDAO;
import dao.EventRegistrationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Event;
import model.User;
import java.io.IOException;

@WebServlet("/student/eventDetails")
public class EventDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        String eventIdStr = request.getParameter("eventId");
        if (eventIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/student/upcomingEvents");
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);

        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(eventId);

        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/student/upcomingEvents");
            return;
        }

        boolean hasJoined = false;
        if (user != null) {
            EventRegistrationDAO erDAO = new EventRegistrationDAO();
            hasJoined = erDAO.hasJoined(eventId, user.getUserId());
        }

        request.setAttribute("event", event);
        request.setAttribute("hasJoined", hasJoined);

        request.getRequestDispatcher("/student/eventDetails.jsp")
               .forward(request, response);
    }
}
