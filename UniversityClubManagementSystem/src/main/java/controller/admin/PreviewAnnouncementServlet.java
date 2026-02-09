package controller.admin;

import dao.AnnouncementDAO;
import dao.ClubMemberDAO;
import dao.EventDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Announcement;
import model.Event;
import model.User;

@WebServlet("/admin/previewAnnouncement")
public class PreviewAnnouncementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (session == null || user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int announcementId;
        try {
            announcementId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }

        AnnouncementDAO announcementDAO = new AnnouncementDAO();
        Announcement announcement = announcementDAO.getAnnouncementById(announcementId);

        if (announcement == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
            return;
        }

        // 🔐 Ownership check via EVENT → CLUB
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int userClubId = cmDAO.getClubIdByUser(user.getUserId());
        
        EventDAO eventDAO = new EventDAO();
        Integer eventId = announcement.getEventId();

        if (eventId != null) {
            Event event = eventDAO.getEventById(eventId);
            if (event == null || event.getClubId() != userClubId) {
                response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
                return;
            }
            request.setAttribute("event", event);
        }        
        request.setAttribute("announcement", announcement);

        request.getRequestDispatcher("/admin/previewAnnouncement.jsp").forward(request, response);
    }
}
