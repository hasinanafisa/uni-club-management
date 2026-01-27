/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AnnouncementDAO;
import model.Announcement;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hasina
 */
@WebServlet(name = "AnnouncementDetailsServlet", urlPatterns = {"/AnnouncementDetailsServlet"})
public class AnnouncementDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        AnnouncementDAO dao = new AnnouncementDAO();
        Announcement announcement = dao.getAnnouncementById(id);

        request.setAttribute("announcement", announcement);
        request.getRequestDispatcher("student/announcementDetails.jsp").forward(request, response);
    }
}