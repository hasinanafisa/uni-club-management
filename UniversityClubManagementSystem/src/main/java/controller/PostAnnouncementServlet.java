/**
 * @izyanie
 * @30/12/2025
 */

package controller;

import dao.AnnouncementDAO;
import model.Announcement;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;

@WebServlet("/admin/postAnnouncement")
public class PostAnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Announcement a = new Announcement();

        // TEXT FIELDS
        a.setAnnounceTitle(request.getParameter("announceTitle"));
        a.setAnnounceContent(request.getParameter("announceContent"));
        a.setAnnounceCategory(request.getParameter("announceCategory"));
        a.setEventID(Integer.parseInt(request.getParameter("eventID")));

        // IMAGE/FILE UPLOADS
        Part imagePart = request.getPart("imagePath");
        Part attachmentPart = request.getPart("attachmentPath");

        // IMAGE
        if (imagePart != null && imagePart.getSize() > 0) {
            a.setImagePath(imagePart.getSubmittedFileName());
        } else {
            a.setImagePath(null); // optional
        }

        // ATTACHMENT (PDF)
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            a.setAttachmentPath(attachmentPart.getSubmittedFileName());
        } else {
            a.setAttachmentPath(null); // optional
        }
        
        String uploadPath = getServletContext().getRealPath("") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName())
                                    .getFileName().toString();

            imagePart.write(uploadPath + File.separator + fileName);
            a.setImagePath(fileName);
        }
        
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            String fileName = Paths.get(attachmentPart.getSubmittedFileName())
                                    .getFileName().toString();

            attachmentPart.write(uploadPath + File.separator + fileName);
            a.setAttachmentPath(fileName);
        }

        AnnouncementDAO dao = new AnnouncementDAO();
        try {
            dao.postAnnouncement(a);
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement.jsp");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to post announcement.");
            request.getRequestDispatcher("admin/postAnnouncement.jsp")
                   .forward(request, response);
        }
    }
}

