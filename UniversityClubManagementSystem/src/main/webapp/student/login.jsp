<%-- 
    Document   : login
    Created on : 26 Dec 2025, 5:59:35 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="login-page">

<div class="container">
    <h2><i class="fa-solid fa-user-lock"></i> Login</h2>

    <%-- Success message from Registration redirect --%>
    <% if ("registered".equals(request.getParameter("msg"))) { %>
        <p style="color:green; text-align:center; font-weight:bold;">Registration successful! Please login.</p>
    <% } %>

    <%-- Error message from LoginServlet --%>
    <% 
        String error = (String) request.getAttribute("errorMessage");
        if (error != null) { 
    %>
        <p style="color:red; text-align:center;"><%= error %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>
    
    <p style="text-align:center; margin-top:15px;">
        <a href="register.jsp">Create an account</a> |
        <a href="forgot-password.jsp">Forgot password?</a>
    </p> 
</div>

</body>
</html>