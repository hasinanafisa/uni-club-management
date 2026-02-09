package controller.admin;

import dao.ClubMemberDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import java.io.IOException;

@WebServlet("/admin/assignRole")
public class AssignRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        int clubId = Integer.parseInt(request.getParameter("clubId"));
        int targetUserId = Integer.parseInt(request.getParameter("userId"));
        String newRole = request.getParameter("role");

        ClubMemberDAO dao = new ClubMemberDAO();

        // 🔐 Get current user's role in this club
        String myRole = dao.getUserRole(currentUser.getUserId(), clubId);

        // 🔐 RULE 1: ONLY Advisor can assign roles
        if (!"Advisor".equals(myRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        // 🔐 Get target user's current role
        String targetCurrentRole = dao.getUserRole(targetUserId, clubId);

        // 🔐 RULE 2: Advisor role is IMMUTABLE
        if ("Advisor".equals(targetCurrentRole) || "Advisor".equals(newRole)) {
            response.sendRedirect(
                request.getContextPath() + "/admin/manageClubMembers?clubId=" + clubId
            );
            return;
        }

        // 🔐 RULE 3: Prevent self-demotion (extra safety)
        if (currentUser.getUserId() == targetUserId) {
            response.sendRedirect(
                request.getContextPath() + "/admin/manageClubMembers?clubId=" + clubId
            );
            return;
        }

        // ✅ Safe to update
        dao.updateMemberRole(targetUserId, clubId, newRole);

        response.sendRedirect(
            request.getContextPath() + "/admin/manageClubMembers?clubId=" + clubId
        );
    }
}
