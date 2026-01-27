/**
 * @izyanie
 * @28/01/2026
 */

package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class SelectRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String selectedRole = request.getParameter("role");

        if ("ADMIN".equals(selectedRole)) {
            session.setAttribute("activeRole", "ADMIN");
            response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
        } else {
            session.setAttribute("activeRole", "STUDENT");
            response.sendRedirect(request.getContextPath() + "/student/home.jsp");
        }
    }
}
