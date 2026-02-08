/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.EventDAO;
import dao.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Event;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hasina
 */
@WebServlet("/student/filterEvents")
public class FilterEventsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        User student = (User) session.getAttribute("user");

        String filter = request.getParameter("filter");
        if (filter == null) filter = "All";

        UserDAO dao = new UserDAO();
        List<Event> events = dao.getJoinedEvents(student.getUserId());

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        for (Event e : events) {
            boolean isPast = e.getEventDate().before(today);
            boolean isCurrent = !isPast;

            if ("Past".equals(filter) && !isPast) continue;
            if ("Current".equals(filter) && !isCurrent) continue;

            out.println("<div class='event-row'>");
            out.println("<span>" + e.getEventTitle() + "</span>");
            out.println("<span>" + sdf.format(e.getEventDate()) + "</span>");
            out.println("</div>");
        }
    }
}