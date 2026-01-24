<%-- 
    Document   : clubDashboard
    Created on : 27 Dec 2025, 6:39:36 pm
    Author     : Razan
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

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">UniClub</div>
    <ul class="nav-links">
        <li><a href="login.jsp">Logout</a></li>
    </ul>
</div>

<!-- SIDEBAR -->
<div class="sidebar">
    <a href="club-dashboard.jsp"><i class="fa-solid fa-house"></i> Home</a>
    <a href="#"><i class="fa-solid fa-calendar-days"></i> Events</a>
    <a href="#"><i class="fa-solid fa-bullhorn"></i> Announcements</a>
    <a href="#"><i class="fa-solid fa-users"></i> Members</a>
    <a href="#"><i class="fa-solid fa-user"></i> My Profile</a>
</div>

<!-- MAIN CONTENT -->
<div class="home-page">
    <div class="home-container">

        <h1>Club Dashboard</h1>
        <p class="subtitle">Manage your club activities efficiently</p>

        <div class="card-container">

            <!-- UPCOMING EVENTS -->
            <div class="card wide-card">
                <h3><i class="fa-solid fa-calendar-check"></i> Upcoming Events</h3>

                <div class="event-row">
                    <div>
                        <strong>CodeFest 2026</strong><br>
                        📅 15 Jan 2026 | ⏰ 2:00 PM
                    </div>
                    <span class="status upcoming">Upcoming</span>
                    <a href="#" class="event-link">View</a>
                </div><br>

                <div class="event-row">
                    <div>
                        <strong>Hackathon Meetup</strong><br>
                        📅 22 Jan 2026 | ⏰ 10:00 AM
                    </div>
                    <span class="status ongoing">Ongoing</span>
                    <a href="#" class="event-link">View</a>
                </div>
            </div>

            <!-- ANNOUNCEMENTS -->
            <div class="card">
                <h3><i class="fa-solid fa-bullhorn"></i> Announcements</h3>
                <ul>
                    📢 Meeting moved to Friday<br>
                    📢 New committee announced<br>
                    📢 Registration closing soon
                </ul>
            </div>

            <!-- MEMBERS -->
            <div class="card">
                <h3><i class="fa-solid fa-users"></i> Members</h3>
                <p><strong>45</strong> total members</p>
                <p><strong>32</strong> active this month</p>
                <a href="#" class="event-link">View Members</a>
            </div>

            <!-- TASKS -->
            <div class="card">
                <h3><i class="fa-solid fa-list-check"></i> My Tasks</h3>
                <ul>
                    ✔ Prepare event slides <br>
                    ✔ Confirm venue<br>
                    ⏳ Upload event poster
                </ul>
            </div>

        </div>
    </div>
</body>
</html>

