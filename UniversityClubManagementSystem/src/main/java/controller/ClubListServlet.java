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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/clubs")
public class ClubListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ClubDAO dao = new ClubDAO();
            List<Club> clubs = dao.getAllClubs();

            request.setAttribute("clubs", clubs);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("clubs", List.of());
        }

        request.getRequestDispatcher("/student/clublist.jsp")
               .forward(request, response);
    }
}
