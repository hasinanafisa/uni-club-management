<%-- 
    Document   : clublist
    Created on : 26 Dec 2025, 10:01:26 pm
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
    <title>Club List - University Club Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

    <%@include file="/includes/header.jsp" %>

    <div class="home-page" id="mainPage">
        <div class="home-container">
            <h1>Explore Clubs</h1>
            <p class="subtitle">Join clubs that match your passion</p>

            <div class="card-container">

                <div class="card">
                    <i class="fa-solid fa-code"></i>
                    <h3>Programming Club</h3>
                    <p>Build apps, learn coding, and compete in hackathons.</p>
                    <div class="card-actions">
                        <a href="clubOverview.jsp?clubId=1" class="view-btn">View Details</a>
                        <button class="active-btn">Active</button>
                    </div>
                </div>

                <div class="card">
                    <i class="fa-solid fa-futbol"></i>
                    <h3>Sports Club</h3>
                    <p>Stay active and represent your team.</p>
                    <div class="card-actions">
                        <a href="clubOverview.jsp?clubId=2" class="view-btn">View Details</a>
                        <button class="join-btn">Join</button>
                    </div>
                </div>

                <div class="card">
                    <i class="fa-solid fa-music"></i>
                    <h3>Music Club</h3>
                    <p>Jam, perform, and create music together.</p>
                    <div class="card-actions">
                        <a href="clubOverview.jsp?clubId=3" class="view-btn">View Details</a>
                        <button class="join-btn">Join</button>
                    </div>
                </div>

                <div class="card">
                    <i class="fa-solid fa-paintbrush"></i>
                    <h3>Art Club</h3>
                    <p>Express creativity through art & design.</p>
                    <div class="card-actions">
                        <a href="clubOverview.jsp?clubId=4" class="view-btn">View Details</a>
                        <button class="join-btn">Join</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</body>
</html>