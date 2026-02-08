package controller;

import dao.ClubDAO;
import dao.ClubMemberDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Club;
import model.ClubMember;
import model.User;

@WebServlet("/admin/manageClubDetails")
public class ManageClubDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        ClubDAO clubDAO = new ClubDAO();

        int clubId = cmDAO.getClubIdByUser(user.getUserId());
        Club club = clubDAO.getClubById(clubId);

        List<ClubMember> members = cmDAO.getMembersWithJoinDate(clubId);

        request.setAttribute("club", club);
        request.setAttribute("members", members);

        request.getRequestDispatcher("/admin/manageClubDetails.jsp")
               .forward(request, response);
    }
}
