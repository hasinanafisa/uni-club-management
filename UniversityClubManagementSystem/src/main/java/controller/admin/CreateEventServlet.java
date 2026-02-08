/**
 * @izyanie
 * @27/12/2025
 */

package controller.admin;

import dao.ClubMemberDAO;
import dao.EventDAO;
import model.Event;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import util.UploadUtil;

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

        // Banner image
        Part bannerPart = request.getPart("bannerImagePath");
        String bannerPath = null;
        try {
            bannerPath = UploadUtil.upload(
                    bannerPart,
                    "events",
                    "default-banner.png"
            );
        } catch (Exception ex) {
            System.getLogger(CreateEventServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        e.setBannerImagePath(bannerPath);
        
        // Attendance QR
        Part qrPart = request.getPart("qrPath");
        String qrPath = null;
        try {
            qrPath = UploadUtil.upload(
                    qrPart,
                    "events/event-qr",
                    "default-qr.png"
            );
        } catch (Exception ex) {
            System.getLogger(CreateEventServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        e.setQrPath(qrPath);
        
        EventDAO dao = new EventDAO();
        try {
            dao.createEvent(e);
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Failed to create event.");
            request.getRequestDispatcher("/admin/createEvent.jsp").forward(request, response);
        }
    }
}