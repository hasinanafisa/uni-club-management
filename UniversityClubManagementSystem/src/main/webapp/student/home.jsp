<%-- 
    Document   : home
    Created on : 26 Dec 2025, 6:44:08 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    
</head>
<body>

<nav class="navbar">
    <div class="logo">UniClub</div>
    <ul class="nav-links">
        <li><a href="home.jsp">Home</a></li>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="events.jsp">Events</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</nav>

<div class="container">
    <h2>Welcome, <%= user.getFullName() %> 👋</h2>
    <p>Your role: <%= user.getRole() %></p>
</div>

</body>
</html>
