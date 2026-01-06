package com.mycompany.universityclubmanagementsystem.controller;

import com.mycompany.universityclubmanagementsystem.dao.AnnouncementDAO;
import com.mycompany.universityclubmanagementsystem.model.Announcement;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig
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

        // FILE UPLOADS
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

