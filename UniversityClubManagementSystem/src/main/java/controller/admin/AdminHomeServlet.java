package controller.admin;

import model.User;
import dao.ClubMemberDAO;
import dao.EventDAO;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/home")
public class AdminHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        ClubMemberDAO clubMemberDAO = new ClubMemberDAO();
        Integer clubId = clubMemberDAO.getAdvisedClubId(user.getUserId());

        // 🔒 Safety check
        if (clubId == null) {
            request.setAttribute("eventsThisMonth", 0);
            request.setAttribute("totalParticipants", 0);
            request.setAttribute("popularEvent", "N/A");
            request.getRequestDispatcher("/admin/adminHome.jsp").forward(request, response);
            return;
        }

        EventDAO eventDAO = new EventDAO();

        request.setAttribute("eventsThisMonth",eventDAO.countEventsThisMonthByClub(clubId));

        request.setAttribute("totalParticipants",eventDAO.getTotalParticipantsByClub(clubId));

        request.setAttribute("popularEvent",eventDAO.getMostPopularEventByClub(clubId));

        request.getRequestDispatcher("/admin/adminHome.jsp").forward(request, response);
    }
}