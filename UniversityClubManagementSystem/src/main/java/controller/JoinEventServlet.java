/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.EventRegistrationDAO;
import dao.EventDAO;
import model.Event;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/student/joinEvent")
public class JoinEventServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Step 1: Check login
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();

        int eventId = Integer.parseInt(request.getParameter("eventId"));

        EventDAO eventDAO = new EventDAO();
        EventRegistrationDAO regDAO = new EventRegistrationDAO();

        try {

            // Step 2: Fetch event details
            Event event = eventDAO.getEventById(eventId);

            if (event == null) {
                response.sendRedirect(request.getContextPath()
                        + "/student/upcomingEvents?error=EventNotFound");
                return;
            }

            // Step 3: Check if event is already past
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            LocalDate eventDate = event.getEventDate().toLocalDate();
            LocalTime eventTime = event.getEventTime().toLocalTime();

            boolean eventPassed =
                    eventDate.isBefore(today) ||
                    (eventDate.isEqual(today) && eventTime.isBefore(now));

            if (eventPassed) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/student/eventDetails?eventId=" + eventId
                        + "&joined=expired"
                );
                return;
            }

            // Step 4: Join event only if still valid
            boolean joined = regDAO.joinEvent(eventId, userId);

            if (joined) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/student/eventDetails?eventId=" + eventId
                        + "&joined=success"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath()
                        + "/student/eventDetails?eventId=" + eventId
                        + "&joined=already"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(
                    request.getContextPath()
                    + "/student/eventDetails?eventId=" + eventId
                    + "&joined=error"
            );
        }
    }
}