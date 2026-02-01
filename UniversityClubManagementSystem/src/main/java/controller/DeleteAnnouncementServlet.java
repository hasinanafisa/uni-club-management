/**
 * @izyanie
 * @30/12/2025
 */

package controller;

import dao.AnnouncementDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteAnnouncement")
public class DeleteAnnouncementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Safety: must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int announceID = Integer.parseInt(request.getParameter("announceID"));

        AnnouncementDAO dao = new AnnouncementDAO();
        try {
            dao.deleteAnnouncement(announceID);
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement.jsp");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to delete announcement.");
            request.getRequestDispatcher("admin/manageAnnouncementt.jsp").forward(request, response);
        }
    }
}
