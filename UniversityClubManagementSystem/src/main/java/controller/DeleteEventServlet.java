/**
 * @izyanie
 * @27/12/2025
 */

package com.mycompany.universityclubmanagementsystem.controller;

import com.mycompany.universityclubmanagementsystem.dao.EventDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventID = Integer.parseInt(request.getParameter("eventID"));

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
