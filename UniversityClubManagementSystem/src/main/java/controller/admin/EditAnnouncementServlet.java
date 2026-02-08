/**
 * @izyanie
 * @30/12/2025
 */

package controller.admin;

import util.UploadUtil;
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
import java.sql.SQLException;
import java.util.List;

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
        
        int announcementId;
        try {
            announcementId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        AnnouncementDAO aDAO = new AnnouncementDAO();
        Announcement announcement = aDAO.getAnnouncementById(announcementId);
        if (announcement == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }
        
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());
        
        // 🔹 Load events for dropdown
        EventDAO eDAO = new EventDAO();
        List<Event> events = eDAO.getEventsByClubId(clubId);
        
        request.setAttribute("announcement", announcement);
        request.setAttribute("events", events);
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
        
        Part imagePart = request.getPart("imagePath");
        Part attachmentPart = request.getPart("attachmentPath");
        
        String imagePath = existing.getImagePath();
        String attachmentPath = existing.getAttachmentPath();
        
        if (imagePart != null && imagePart.getSize() > 0) {
            try {
                imagePath = UploadUtil.upload(
                        imagePart,
                        "announcements",
                        "default-image.png"
                );      
            } catch (Exception ex) {
                System.getLogger(EditAnnouncementServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        a.setImagePath(imagePath);
        
        if (attachmentPart != null && attachmentPart.getSize() > 0) {
            try {
                attachmentPath = UploadUtil.upload(
                        attachmentPart,
                        "announcements",
                        "default-attachment.pdf"
                );      
            } catch (Exception ex) {
                System.getLogger(EditAnnouncementServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        a.setAttachmentPath(attachmentPath);

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
