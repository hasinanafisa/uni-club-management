<%-- 
    Document   : register
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
        <!-- USER TYPE TABS -->
        <div class="role-tabs">
            <button type="button"
                    class="role-tab active"
                    onclick="selectRole('Student', this)">
                Student
            </button>

            <button type="button"
                    class="role-tab"
                    onclick="selectRole('Lecturer', this)">
                Lecturer
            </button>
        </div>
        <input type="hidden" name="userType" id="userType" value="STUDENT">
        <input type="text" name="fullname" placeholder="Full Name" 
               value="<%= request.getParameter("fullname") != null ? request.getParameter("fullname") : "" %>" required>
        <input type="email" name="email" placeholder="Email Address" 
               value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
        <button type="submit">Register</button>
    </form>

    <p style="text-align:center; margin-top:15px;">
        Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Login here</a>
    </p>
</div>
<script>
function selectRole(role, btn) {
    // update hidden input
    document.getElementById("userType").value = role;

    // remove active from all tabs
    document.querySelectorAll(".role-tab")
        .forEach(tab => tab.classList.remove("active"));

    // activate clicked tab
    btn.classList.add("active");
}
</script>
</body>
</html>
