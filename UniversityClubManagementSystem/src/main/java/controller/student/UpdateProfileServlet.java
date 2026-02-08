/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 *
 * @author Hasina
 */

package controller.student;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;

@WebServlet("/UpdateProfileServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User student = (User) session.getAttribute("user");

        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UserDAO dao = new UserDAO();

        // Update basic info
        student.setFullName(request.getParameter("fullName"));
        student.setEmail(request.getParameter("email"));
        student.setCourse(request.getParameter("course"));
        student.setFaculty(request.getParameter("faculty"));

        //PROFILE IMAGE UPLOAD]
        Part imagePart = request.getPart("profilePic");

        if (imagePart != null && imagePart.getSize() > 0) {

            String fileName = Paths.get(imagePart.getSubmittedFileName())
                    .getFileName().toString();

            String uploadPath =
                    System.getProperty("user.home")
                    + File.separator + "uni-club-uploads"
                    + File.separator + "profiles";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            File file = new File(uploadDir, fileName);

            // SAFE WRITE METHOD (works in GlassFish)
            imagePart.getInputStream()
                    .transferTo(new java.io.FileOutputStream(file));

            student.setProfileImage(fileName);
            dao.updateProfileImage(student.getUserId(), fileName);
        }

        //PASSWORD UPDATE
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password != null && !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match!");
                request.getRequestDispatcher("/student/myProfile.jsp")
                       .forward(request, response);
                return;
            }

            dao.updatePassword(student.getUserId(), password);
        }
        
        dao.updateProfile(student);

        session.setAttribute("user", student);
        session.setAttribute("success", "Profile updated successfully!");

        response.sendRedirect(request.getContextPath() + "/StudentProfileServlet");
    }
}