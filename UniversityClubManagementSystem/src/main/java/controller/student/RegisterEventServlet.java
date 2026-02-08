package controller.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import model.User;
import dao.EventRegistrationDAO;

@WebServlet("/student/registerEvent")
public class RegisterEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = user.getUserId();
        int eventId = Integer.parseInt(request.getParameter("eventId"));

        EventRegistrationDAO erDAO = new EventRegistrationDAO();

        /*boolean alreadyRegistered = erDAO.isRegistered(eventId, userId);

        if (!alreadyRegistered) {
            erDAO.register(eventId, userId);
            session.setAttribute("toast", "You have successfully registered!");
            response.sendRedirect(request.getContextPath() + "/student/viewEvents");
        } else {
            request.setAttribute("toast", "You are already registered.");
            response.sendRedirect(request.getContextPath() + "/student/viewEvents");
        */
    }
}
