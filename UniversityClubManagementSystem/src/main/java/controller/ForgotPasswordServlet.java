/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import model.ForgotPasswordBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
/**
 *
 * @author Hasina
 */

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDAO dao = new UserDAO();
        String action = request.getParameter("action");

        // Create bean (MODEL)
        ForgotPasswordBean bean = new ForgotPasswordBean();

        /* STEP 1 — VERIFY EMAIL */
        if ("verifyEmail".equals(action)) {

            String email = request.getParameter("email");
            bean.setEmail(email);

            if (dao.emailExists(bean.getEmail())) {
                session.setAttribute("resetEmail", bean.getEmail());

                String captcha = generateCode();
                session.setAttribute("captcha", captcha);

                request.setAttribute("showResetSection", true);
                request.setAttribute("captchaText", captcha);
            } else {
                request.setAttribute("error", "Email not found.");
            }
        }

        /* STEP 2 — RESET PASSWORD */
        else if ("resetPassword".equals(action)) {

            bean.setNewPassword(request.getParameter("newPassword"));
            bean.setConfirmPassword(request.getParameter("confirmPassword"));

            String captchaInput = request.getParameter("captchaInput");
            String sessionCaptcha =
                    (String) session.getAttribute("captcha");

            if (!sessionCaptcha.equals(captchaInput)) {
                request.setAttribute("error", "Captcha incorrect.");
                request.setAttribute("showResetSection", true);
                request.setAttribute("captchaText", sessionCaptcha);
            }
            else if (!bean.getNewPassword()
                          .equals(bean.getConfirmPassword())) {
                request.setAttribute("error", "Passwords do not match.");
                request.setAttribute("showResetSection", true);
                request.setAttribute("captchaText", sessionCaptcha);
            }
            else {
                String resetEmail =
                        (String) session.getAttribute("resetEmail");

                dao.updatePassword(resetEmail, bean.getNewPassword());

                session.removeAttribute("resetEmail");
                session.removeAttribute("captcha");

                response.sendRedirect("login.jsp");
                return;
            }
        }

        request.getRequestDispatcher("forgot-password.jsp")
               .forward(request, response);
    }
}