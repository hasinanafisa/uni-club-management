/**
 * @izyanie
 * @27/12/2025
 */
package controller;

import dao.ClubMemberDAO;
import dao.EventDAO;
import model.User;
import model.Event;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/admin/deleteEvent")
public class DeleteEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Safety: must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        int eventID;
        try {
            eventID = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }

        EventDAO dao = new EventDAO();
        Event event = dao.getEventById(eventID);
        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }

        // Ownership check
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int userClubId = cmDAO.getClubIdByUser(user.getUserId());
        if (event.getClubId() != userClubId) {
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
            return;
        }

        // File cleanup
        Path uploadDir = Paths.get(
                System.getProperty("user.home"),
                "uni-club-uploads",
                "events"
        );

        deleteFileIfExists(uploadDir, event.getBannerImagePath());
        deleteFileIfExists(uploadDir, event.getQrPath());

        try {
            dao.deleteEvent(eventID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
    }

    private void deleteFileIfExists(Path dir, String fileName) {
        if (fileName == null || fileName.isBlank() || fileName.startsWith("default")) {
            return;
        }

        File file = dir.resolve(fileName).toFile();
        if (file.exists()) {
            file.delete();
        }
    }
}
