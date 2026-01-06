package com.mycompany.universityclubmanagementsystem.controller;

import com.mycompany.universityclubmanagementsystem.dao.AnnouncementDAO;
import com.mycompany.universityclubmanagementsystem.model.Announcement;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig
public class EditAnnouncementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AnnouncementDAO dao = new AnnouncementDAO();
        Announcement a = new Announcement();

        int announceID = Integer.parseInt(request.getParameter("announceID"));
        Announcement existing = dao.getAnnouncementById(announceID);

        a.setAnnounceID(announceID);
        a.setAnnounceTitle(request.getParameter("announceTitle"));
        a.setAnnounceContent(request.getParameter("announceContent"));
        a.setAnnounceCategory(request.getParameter("announceCategory"));
        a.setEventID(Integer.parseInt(request.getParameter("eventID")));

        // IMAGE
        Part imagePart = request.getPart("imagePath");
        if (imagePart != null && imagePart.getSize() > 0) {
            a.setImagePath(imagePart.getSubmittedFileName());
        } else {
            a.setImagePath(existing.getImagePath());
        }

        // ATTACHMENT
        Part attachmentPart = request.getPart("attachmentPath");
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            a.setAttachmentPath(attachmentPart.getSubmittedFileName());
        } else {
            a.setAttachmentPath(existing.getAttachmentPath());
        }

        try {
            dao.updateAnnouncement(a);
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement.jsp");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to update announcement.");
            request.getRequestDispatcher("admin/postAnnouncement.jsp").forward(request, response);
        }
    }
}
