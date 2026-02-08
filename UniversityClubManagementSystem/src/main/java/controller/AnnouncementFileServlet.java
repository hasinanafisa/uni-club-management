package controller;

import dao.AnnouncementDAO;
import model.Announcement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/announcementFile")
public class AnnouncementFileServlet extends HttpServlet {

    private static final Path BASE_DIR = Paths.get(
        System.getProperty("user.home"),
        "uni-club-uploads"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String type = request.getParameter("type"); // image | attachment

        if (idParam == null || type == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int announcementId;
        try {
            announcementId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        AnnouncementDAO dao = new AnnouncementDAO();
        Announcement a = dao.getAnnouncementById(announcementId);

        if (a == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String relativePath;
        if ("image".equals(type)) {
            relativePath = a.getImagePath();
        } else if ("attachment".equals(type)) {
            relativePath = a.getAttachmentPath();
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (relativePath == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path file = BASE_DIR.resolve(relativePath);

        if (!Files.exists(file)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(
            getServletContext().getMimeType(file.getFileName().toString())
        );
        response.setContentLengthLong(Files.size(file));

        // Force download for attachments
        if ("attachment".equals(type)) {
            response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"" + file.getFileName().toString() + "\""
            );
        }

        Files.copy(file, response.getOutputStream());
    }
}
