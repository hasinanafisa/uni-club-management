package controller.admin;

import model.Event;
import model.User;
import dao.ClubMemberDAO;
import dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manageEvent")
public class ManageEventServlet extends HttpServlet {

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

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());
        if (clubId == 0) {
            request.setAttribute("events", List.of());
            request.getRequestDispatcher("/admin/manageEvent.jsp").forward(request, response);
            return;
        }
        session.setAttribute("clubId", clubId);

        EventDAO dao = new EventDAO();
        List<Event> events = dao.getEventsByClubId(clubId);

        request.setAttribute("events", events);
        request.getRequestDispatcher("/admin/manageEvent.jsp")
               .forward(request, response);
    }
}