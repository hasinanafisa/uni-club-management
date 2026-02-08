package controller.admin;

import dao.AnnouncementDAO;
import dao.ClubMemberDAO;
import model.Announcement;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manageAnnouncement")
public class ManageAnnouncementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Safety: must be logged in
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());

        AnnouncementDAO dao = new AnnouncementDAO();
        List<Announcement> announcements = dao.getAnnouncementsByClubId(clubId);

        request.setAttribute("announcements", announcements);
        request.getRequestDispatcher("/admin/manageAnnouncement.jsp").forward(request, response);
    }
}
