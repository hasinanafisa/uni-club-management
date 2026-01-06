/**
 * @izyanie
 * @24/12/2025
 */

package com.mycompany.universityclubmanagementsystem.controller;

import jakarta.servlet.http.*;
import java.io.IOException;

public class AdminLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {
        
        HttpSession session = req.getSession(false);
        if (session != null) { session.invalidate(); }

        res.sendRedirect(req.getContextPath() + "/admin/adminLogin.jsp?logout=true");
    }

}