/**
 * @izyanie
 * @27/12/2025
 */

package controller;

import dao.EventDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
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

        int eventID = Integer.parseInt(request.getParameter("id"));

        EventDAO dao = new EventDAO();
        try {
            dao.deleteEvent(eventID);
            response.sendRedirect(request.getContextPath() + "/admin/manageEvent.jsp");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to delete event.");
            request.getRequestDispatcher("admin/manageEvent.jsp").forward(request, response);
        }
    }
}
