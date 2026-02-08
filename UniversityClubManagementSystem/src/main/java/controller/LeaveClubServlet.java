/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author Hasina
 */
@WebServlet("/LeaveClubServlet")
public class LeaveClubServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User student = (User) session.getAttribute("user");

        if (student != null) {
            UserDAO dao = new UserDAO();
            dao.leaveClub(student.getUserId());

            session.setAttribute("success", "Successfully left club!");
        }

        response.sendRedirect(request.getContextPath() + "/student/clubs");
    }
}