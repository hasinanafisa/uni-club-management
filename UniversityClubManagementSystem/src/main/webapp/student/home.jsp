<%-- 
    Document   : home
    Created on : 26 Dec 2025, 7:08:25 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>

<%
    User user = (User) session.getAttribute("user");

    if (user == null || !"Student".equals(user.getUserType())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Home - UniClub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

    <%@include file="/includes/header.jsp" %>

    <div class="home-page" id="mainPage">
        <div class="home-container">
            <h1>WELCOME, <%= user.getFullName().toUpperCase() %></h1>
            <p class="subtitle">Select a category to manage your club activities</p>

            <div class="card-container">
                <div class="card">
                    <i class="fa-solid fa-users-viewfinder"></i>
                    <h3>CLUB LIST</h3>
                    <p>Find new clubs to join this semester.</p>
                    <a href="${pageContext.request.contextPath}/student/clubs">BROWSE</a>
                </div>

                <div class="card">
                    <i class="fa-solid fa-gauge"></i>
                    <h3>DASHBOARD</h3>
                    <p>Check your membership status and roles.</p>
                    <a href="${pageContext.request.contextPath}/student/clubDashboard">VIEW</a>
                </div>

                <div class="card">
                    <i class="fa-solid fa-calendar-days"></i>
                    <h3>EVENTS</h3>
                    <p>See what's happening around campus.</p>
                    <a href="${pageContext.request.contextPath}/student/upcomingEvents">EXPLORE</a>
                </div>

                <div class="card">
                    <i class="fa-solid fa-bell"></i>
                    <h3>NEWS</h3>
                    <p>Read the latest club announcements.</p>
                    <a href="${pageContext.request.contextPath}/student/announcements">READ</a>
                </div>
            </div>
        </div>
    </div>

    <%-- Success Message Logic --%>
    <% if (request.getParameter("loginSuccess") != null) { %>
    <script>
        Swal.fire({
            title: 'Hello, <%= user.getFullName() %>!',
            text: 'You have logged in successfully.',
            icon: 'success',
            timer: 2500,
            showConfirmButton: false
        });
    </script>
    <% } %>

</body>
</html>