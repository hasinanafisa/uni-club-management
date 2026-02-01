package controller;

import dao.ClubDAO;
import dao.ClubMemberDAO;
import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Club;
import model.User;

@WebServlet("/admin/manageClubDetails")
public class ManageClubDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        ClubDAO clubDAO = new ClubDAO();
        UserDAO userDAO = new UserDAO();
        
        int userId = user.getUserId();
        int clubId = cmDAO.getClubIdByUser(userId);
        Club club = clubDAO.getClubById(clubId);
        
        List<User> members = userDAO.getMembersByClubId(clubId);

        request.setAttribute("club", club);
        request.setAttribute("members", members);

        request.getRequestDispatcher("/admin/manageClubDetails.jsp")
               .forward(request, response);
    }
}