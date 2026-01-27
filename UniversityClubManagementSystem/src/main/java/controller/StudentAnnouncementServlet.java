/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AnnouncementDAO;
import model.Announcement;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Hasina
 */
@WebServlet(name = "StudentAnnouncementServlet", urlPatterns = {"/StudentAnnouncementServlet"})
public class StudentAnnouncementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AnnouncementDAO dao = new AnnouncementDAO();
        List<Announcement> announcements = dao.getAllAnnouncements();

        request.setAttribute("announcements", announcements);
        request.getRequestDispatcher("student/announcement.jsp").forward(request, response);
    }
}