/**
 * @izyanie
 * @24/12/2025
 */

package com.mycompany.universityclubmanagementsystem.controller;

import com.mycompany.universityclubmanagementsystem.dao.AdminDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        AdminDAO dao = new AdminDAO();

        if (dao.login(email, password)) {
            //login success → redirect
            HttpSession session = req.getSession();
            session.setAttribute("admin", email);
            session.setAttribute("loginSuccess", true); //session flag → show popup
            res.sendRedirect(req.getContextPath() + "/admin/adminHome.jsp");
        } else {
            //login failure → stay on login page
            req.setAttribute("error", "Invalid email or password.");
            req.setAttribute("email", email); // 👈 remember email
            req.getRequestDispatcher("/admin/adminLogin.jsp").forward(req, res);
        }
    }
}
