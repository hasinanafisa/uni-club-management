package controller;

import dao.EventDAO;
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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
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
        request.getRequestDispatcher("/admin/editEvent.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
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

        if (timeStr.length() == 5) {
            timeStr += ":00";
        }

        Event e = new Event();
        e.setEventID(eventID);
        e.setEventTitle(request.getParameter("eventTitle"));
        e.setEventDesc(request.getParameter("eventDesc"));
        e.setEventLoc(request.getParameter("eventLoc"));
        e.setEventDate(Date.valueOf(dateStr));
        e.setEventTime(Time.valueOf(timeStr));

        Path uploadDir = Paths.get(
                System.getProperty("user.home"),
                "uni-club-uploads",
                "events"
        );
        Files.createDirectories(uploadDir);

        // Banner
        Part bannerPart = request.getPart("bannerImagePath");
        String bannerFile = existing.getBannerImagePath();

        if (bannerPart != null && bannerPart.getSize() > 0) {
            bannerFile = Paths.get(bannerPart.getSubmittedFileName())
                    .getFileName().toString();

            try (InputStream in = bannerPart.getInputStream()) {
                Files.copy(in, uploadDir.resolve(bannerFile),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }

        // QR
        Part qrPart = request.getPart("qrPath");
        String qrFile = existing.getQrPath();

        if (qrPart != null && qrPart.getSize() > 0) {
            qrFile = Paths.get(qrPart.getSubmittedFileName())
                    .getFileName().toString();

            try (InputStream in = qrPart.getInputStream()) {
                Files.copy(in, uploadDir.resolve(qrFile),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }

        e.setBannerImagePath(bannerFile);
        e.setQrPath(qrFile);

        try {
            dao.updateEvent(e);
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Failed to update event.");
            request.setAttribute("event", existing);
            request.getRequestDispatcher("/admin/editEvent.jsp")
                   .forward(request, response);
        }
    }
}
