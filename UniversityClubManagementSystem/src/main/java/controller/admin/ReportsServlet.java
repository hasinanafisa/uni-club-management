package controller.admin;

import dao.EventDAO;
import dao.EventRegistrationDAO;
import dao.ClubMemberDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Event;
import model.User;

import java.io.IOException;
import java.util.*;

@WebServlet("/admin/reports")
public class ReportsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User admin = (User) session.getAttribute("user");

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        Integer clubId = cmDAO.getStudentClubId(admin.getUserId());

        if (clubId == null) {
            response.sendRedirect(request.getContextPath() + "/admin/home");
            return;
        }

        EventDAO eventDAO = new EventDAO();
        EventRegistrationDAO erDAO = new EventRegistrationDAO();

        List<Event> events = eventDAO.getEventsByClubId(clubId);

        // Map<EventTitle, ParticipantCount>
        Map<String, Integer> participationMap = new LinkedHashMap<>();

        for (Event e : events) {
            int count = erDAO.getParticipantCountByEvent(e.getEventID());
            participationMap.put(e.getEventTitle(), count);
        }

        request.setAttribute("events", events);
        request.setAttribute("participationMap", participationMap);

        request.getRequestDispatcher("/admin/reports.jsp").forward(request, response);
    }
}
