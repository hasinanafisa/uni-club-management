/**
 * @izyanie
 * @27/12/2025
 */

package controller;

import dao.EventDAO;
import model.Event;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;

public class CreateEventServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Event e = new Event();
        
        String dateStr = request.getParameter("eventDate");
        String timeStr = request.getParameter("eventTime");
        
        if (dateStr == null || timeStr == null) {
            throw new ServletException("Date or time missing");
        }
        if (timeStr != null && timeStr.length() == 5) {
            timeStr = timeStr + ":00";
        }

        // TEXT FIELDS
        e.setEventTitle(request.getParameter("eventTitle"));
        e.setEventDesc(request.getParameter("eventDesc"));
        e.setEventLoc(request.getParameter("eventLoc"));
        e.setEventDate(Date.valueOf(dateStr));
        e.setEventTime(Time.valueOf(timeStr));
        
        // IMAGE UPLOADS
        Part bannerPart = request.getPart("bannerImagePath");
        Part qrPart = request.getPart("qrPath");

        // Save file names only
        if (bannerPart != null && bannerPart.getSize() > 0) {
            e.setBannerImagePath(bannerPart.getSubmittedFileName());
        }
        if (qrPart != null && qrPart.getSize() > 0) {
            e.setQrPath(qrPart.getSubmittedFileName());
        }

        // Save file names with default if null
        if (e.getBannerImagePath() == null) {
            e.setBannerImagePath("default-banner.png");
        }
        if (e.getQrPath() == null) {
            e.setQrPath("default-qr.png");
        }
        
        String uploadPath = getServletContext().getRealPath("") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        if (bannerPart != null && bannerPart.getSize() > 0) {
            String fileName = Paths.get(bannerPart.getSubmittedFileName())
                                    .getFileName().toString();

            bannerPart.write(uploadPath + File.separator + fileName);
            e.setBannerImagePath(fileName);
        }
        if (qrPart != null && qrPart.getSize() > 0) {
            String fileName = Paths.get(qrPart.getSubmittedFileName())
                                    .getFileName().toString();

            qrPart.write(uploadPath + File.separator + fileName);
            e.setQrPath(fileName);
        }

        EventDAO dao = new EventDAO();
        try {
            dao.createEvent(e);
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent.jsp");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to create event.");
            request.getRequestDispatcher("admin/createEvent.jsp").forward(request, response);
        }

    }
}
