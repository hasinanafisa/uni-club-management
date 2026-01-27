/**
 * @izyanie
 * @24/12/2025
 */

package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/adminLogout")
public class AdminLogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {
        
        HttpSession session = req.getSession(false);
        if (session != null) { session.invalidate(); }

        res.sendRedirect(req.getContextPath() + "/admin/adminLogin.jsp?logout=true");
    }
}