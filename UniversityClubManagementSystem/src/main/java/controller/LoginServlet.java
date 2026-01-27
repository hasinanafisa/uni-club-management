package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(email, password);

        if (user != null) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        // Add ?loginSuccess=true to the URL
        response.sendRedirect(request.getContextPath() + "/student/home.jsp?loginSuccess=true");
    } else {
            // Failure: Return to login with error
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("/student/login.jsp").forward(request, response);
        }
    }
}