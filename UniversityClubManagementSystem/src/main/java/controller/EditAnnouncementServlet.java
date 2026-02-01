/**
 * @izyanie
 * @30/12/2025
 */

package controller;

import dao.AnnouncementDAO;
import dao.ClubMemberDAO;
import dao.EventDAO;
import model.Announcement;
import model.Event;
import model.User;

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
@WebServlet("/admin/editAnnouncement")
public class EditAnnouncementServlet extends HttpServlet {
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
        
        int eventId = Integer.parseInt(request.getParameter("id"));
        EventDAO evDAO = new EventDAO();
        Event event = evDAO.getEventById(eventId);
        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }

        int announcementId = Integer.parseInt(request.getParameter("id"));
        AnnouncementDAO AnnDAO = new AnnouncementDAO();
        Announcement announcement = AnnDAO.getAnnouncementById(announcementId);
        if (announcement == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        request.setAttribute("announcement", announcement);
        request.setAttribute("event", event);
        request.getRequestDispatcher("/admin/editAnnouncement.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        
        int announcementId = Integer.parseInt(request.getParameter("announcementId"));
        AnnouncementDAO aDAO = new AnnouncementDAO();
        Announcement existing = aDAO.getAnnouncementById(announcementId);
        if (existing == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        EventDAO eDAO = new EventDAO();
        Event event = eDAO.getEventById(existing.getEventId());
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int userClubId = cmDAO.getClubIdByUser(user.getUserId());
        if (event == null || event.getClubId() != userClubId) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }

        Announcement a = new Announcement();
        
        // TEXT FIELDS
        a.setAnnouncementId(announcementId);
        a.setEventId(Integer.parseInt(request.getParameter("eventId")));
        a.setTitle(request.getParameter("title"));
        a.setContent(request.getParameter("content"));
        a.setCategory(request.getParameter("category"));

        // 📁 upload dir (same pattern as createEvent / createClub)
        Path uploadDir = Paths.get(
            System.getProperty("user.home"),
            "uni-club-uploads",
            "announcements"
        );
        Files.createDirectories(uploadDir);
        
        // 🔹 Image
        Part imagePart = request.getPart("imagePath");
        String imageFile = existing.getImagePath();
        if (imagePart != null && imagePart.getSize() > 0) {
            imageFile = Paths.get(imagePart.getSubmittedFileName())
                              .getFileName().toString();
            try (InputStream in = imagePart.getInputStream()) {
                Files.copy(in, uploadDir.resolve(imageFile),
                           StandardCopyOption.REPLACE_EXISTING);
            }
        }
        a.setImagePath(imageFile);

        // 🔹 Attachment
        Part attachmentPart = request.getPart("attachmentPath");
        String attachmentFile = existing.getAttachmentPath();
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            attachmentFile = Paths.get(attachmentPart.getSubmittedFileName())
                              .getFileName().toString();
            try (InputStream in = attachmentPart.getInputStream()) {
                Files.copy(in, uploadDir.resolve(attachmentFile),
                           StandardCopyOption.REPLACE_EXISTING);
            }
        }
        a.setAttachmentPath(attachmentFile);

        try {
            aDAO.updateAnnouncement(a);
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to update announcement.");
            request.setAttribute("announcement", existing);
            request.getRequestDispatcher("admin/editAnnouncement.jsp").forward(request, response);
        }
    }
}
