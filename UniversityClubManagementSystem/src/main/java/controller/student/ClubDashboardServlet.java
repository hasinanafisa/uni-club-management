package controller.student;

import dao.AnnouncementDAO;
import dao.ClubDAO;
import dao.ClubMemberDAO;
import dao.EventDAO;
import dao.TaskDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Club;
import model.Task;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/clubDashboard")
public class ClubDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO memberDAO = new ClubMemberDAO();
        Integer clubId = memberDAO.getStudentClubId(user.getUserId());

        // Student not in any club
        if (clubId == null) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        // Load club
        ClubDAO clubDAO = new ClubDAO();
        Club club = clubDAO.getClubById(clubId);

        if (club == null) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        // Load tasks
        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getTasksByUser(clubId, user.getUserId());

        // Load counts
        int memberCount = memberDAO.getMemberCount(clubId);

        EventDAO eventDAO = new EventDAO();
        int upcomingEventCount = eventDAO.getUpcomingEventCount(clubId);

        AnnouncementDAO announcementDAO = new AnnouncementDAO();
        int announcementCount = announcementDAO.getAnnouncementCount(clubId);

        // Send data to JSP
        request.setAttribute("club", club);
        request.setAttribute("clubId", clubId);
        request.setAttribute("tasks", tasks);
        request.setAttribute("memberCount", memberCount);
        request.setAttribute("upcomingEventCount", upcomingEventCount);
        request.setAttribute("announcementCount", announcementCount);

        request.getRequestDispatcher("/student/clubDashboard.jsp")
               .forward(request, response);
    }
}
