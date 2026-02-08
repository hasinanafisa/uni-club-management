<%-- 
    Document   : forgot-password
    Created on : 8 Feb 2026, 3:15:11 pm
    Author     : Hasina
--%>

<%@page import="java.util.Random"%>
<%
    String captcha = (String) session.getAttribute("captcha");
    if (captcha == null) {
        captcha = String.valueOf(1000 + new Random().nextInt(9000));
        session.setAttribute("captcha", captcha);
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="login-page">

<div class="container">
    <h2>Forgot Password</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <% if (request.getAttribute("success") != null) { %>
        <p style="color:green;"><%= request.getAttribute("success") %></p>
    <% } %>

    <form action="ForgotPasswordServlet" method="post">

        <% if (request.getAttribute("showResetSection") == null) { %>

            <!-- STEP 1: EMAIL -->
            <input type="email" name="email"
                   placeholder="Enter email" required>

            <button type="submit" name="action" value="verifyEmail">
                Verify Email
            </button>

        <% } else { %>

            <!-- STEP 2: CAPTCHA + PASSWORD RESET -->
            <div style="
                background:#f1f1f1;
                padding:12px;
                margin:10px 0;
                font-size:22px;
                text-align:center;
                letter-spacing:5px;
                border-radius:6px;
                font-weight:bold;">
                <%= request.getAttribute("captchaText") %>
            </div>

            <input type="text" name="captchaInput"
                   placeholder="Enter captcha" required>

            <input type="password" name="newPassword"
                   placeholder="New password" required>

            <input type="password" name="confirmPassword"
                   placeholder="Confirm password" required>

            <button type="submit" name="action" value="resetPassword">
                Reset Password
            </button>

        <% } %>

    </form>

</div>

</body>
</html>