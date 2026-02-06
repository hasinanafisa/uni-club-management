package controller;

import dao.ClubMemberDAO;
import dao.EventDAO;
import model.Event;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/previewEvent")
public class PreviewEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Safety: must be logged in
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }

        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(eventId);

        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }

        // 🔐 Ownership check (event must belong to user's club)
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int userClubId = cmDAO.getClubIdByUser(user.getUserId());

        if (event.getClubId() != userClubId) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }

        request.setAttribute("event", event);
        request.getRequestDispatcher("/admin/previewEvent.jsp")
               .forward(request, response);
    }
}