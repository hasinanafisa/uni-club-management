/**
 * @izyanie
 * @30/12/2025
 */

package controller.admin;

import dao.AnnouncementDAO;
import dao.ClubMemberDAO;
import dao.EventDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import model.Announcement;
import model.Event;

@WebServlet("/admin/deleteAnnouncement")
public class DeleteAnnouncementServlet extends HttpServlet {
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

        // 📌 Get announcement ID
        int announcementId;
        try {
            announcementId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }

        AnnouncementDAO dao = new AnnouncementDAO();
        Announcement announcement = dao.getAnnouncementById(announcementId);
        if (announcement == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        // 🔐 Ownership check via EVENT → CLUB
        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(announcement.getEventId());

        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int userClubId = cmDAO.getClubIdByUser(user.getUserId());

        if (event.getClubId() != userClubId) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        // 📁 File cleanup
        Path uploadDir = Paths.get(
            System.getProperty("user.home"),
            "uni-club-uploads",
            "announcements"
        );
        deleteFileIfExists(uploadDir, announcement.getImagePath());
        deleteFileIfExists(uploadDir, announcement.getAttachmentPath());
                
        try {
            dao.deleteAnnouncement(announcementId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
    }
    
    private void deleteFileIfExists(Path dir, String fileName) {
        if (fileName == null || fileName.isBlank() || fileName.startsWith("default")) {
            return;
        }

        File file = dir.resolve(fileName).toFile();
        if (file.exists()) {
            file.delete();
        }
    }
}
