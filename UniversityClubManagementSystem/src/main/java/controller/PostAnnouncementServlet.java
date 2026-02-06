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
@WebServlet("/admin/postAnnouncement")
public class PostAnnouncementServlet extends HttpServlet {

    // SHOW FORM
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());

        EventDAO eventDAO = new EventDAO();
        request.setAttribute("events", eventDAO.getEventsByClubId(clubId));

        request.getRequestDispatcher("/admin/postAnnouncement.jsp")
               .forward(request, response);
    }

    // HANDLE SUBMIT
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());

        EventDAO eventDAO = new EventDAO();
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventDAO.getEventById(eventId);

        // Validate event ownership
        if (event == null || event.getClubId() != clubId) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }

        Announcement a = new Announcement();
        a.setClubId(clubId);
        a.setPostedBy(user.getUserId());
        a.setEventId(eventId);
        a.setTitle(request.getParameter("title"));
        a.setContent(request.getParameter("content"));
        a.setCategory(request.getParameter("category"));

        // Upload directory
        Path uploadDir = Paths.get(
                System.getProperty("user.home"),
                "uni-club-uploads",
                "announcements"
        );
        Files.createDirectories(uploadDir);

        // Image upload
        Part imagePart = request.getPart("imagePath");
        String imageFileName = "default-image.png";
        if (imagePart != null && imagePart.getSize() > 0) {
            imageFileName = Paths.get(imagePart.getSubmittedFileName())
                                 .getFileName()
                                 .toString();
            try (InputStream in = imagePart.getInputStream()) {
                Files.copy(in, uploadDir.resolve(imageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        a.setImagePath(imageFileName);

        // Attachment upload
        Part attachmentPart = request.getPart("attachmentPath");
        String attachmentFileName = "default-attachment.pdf";
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            attachmentFileName = Paths.get(attachmentPart.getSubmittedFileName())
                                      .getFileName()
                                      .toString();
            try (InputStream in = attachmentPart.getInputStream()) {
                Files.copy(in, uploadDir.resolve(attachmentFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        a.setAttachmentPath(attachmentFileName);

        AnnouncementDAO dao = new AnnouncementDAO();

        try {
            dao.postAnnouncement(a);
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to post announcement.");
            request.getRequestDispatcher("/admin/postAnnouncement.jsp")
                   .forward(request, response);
        }
    }
}
