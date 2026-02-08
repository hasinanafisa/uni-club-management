/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.AnnouncementDAO;
import dao.ClubMemberDAO;
import model.Announcement;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;

/**
 *
 * @author Hasina
 */
@WebServlet("/student/announcements")
public class StudentAnnouncementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Session check
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
        
        // 📌 Get student's club
        ClubMemberDAO cmDAO = new ClubMemberDAO();
        int clubId = cmDAO.getClubIdByUser(user.getUserId());

        if (clubId == 0) {
            request.setAttribute("error", "You have not joined any club.");
            request.getRequestDispatcher("/student/announcement.jsp")
                   .forward(request, response);
            return;
        }
        
        // 📣 Get announcements
        AnnouncementDAO dao = new AnnouncementDAO();
        List<Announcement> announcements = dao.getAnnouncementsByClubId(clubId);

        request.setAttribute("announcements", announcements);
        request.getRequestDispatcher("student/announcement.jsp").forward(request, response);
    }
}