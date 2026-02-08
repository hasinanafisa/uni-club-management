package controller.student;

import dao.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Club;
import model.User;
import model.Task;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/clubDashboard")
public class ClubDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO memberDAO = new ClubMemberDAO();
        Integer clubId = memberDAO.getStudentClubId(user.getUserId());

        // Not a member → block access
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
        
        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getTasksByUser(clubId, user.getUserId());
        request.setAttribute("tasks", tasks);


        // Load counts
        int memberCount = memberDAO.getMemberCount(clubId);

        EventDAO eventDAO = new EventDAO();
        int upcomingEventCount = eventDAO.getUpcomingEventCount(clubId);

        AnnouncementDAO announcementDAO = new AnnouncementDAO();
        int announcementCount = announcementDAO.getAnnouncementCount(clubId);

        // Send to JSP
        request.setAttribute("club", club);
        request.setAttribute("memberCount", memberCount);
        request.setAttribute("upcomingEventCount", upcomingEventCount);
        request.setAttribute("announcementCount", announcementCount);

        request.getRequestDispatcher("/student/clubDashboard.jsp")
               .forward(request, response);
    }
}