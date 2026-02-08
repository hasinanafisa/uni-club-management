/**
 * @izyanie
 * @28/01/2026
 */
package controller;

import model.Club;
import model.User;
import dao.ClubDAO;
import dao.ClubMemberDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@MultipartConfig
@WebServlet("/admin/createClub")
public class CreateClubServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Safety: must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Lecturer‑only access
        if (!"Lecturer".equals(user.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String clubName = request.getParameter("clubName");
        String description = request.getParameter("description");
        String mission = request.getParameter("mission");
        String achievements = request.getParameter("achievements");

        if (clubName == null || clubName.isBlank()) {
            request.setAttribute("error", "Club name is required");
            request.getRequestDispatcher("/admin/createClub.jsp")
                   .forward(request, response);
            return;
        }

        Club c = new Club();
        c.setClubName(clubName);
        c.setDescription(description);
        c.setCreatedBy(user.getUserId());
        c.setMission(mission);
        c.setAchievements(achievements);

        // Handle logo upload
        Part logoPart = request.getPart("logoPath");
        String logoFileName = "default-logo.png";

        if (logoPart != null && logoPart.getSize() > 0) {
            logoFileName = Paths.get(logoPart.getSubmittedFileName())
                                .getFileName().toString();

            Path uploadDir = Paths.get(
                    System.getProperty("user.home"),
                    "uni-club-uploads"
            );

            Files.createDirectories(uploadDir);

            Path target = uploadDir.resolve(logoFileName);
            try (InputStream in = logoPart.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        c.setLogoPath(logoFileName);

        try {
            ClubDAO clubDAO = new ClubDAO();
            int clubId = clubDAO.createClubAndReturnId(c);

            ClubMemberDAO cmDAO = new ClubMemberDAO();
            cmDAO.addAdvisor(user.getUserId(), clubId);

            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to create club.");
            request.getRequestDispatcher("/admin/createClub.jsp")
                   .forward(request, response);
        }
    }
}
