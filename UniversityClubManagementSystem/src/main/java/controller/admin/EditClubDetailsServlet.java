package controller.admin;

import dao.ClubDAO;
import model.Club;
import util.UploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/editClubDetails")
@MultipartConfig
public class EditClubDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== SESSION & BASIC SAFETY =====
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ===== READ PARAMETERS =====
        int clubId;
        try {
            clubId = Integer.parseInt(request.getParameter("clubId"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
            return;
        }

        String clubName = request.getParameter("clubName");
        String description = request.getParameter("description");
        String mission = request.getParameter("mission");
        String achievements = request.getParameter("achievements");

        // ===== LOAD EXISTING CLUB =====
        ClubDAO clubDAO = new ClubDAO();
        Club existingClub = clubDAO.getClubById(clubId);

        if (existingClub == null) {
            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
            return;
        }

        // ===== HANDLE LOGO UPLOAD =====
        String logoPath = existingClub.getLogoPath(); // keep old by default

        try {
            Part logoPart = request.getPart("logoPath");

            if (logoPart != null && logoPart.getSize() > 0) {
                logoPath = UploadUtil.upload(
                        logoPart,
                        "clubs",
                        "default-logo.png"
                );
            }

        } catch (Exception ex) {
            throw new ServletException("Error uploading club logo", ex);
        }

        // ===== UPDATE CLUB OBJECT =====
        existingClub.setClubName(clubName);
        existingClub.setDescription(description);
        existingClub.setMission(mission);
        existingClub.setAchievements(achievements);
        existingClub.setLogoPath(logoPath);

        // ===== UPDATE DATABASE =====
        clubDAO.updateClub(existingClub);

        // ===== REDIRECT BACK TO MANAGE PAGE =====
        response.sendRedirect(
                request.getContextPath()
                + "/admin/manageClubDetails?clubId=" + clubId
        );
    }
}
