/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ClubDAO;
import model.Club;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/club")
public class ClubOverviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String clubIdParam = request.getParameter("clubId");

        // Safety check
        if (clubIdParam == null || clubIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        int clubId;
        try {
            clubId = Integer.parseInt(clubIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        ClubDAO dao = new ClubDAO();
        Club club = dao.getClubById(clubId);

        //If club not found
        if (club == null) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        //Send club to JSP
        request.setAttribute("club", club);
        request.getRequestDispatcher("/student/clubOverview.jsp")
               .forward(request, response);
    }
}
