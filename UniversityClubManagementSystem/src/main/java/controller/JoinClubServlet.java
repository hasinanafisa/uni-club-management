package controller;

import dao.ClubMemberDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import java.io.IOException;

@WebServlet("/student/joinClub")
public class JoinClubServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        //Must be logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        //Validate clubId
        String clubIdParam = request.getParameter("clubId");
        if (clubIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        int clubId;
        try {
            clubId = Integer.parseInt(clubIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/student/clubs");
            return;
        }

        ClubMemberDAO dao = new ClubMemberDAO();
       
        try {

            // Student can only join ONE club
            if (dao.hasJoinedAnyClub(user.getUserId())) {
                session.setAttribute("joinMessage",
                        "You already joined a club. Leave first before joining another.");
                response.sendRedirect(request.getContextPath() + "/student/clubs");
                return;
            }

            //Already joined this specific club
            if (dao.isMember(user.getUserId(), clubId)) {
                session.setAttribute("joinMessage",
                        "You already joined this club.");
                response.sendRedirect(request.getContextPath()
                        + "/student/clubOverview?clubId=" + clubId);
                return;
            }

            //Join club
            dao.addStudent(user.getUserId(), clubId);

            session.setAttribute("joinMessage",
                    "Successfully joined the club!");

            response.sendRedirect(request.getContextPath()
                    + "/student/clubDashboard");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("joinMessage",
                    "Error joining club. Please try again.");
            response.sendRedirect(request.getContextPath()
                    + "/student/clubs");
        }
    }
}