/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
/**
 *
 * @author Hasina
 */

@WebServlet(name = "StudentProfileServlet", urlPatterns = {"/StudentProfileServlet"})
public class StudentProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User student = (User) session.getAttribute("user");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fileName = student.getProfilePicture();
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("/uploads") + File.separator + fileName;
            File imageFile = new File(uploadPath);

            // Check if file actually exists to avoid NoSuchFileException
            if (imageFile.exists()) {
                byte[] fileContent = Files.readAllBytes(imageFile.toPath());
                String base64 = Base64.getEncoder().encodeToString(fileContent);
                student.setBase64Image("data:image/png;base64," + base64);
            } else {
                student.setBase64Image(null); // Reset if file is missing
            }
        }

        UserDAO dao = new UserDAO();
        request.setAttribute("events", dao.getJoinedEvents(student.getUserId()));

        request.getRequestDispatcher("student/myProfile.jsp").forward(request, response);
    }
}