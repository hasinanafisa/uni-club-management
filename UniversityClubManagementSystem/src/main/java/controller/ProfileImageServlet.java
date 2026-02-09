package controller;

import model.User;
import dao.UserDAO;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@WebServlet("/profileImage")
public class ProfileImageServlet extends HttpServlet {

    private static final String PROFILE_DIR =
            System.getProperty("user.home")
            + File.separator + "uni-club-uploads"
            + File.separator + "profiles";

    private static final String DEFAULT_IMAGE =
            "/uploads/default-profile.png";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            serveDefaultImage(request, response);
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            serveDefaultImage(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.getUserById(userId);

        if (user == null || user.getProfileImage() == null) {
            serveDefaultImage(request, response);
            return;
        }

        File imageFile = new File(PROFILE_DIR, user.getProfileImage());

        if (!imageFile.exists()) {
            serveDefaultImage(request, response);
            return;
        }

        String mime = getServletContext().getMimeType(imageFile.getName());
        response.setContentType(mime != null ? mime : "image/png");
        response.setContentLengthLong(imageFile.length());
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream os = response.getOutputStream()) {
            fis.transferTo(os);
        }
    }

    private void serveDefaultImage(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {

        File defaultImg = new File(
                getServletContext().getRealPath(DEFAULT_IMAGE)
        );

        response.setContentType("image/png");
        response.setContentLengthLong(defaultImg.length());

        try (FileInputStream fis = new FileInputStream(defaultImg);
             OutputStream os = response.getOutputStream()) {
            fis.transferTo(os);
        }
    }
}
