package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RegisterServlet") 
public class RegisterServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:derby://localhost:1527/uniClub";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "app";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 1. Validation Logic
        if (password == null || !password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            // Forward back to the registration page
            request.getRequestDispatcher("/student/register.jsp").forward(request, response);
            return;
        }

        // 2. Database Logic
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO users (full_name, email, password, role) VALUES (?, ?, ?, 'STUDENT')";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, fullName);
                    ps.setString(2, email);
                    ps.setString(3, password);
                    ps.executeUpdate();
                    
                    // SUCCESS: Go to login page
                    response.sendRedirect(request.getContextPath() + "/student/login.jsp?msg=registered");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            request.setAttribute("errorMessage", "Email is already registered!");
            request.getRequestDispatcher("/student/register.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Database Error: " + e.getMessage());
            request.getRequestDispatcher("/student/register.jsp").forward(request, response);
        }
    }
}