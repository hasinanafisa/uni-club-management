<%-- 
    Document   : clubDashboard
    Created on : 27 Dec 2025, 6:39:36 pm
    Author     : Razan, Hasina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Club Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<%@include file="/includes/header.jsp" %>
<!-- CONTENT -->

    <div class="notion-wrapper">

        <h1>Club Overview</h1>
        <p class="subtitle">Everything at a glance</p>

        <!-- STATS -->
        <div class="stats-row">
            <div class="stat-box">
                <span>Members</span>
                <h2>45</h2>
                <a href="clubMembers.jsp" class="btn-text">View Members <i class="fa-solid fa-arrow-right"></i></a>
            </div>
            <div class="stat-box">
                <span>Upcoming Events</span>
                <h2>3</h2>
            </div>
            <div class="stat-box">
                <span>Announcements</span>
                <h2>5</h2>
            </div>
        </div>

        <!-- MAIN GRID -->
        <div class="notion-grid">

            <!-- EVENTS -->
            <div class="panel">
                <h3>📅 Upcoming Events</h3>

                <div class="event-item">
                    <div>
                        <strong>CodeFest 2026</strong>
                        <small>15 Jan · 2:00 PM</small>
                    </div>
                    <span class="badge upcoming">Upcoming</span>
                </div>

                <div class="event-item">
                    <div>
                        <strong>Hackathon Meetup</strong>
                        <small>22 Jan · 10:00 AM</small>
                    </div>
                    <span class="badge ongoing">Ongoing</span>
                </div>
            </div>

            <!-- SIDE -->
            <div class="side-panels">

                <div class="panel">
                    <h3>✅ My Tasks</h3>
                    <ul class="task-list">
                        <li>✔ Prepare slides</li>
                        <li>✔ Confirm venue</li>
                        <li>⏳ Upload poster</li>
                    </ul>
                </div>

                <div class="panel">
                    <h3>📢 Announcements</h3>
                    <ul class="feed-list">
                        <li>Meeting moved to Friday</li>
                        <li>New committee announced</li>
                        <li>Registration closing soon</li>
                    </ul>
                </div>

            </div>
        </div>
    </div>

</body>
</html>
