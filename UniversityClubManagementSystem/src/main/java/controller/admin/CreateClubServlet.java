/**
 * @izyanie
 * @28/01/2026
 */

package controller.admin;

import util.UploadUtil;
import model.Club;
import model.User;
import dao.ClubDAO;
import dao.ClubMemberDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

@MultipartConfig
@WebServlet("/admin/createClub")
public class CreateClubServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Safety: must be logged in
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (session == null || user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 🔐 Lecturer-only access (DB-based, correct)
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
            request.getRequestDispatcher("/admin/createClub.jsp").forward(request, response);
            return;
        }
        
        Club c = new Club();
        c.setClubName(clubName);
        c.setDescription(description);
        c.setCreatedBy(user.getUserId());
        c.setMission(mission);
        c.setAchievements(achievements);
        
        // 📷 Handle logo upload
        Part logoPart = request.getPart("logoPath");
        String logoPath = null;
        try {
            logoPath = UploadUtil.upload(
                    logoPart,
                    "clubs",
                    "default-logo.png"
            );
        } catch (Exception ex) {
            System.getLogger(CreateClubServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        c.setLogoPath(logoPath);

        try {
            ClubDAO clubDAO = new ClubDAO();
            int clubId = clubDAO.createClubAndReturnId(c);

            ClubMemberDAO cmDAO = new ClubMemberDAO();
            cmDAO.addAdvisor(user.getUserId(), clubId);

            response.sendRedirect(request.getContextPath() + "/admin/home");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to create club." + e.getMessage());
            request.getRequestDispatcher("/admin/createClub.jsp").forward(request, response);
        }
    }
}