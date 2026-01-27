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

            int userId = user.getUserId();

            // 🔍 CHECK club membership FIRST
            boolean hasClub = userDAO.hasClubMembership(userId);

            if (!hasClub) {
                // ❗ No club_member row → advisor must create club
                response.sendRedirect(
                    request.getContextPath() + "/admin/createClub.jsp"
                );
                return;
            }

            // Resolve role AFTER confirming club exists
            String role = userDAO.getUserRole(userId);
            session.setAttribute("role", role);

            if (role == null) {
                // normal member
                response.sendRedirect(
                    request.getContextPath() + "/student/home.jsp?loginSuccess=true"
                );
                return;
            }

            switch (role) {
                case "Advisor":
                    response.sendRedirect(
                        request.getContextPath() + "/admin/adminHome.jsp?loginSuccess=true"
                    );
                    break;

                case "President":
                    response.sendRedirect(
                        request.getContextPath() + "/selectRole.jsp"
                    );
                    break;

                default:
                    response.sendRedirect(
                        request.getContextPath() + "/student/home.jsp?loginSuccess=true"
                    );
                    break;
            }
        } else {
            // Failure: Return to login with error
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}