/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.EventRegistrationDAO;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/student/joinEvent")
public class JoinEventServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        int eventId = Integer.parseInt(request.getParameter("eventId"));

        EventRegistrationDAO dao = new EventRegistrationDAO();

        try {
            boolean joined = dao.joinEvent(eventId, userId);

            if (joined) {
                response.sendRedirect(
                    request.getContextPath()
                    + "/student/eventDetails?eventId=" + eventId + "&joined=success"
                );
            } else {
                response.sendRedirect(
                    request.getContextPath()
                    + "/student/eventDetails?eventId=" + eventId + "&joined=already"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(
                request.getContextPath()
                + "/student/eventDetails?eventId=" + eventId + "&joined=error"
            );
        }
    }
}
