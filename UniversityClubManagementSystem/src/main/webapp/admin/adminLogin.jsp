<%-- 
    Document   : adminLogin
    Created on : 24 Dec 2025, 2:28:33 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Admin Login | University Club Management System</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body class="login-page">
        <%
            if ("true".equals(request.getParameter("logout"))) {
        %>
        <script>
            window.onload = function () {
                showToast("Logged out successfully!");
            };
        </script>
        <%
            }
        %>

        <!-- ===== LOGIN ===== -->
        <div class="container login-card">
            <div class="login-icon">
                <i class="fa-solid fa-user-lock"></i>
            </div>

            <h2>Login</h2>
            <p class="login-subtitle">Please enter your details</p>

            <% if (request.getAttribute("error") != null) { %>
                <p class="login-error">
                    <%= request.getAttribute("error") %>
                </p>
            <% } %>

            <form action="${pageContext.request.contextPath}/admin/adminLogin" method="post">
                <input type="email" name="email" placeholder="Email"
                    value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" 
                    required>
                <div class="password-wrapper">
                    <input type="password" name="password" id="password" class="password-input" placeholder="Password" required>
                    <i class="fa-solid fa-eye" id="togglePassword"></i>
                </div>
                <button type="submit" class="login-btn">Login</button>
            </form>

            <div class="login-links">
                <a href="${pageContext.request.contextPath}/admin/indexAdmin.jsp">Back to Home</a>
                <span>|</span>
                <a href="forgot-password.jsp">Forgot Password?</a>
            </div>
        </div>

        <!-- ===== TOAST NOTIFICATION ===== -->
        <div id="toast" class="toast"></div>

        <script>   
            const togglePassword = document.getElementById("togglePassword");
            const passwordInput = document.getElementById("password");

            togglePassword.addEventListener("click", function () {
                const type = passwordInput.type === "password" ? "text" : "password";
                passwordInput.type = type;
                this.classList.toggle("fa-eye");
                this.classList.toggle("fa-eye-slash");
            });

            function showToast(message, type = "success") {
                const toast = document.getElementById("toast");
                toast.textContent = message;

                toast.className = "toast show";
                if (type === "error") {
                    toast.classList.add("error");
                }

                setTimeout(() => {
                    toast.className = "toast";
                }, 3000);
            }
        </script>

        <% if (request.getAttribute("error") != null) { %>
        <script>
            window.onload = function () {
                showToast("Invalid email or password.", "error");
            };
        </script>
        <% } %>
    </body>
</html>