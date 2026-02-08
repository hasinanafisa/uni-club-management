/**
 * @izyanie
 * @27/12/2025
 */

package controller.admin;

import util.UploadUtil;
import dao.EventDAO;
import model.Event;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/admin/editEvent")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class EditEventServlet extends HttpServlet {
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

        EventDAO dao = new EventDAO();
        Event event = dao.getEventById(eventId);
        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }
        
        request.setAttribute("event", event);
        request.getRequestDispatcher("/admin/editEvent.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        
        int eventID = Integer.parseInt(request.getParameter("eventID"));
        EventDAO dao = new EventDAO();
        Event existing = dao.getEventById(eventID);
        if (existing == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }
        
        String dateStr = request.getParameter("eventDate");
        String timeStr = request.getParameter("eventTime");
        if (dateStr == null || timeStr == null) {
            throw new ServletException("Date or time missing");
        }
        if (timeStr != null && timeStr.length() == 5) {
            timeStr += ":00";
        }
        
        Event e = new Event();
        
        // TEXT FIELDS
        e.setEventID(eventID);
        e.setEventTitle(request.getParameter("eventTitle"));
        e.setEventDesc(request.getParameter("eventDesc"));
        e.setEventLoc(request.getParameter("eventLoc"));
        e.setEventDate(Date.valueOf(dateStr));
        e.setEventTime(Time.valueOf(timeStr));
        
        Part bannerPart = request.getPart("bannerImagePath");
        Part qrPart = request.getPart("qrPath");

        String bannerPath = existing.getBannerImagePath();
        String qrPath = existing.getQrPath();

        if (bannerPart != null && bannerPart.getSize() > 0) {
            try {
                bannerPath = UploadUtil.upload(
                        bannerPart,
                        "events",
                        "default-banner.png"
                );      
            } catch (Exception ex) {
                System.getLogger(EditEventServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        if (qrPart != null && qrPart.getSize() > 0) {
            try {
                qrPath = UploadUtil.upload(
                        qrPart,
                        "events/event-qr",
                        "default-qr.png"
                );      
            } catch (Exception ex) {
                System.getLogger(EditEventServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        e.setBannerImagePath(bannerPath);
        e.setQrPath(qrPath);

        try {
            dao.updateEvent(e);
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Failed to update event.");
            request.setAttribute("event", existing);
            request.getRequestDispatcher("/admin/editEvent.jsp").forward(request, response);
        }
    }
}
