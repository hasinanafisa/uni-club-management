package controller.admin;

import dao.ClubMemberDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;

@WebServlet("/admin/manageClubMembers")
public class ManageClubMembersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int clubId = Integer.parseInt(request.getParameter("clubId"));

        ClubMemberDAO clubMemberDAO = new ClubMemberDAO();
        List<User> members = clubMemberDAO.getMembersByClubId(clubId);

        request.setAttribute("clubId", clubId);
        request.setAttribute("members", members);

        // Role of logged-in user inside THIS club
        String clubRole = clubMemberDAO.getUserRole(
                ((User) session.getAttribute("user")).getUserId(),
                clubId
        );

        session.setAttribute("clubRole", clubRole);

        request.getRequestDispatcher("/admin/manageMembers.jsp")
               .forward(request, response);
    }
}
