/**
 * @izyanie
 * @27/12/2025
 */

package controller;

import dao.EventDAO;
import model.Event;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class EditEventServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Event e = new Event();
        EventDAO dao = new EventDAO();
        
        int eventID = Integer.parseInt(request.getParameter("eventID"));
        
        Event existing = dao.getEventById(eventID);
        
        String dateStr = request.getParameter("eventDate");
        String timeStr = request.getParameter("eventTime");
        
        if (dateStr == null || timeStr == null) {
            throw new ServletException("Date or time missing");
        }
        
        if (timeStr != null && timeStr.length() == 5) {
            timeStr = timeStr + ":00";
        }
        
        // TEXT FIELDS
        e.setEventID(eventID);
        e.setEventTitle(request.getParameter("eventTitle"));
        e.setEventDesc(request.getParameter("eventDesc"));
        e.setEventLoc(request.getParameter("eventLoc"));
        e.setEventDate(Date.valueOf(dateStr));
        e.setEventTime(Time.valueOf(timeStr));
        
        // IMAGE UPLOADS
        Part bannerPart = request.getPart("bannerImagePath");
        if (bannerPart != null && bannerPart.getSize() > 0) {
            e.setBannerImagePath(bannerPart.getSubmittedFileName());
        } else {
            e.setBannerImagePath(existing.getBannerImagePath());
        }

        Part qrPart = request.getPart("qrPath");
        if (qrPart != null && qrPart.getSize() > 0) {
            e.setQrPath(qrPart.getSubmittedFileName());
        } else {
            e.setQrPath(existing.getQrPath());
        }

        try {
            dao.updateEvent(e);
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent.jsp");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to update event.");
            request.getRequestDispatcher("admin/editEvent.jsp").forward(request, response);
        }
    }
}
