/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.AnnouncementDAO;
import model.Announcement;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hasina
 */
@WebServlet("/student/announcementDetails")
public class AnnouncementDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/student/announcements");
            return;
        }

        AnnouncementDAO dao = new AnnouncementDAO();
        Announcement announcement = dao.getAnnouncementById(id);

        if (announcement == null) {
            response.sendRedirect(request.getContextPath() + "/student/announcements");
            return;
        }

        request.setAttribute("announcement", announcement);
        request.getRequestDispatcher("/student/announcementDetails.jsp")
               .forward(request, response);

    }
}