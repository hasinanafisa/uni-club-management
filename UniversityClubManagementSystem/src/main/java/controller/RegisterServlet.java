package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import util.DBUtil;

@WebServlet("/RegisterServlet") 
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String userType = request.getParameter("userType"); // STUDENT / LECTURER

        // Password validation
        if (password == null || !password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 2. Database Logic
        try {
            try (Connection con = DBUtil.getConnection()) {
                String sql = "INSERT INTO users (full_name, email, password, user_type) VALUES (?, ?, ?, ?)";
                
                try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, fullName);
                    ps.setString(2, email);
                    ps.setString(3, password);
                    ps.setString(4, userType);
                    ps.executeUpdate();

                    response.sendRedirect(request.getContextPath() + "/login.jsp?msg=registered");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            request.setAttribute("errorMessage", "Email is already registered!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
        catch (IOException | SQLException e) {
            request.setAttribute("errorMessage", "Database Error: " + e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}