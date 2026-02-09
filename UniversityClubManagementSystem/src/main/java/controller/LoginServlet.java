package controller;

import dao.UserDAO;
import dao.ClubMemberDAO;
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
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        
        User user = userDAO.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            String userType = user.getUserType();

            if ("Student".equals(userType)) {
                response.sendRedirect(request.getContextPath() + "/student/home.jsp");
                return;
            }
            
            // LECTURER
            boolean hasClub = cmDAO.hasClubMembership(user.getUserId());

            if (!hasClub) { // Lecturer without club → MUST create club
                response.sendRedirect(request.getContextPath() + "/admin/createClub.jsp"); 
            } else {    // Lecturer with club
                response.sendRedirect(request.getContextPath() + "/admin/home"); 
            }
        } else {
            // Failure: Return to login with error
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}