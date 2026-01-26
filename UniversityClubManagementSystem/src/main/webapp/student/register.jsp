<%-- 
    Document   : login
    Created on : 26 Dec 2025, 6:17:09 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | University Club</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="login-page">

<div class="container">
    <h2><i class="fa-solid fa-user-plus"></i> Create Account</h2>

    <%-- Error Message --%>
    <%
        String error = (String) request.getAttribute("errorMessage");
        if (error != null) {
    %>
        <p class="login-error" style="color:red; text-align:center;"><%= error %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
        <input type="text" name="fullname" placeholder="Full Name" 
               value="<%= request.getParameter("fullname") != null ? request.getParameter("fullname") : "" %>" required>
        <input type="email" name="email" placeholder="Email Address" 
               value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
        <button type="submit">Register</button>
    </form>

    <p style="text-align:center; margin-top:15px;">
        Already have an account? <a href="${pageContext.request.contextPath}/student/login.jsp">Login here</a>
    </p>
</div>

</body>
</html>
