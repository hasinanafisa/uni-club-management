/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.EventDAO;
import model.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

@WebServlet("/eventImage")
public class EventImageServlet extends HttpServlet {

    private static final Path IMAGE_DIR = Paths.get(
            System.getProperty("user.home"),
            "uni-club-uploads",
            "events"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String eventIdStr = request.getParameter("id");
        String type = request.getParameter("type"); // banner | qr

        if (eventIdStr == null || type == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);
        Event event = new EventDAO().getEventById(eventId);

        if (event == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fileName =
                "banner".equals(type) ? event.getBannerImagePath()
              : "qr".equals(type)     ? event.getQrPath()
              : null;

        if (fileName == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path file = IMAGE_DIR.resolve(fileName);
        if (!Files.exists(file)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(getServletContext().getMimeType(fileName));
        response.setContentLengthLong(Files.size(file));

        Files.copy(file, response.getOutputStream());
    }
}