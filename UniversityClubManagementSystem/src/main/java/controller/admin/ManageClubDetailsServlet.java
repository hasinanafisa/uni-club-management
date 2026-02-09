package controller.admin;

import dao.ClubDAO;
import dao.ClubMemberDAO;
import model.Club;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manageClubDetails")
public class ManageClubDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Session check FIRST
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        ClubMemberDAO cmDAO = new ClubMemberDAO();
        ClubDAO clubDAO = new ClubDAO();

        int userId = user.getUserId();
        int clubId = cmDAO.getClubIdByUser(userId);

        Club club = clubDAO.getClubById(clubId);

        // 🔽 sort option (ONLY for manageClubDetails)
        String sort = request.getParameter("sort");

        // ✅ Correct DAO call
        List<User> members = cmDAO.getMembersByClubId(clubId, sort);

        request.setAttribute("club", club);
        request.setAttribute("members", members);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("/admin/manageClubDetails.jsp").forward(request, response);
    }
}
