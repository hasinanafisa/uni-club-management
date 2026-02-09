package controller.admin;

import dao.EventDAO;
import dao.EventRegistrationDAO;
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

        EventDAO eventDAO = new EventDAO();

        // 🔢 Events this month
        int eventsThisMonth = eventDAO.countEventsThisMonth();

        // 👥 Total participants
        int totalParticipants = eventDAO.getTotalParticipants();

        // ⭐ Most popular event
        String popularEvent = eventDAO.getMostPopularEvent();

        // Send to JSP
        request.setAttribute("eventsThisMonth", eventsThisMonth);
        request.setAttribute("totalParticipants", totalParticipants);
        request.setAttribute("popularEvent", popularEvent);

        request.getRequestDispatcher("/admin/adminHome.jsp").forward(request, response);
    }
}
