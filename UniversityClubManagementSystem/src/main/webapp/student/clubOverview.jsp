<%-- 
    Document   : clubOverview
    Created on : 27 Dec 2025, 6:10:06 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Club"%>
<%@page import="model.User"%>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Club club = (Club) request.getAttribute("club");
    if (club == null) {
        response.sendRedirect(request.getContextPath() + "/student/clubs");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= club.getClubName() %> | Club Overview</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet"  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

    <%@include file="/includes/header.jsp" %>

    <div class="home-page">
        <div class="home-container">

            <!-- CLUB HEADER -->
            <div style="text-align:center;">
                <h1><%= club.getClubName() %></h1>
                <p class="subtitle">
                    <%= club.getDescription() != null ? club.getDescription() : "No description available." %>
                </p>
            </div>

            <!-- CLUB CONTENT -->
            <div class="card-container">

                <!-- ABOUT -->
                <div class="card">
                    <h3>About the Club</h3>
                    <p>
                        <%= club.getDescription() != null
                                ? club.getDescription()
                                : "This club has no description yet." %>
                    </p>
                </div>

                <!-- MISSION -->
                <div class="card">
                    <h3>Mission</h3>
                    <p>
                        <%= club.getMission() != null && !club.getMission().isBlank()
                                ? club.getMission()
                                : "Mission has not been set yet." %>
                    </p>
                </div>

                <!-- ACHIEVEMENTS -->
                <div class="card">
                    <h3>Achievements</h3>
                    <p>
                        <%= club.getAchievements() != null && !club.getAchievements().isBlank()
                                ? club.getAchievements()
                                : "No achievements recorded yet." %>
                    </p>
                </div>

            </div>
                    
            <!-- JOIN BUTTON -->
            <div style="text-align:center; margin-top:30px;">
                <form action="${pageContext.request.contextPath}/student/joinClub" method="post">
                    <input type="hidden" name="clubId" value="<%= club.getClubId() %>">
                    <button type="submit" class="join-btn">
                        Join Club
                    </button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
