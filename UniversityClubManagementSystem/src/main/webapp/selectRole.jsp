<%-- 
    Document   : selectRole
    Created on : 28 Jan 2026, 1:21:59 am
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>

<%
    User user = (User) session.getAttribute("user");

    // Not logged in
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/student/login.jsp");
        return;
    }

    // Only PRESIDENT allowed
    if (!"PRESIDENT".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/student/home.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Select Role</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="login-page">

<div class="container">
    <h2>Select Your View</h2>
    <p style="text-align:center; margin-bottom:20px;">
        You are logged in as <strong>President</strong>
    </p>

    <form action="${pageContext.request.contextPath}/SelectRoleServlet" method="post">
        <button type="submit" name="role" value="STUDENT" style="width:100%; margin-bottom:15px;">
            Student View
        </button>

        <button type="submit" name="role" value="ADMIN" style="width:100%;">
            Admin View
        </button>
    </form>
</div>

</body>
</html>
