/**
 * @izyanie
 * @28/01/2026
 */

package controller;

import dao.ClubDAO;
import model.Club;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class CreateClubServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Safety: must be logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/student/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Only advisor allowed
        String role = (String) session.getAttribute("role");
        if (role == null || !"ADVISOR".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
            return;
        }

        ClubDAO clubDAO = new ClubDAO();

        // Prevent duplicate club creation
        if (clubDAO.advisorHasClub(user.getUserId())) {
            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
            return;
        }

        // Read form data
        String clubName = request.getParameter("clubName");
        String description = request.getParameter("description");

        // Basic validation
        if (clubName == null || clubName.isBlank()) {
            request.setAttribute("error", "Club name is required.");
            request.getRequestDispatcher("/admin/createClub.jsp")
                   .forward(request, response);
            return;
        }

        // Create club object
        Club club = new Club();
        club.setClubName(clubName);
        club.setDescription(description);
        club.setLogo(null);          // optional for now
        club.setCreatedBy(user.getUserId());

        try {
            clubDAO.createClub(club);
            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to create club.");
            request.getRequestDispatcher("/admin/createClub.jsp")
                   .forward(request, response);
        }
    }
}
