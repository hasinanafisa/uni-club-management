package controller;

import dao.ClubDAO;
import model.Club;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/clubImage")
public class ClubImageServlet extends HttpServlet {

    private static final Path BASE_DIR = Paths.get(
        System.getProperty("user.home"),
        "uni-club-uploads"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int clubId;
        try {
            clubId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ClubDAO dao = new ClubDAO();
        Club club = dao.getClubById(clubId);

        if (club == null || club.getLogoPath() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // logoPath example: "clubs/logo.png"
        Path file = BASE_DIR.resolve(club.getLogoPath());

        if (!Files.exists(file)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(
            getServletContext().getMimeType(file.getFileName().toString())
        );
        response.setContentLengthLong(Files.size(file));

        Files.copy(file, response.getOutputStream());
    }
}
