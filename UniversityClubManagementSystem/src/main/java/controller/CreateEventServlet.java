/**
 * @izyanie
 * @27/12/2025
 */

package controller;

import util.QRCodeUtil;
import dao.ClubMemberDAO;
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

@WebServlet("/admin/createEvent")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class CreateEventServlet extends HttpServlet {
    
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

        request.getRequestDispatcher("/admin/createEvent.jsp").forward(request, response);
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
        
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());
        if (clubId == 0) {
            request.setAttribute("error", "No club found for this user.");
            request.getRequestDispatcher("/admin/createEvent.jsp").forward(request, response);
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
        e.setClubId(clubId);
        e.setCreatedBy(user.getUserId());
        e.setEventTitle(request.getParameter("eventTitle"));
        e.setEventDesc(request.getParameter("eventDesc"));
        e.setEventLoc(request.getParameter("eventLoc"));
        e.setEventDate(Date.valueOf(dateStr));
        e.setEventTime(Time.valueOf(timeStr));
        
        // 📁 Base upload directory
        Path uploadDir = Paths.get(
                System.getProperty("user.home"),
                "uni-club-uploads",
                "events"
        );

        // Ensure directory exists
        Files.createDirectories(uploadDir);

        // Banner image
        Part bannerPart = request.getPart("bannerImagePath");
        String bannerFileName = "default-banner.png";
        if (bannerPart != null && bannerPart.getSize() > 0) {
            bannerFileName = Paths.get(bannerPart.getSubmittedFileName())
                                  .getFileName()
                                  .toString();
            Path target = uploadDir.resolve(bannerFileName);
            try (InputStream in = bannerPart.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        e.setBannerImagePath(bannerFileName);

        /*
        Part qrPart = request.getPart("qrPath");
        String qrFileName = "default-qr.png";

        if (qrPart != null && qrPart.getSize() > 0) {
            qrFileName = Paths.get(qrPart.getSubmittedFileName())
                              .getFileName()
                              .toString();

            Path target = uploadDir.resolve(qrFileName);

            try (InputStream in = qrPart.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }*/
        
        EventDAO dao = new EventDAO();
        try {
            int eventId = dao.createEvent(e);
            
            String qrFileName = "event-" + eventId + ".png";
            e.setQrPath("event-qr/" + qrFileName);
            e.setEventID(eventId);

            // 🔹 Generate QR
            String qrUrl = request.getScheme() + "://" +
                           request.getServerName() + ":" +
                           request.getServerPort() +
                           request.getContextPath() +
                           "/student/registerEvent?eventId=" + eventId;

            Path qrDir = Paths.get(
                System.getProperty("user.home"),
                "uni-club-uploads",
                "events",
                "event-qr"
            );
            Files.createDirectories(qrDir);

            Path qrPath = qrDir.resolve("event-" + eventId + ".png");
            QRCodeUtil.generate(qrUrl, qrPath);

            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Failed to create event.");
            request.getRequestDispatcher("/admin/createEvent.jsp").forward(request, response);
        }
    }
}