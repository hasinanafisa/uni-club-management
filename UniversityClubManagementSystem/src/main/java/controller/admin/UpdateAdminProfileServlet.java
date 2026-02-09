package controller.admin;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/UpdateAdminProfileServlet")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5
)
public class UpdateAdminProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"Lecturer".equals(user.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UserDAO dao = new UserDAO();

        user.setFullName(request.getParameter("fullName"));
        user.setFaculty(request.getParameter("faculty"));

        // IMAGE
        Part imagePart = request.getPart("profilePic");
        if (imagePart != null && imagePart.getSize() > 0) {

            String original = Paths.get(imagePart.getSubmittedFileName())
                    .getFileName().toString();

            String extension = original.substring(original.lastIndexOf('.'));
            String fileName = "profile_" + user.getUserId() + "_" + System.currentTimeMillis() + extension;

            String uploadPath = System.getProperty("user.home")
                    + File.separator + "uni-club-uploads"
                    + File.separator + "profiles";

            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, fileName);
            imagePart.getInputStream()
                     .transferTo(new java.io.FileOutputStream(file));

            user.setProfileImage(fileName);
            dao.updateProfileImage(user.getUserId(), fileName);
        }

        // PASSWORD
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        if (password != null && !password.isBlank()) {
            if (!password.equals(confirm)) {
                request.setAttribute("error", "Passwords do not match.");
                request.getRequestDispatcher("/admin/myProfile.jsp")
                       .forward(request, response);
                return;
            }
            dao.updatePassword(user.getUserId(), password);
        }

        dao.updateProfile(user);
        session.setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/admin/myProfile");
    }
}
