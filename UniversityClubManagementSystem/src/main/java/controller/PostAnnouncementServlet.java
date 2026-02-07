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
import java.sql.*;

@MultipartConfig
@WebServlet("/admin/postAnnouncement")
public class PostAnnouncementServlet extends HttpServlet {
    
    // ✅ SHOW FORM
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
        
        // Load events for dropdown (important!)
        EventDAO eventDAO = new EventDAO();
        request.setAttribute("events", eventDAO.getEventsByClubId(
                new ClubMemberDAO().getClubIdByUser(user.getUserId())
        ));
        
        request.getRequestDispatcher("/admin/postAnnouncement.jsp").forward(request, response);
    }
    
    // ✅ HANDLE SUBMIT
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
        
        // 🔐 Get user's club
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());

        // 🔐 Validate event belongs to this club
        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(
                Integer.parseInt(request.getParameter("eventId"))
        );
        if (event == null || event.getClubId() != clubId) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        Announcement a = new Announcement();
        
        // REQUIRED FIELDS
        a.setClubId(clubId);
        a.setPostedBy(user.getUserId());
        a.setEventId(event.getEventID());

        // TEXT FIELDS
        a.setTitle(request.getParameter("title"));
        a.setContent(request.getParameter("content"));
        a.setCategory(request.getParameter("category"));
        a.setEventId(Integer.parseInt(request.getParameter("eventId")));

        // 📁 Base upload directory
        String uploadPath = getServletContext().getRealPath("/uploads/announcements");
        Path uploadDir = Paths.get(uploadPath);
        // Ensure directory exists
        Files.createDirectories(uploadDir);
        
        // IMAGE/FILE UPLOADS
        Part imagePart = request.getPart("imagePath");
        String imageFileName = "default-image.png";
        if (imagePart != null && imagePart.getSize() > 0) {
            imageFileName = Paths.get(imagePart.getSubmittedFileName())
                                  .getFileName()
                                  .toString();
            Path target = uploadDir.resolve(imageFileName);
            try (InputStream in = imagePart.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        a.setImagePath("uploads/announcements/" + imageFileName);
        
        Part attachmentPart = request.getPart("attachmentPath");
        String attachmentFileName = "default-attachment.pdf";
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            attachmentFileName = Paths.get(attachmentPart.getSubmittedFileName())
                                  .getFileName()
                                  .toString();
            Path target = uploadDir.resolve(attachmentFileName);
            try (InputStream in = attachmentPart.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        a.setAttachmentPath("uploads/announcements/" + attachmentFileName);

        AnnouncementDAO dao = new AnnouncementDAO();
        try {
            dao.postAnnouncement(a);
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to post announcement.");
            request.getRequestDispatcher("/admin/postAnnouncement.jsp").forward(request, response);
        }
    }
}