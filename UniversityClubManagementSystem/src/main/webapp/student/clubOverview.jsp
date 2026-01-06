<%-- 
    Document   : clubOverview
    Created on : 27 Dec 2025, 6:10:06 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Club Overview</title>
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
    <a href="club-list.jsp"><i class="fa-solid fa-house"></i> Home</a>
    <a href="club-overview.jsp"><i class="fa-solid fa-users"></i> Club Overview</a>
    <a href="club-dashboard.jsp"><i class="fa-solid fa-people-group"></i> Club Dashboard</a>
    <a href="#"><i class="fa-solid fa-calendar-days"></i> Events</a>
    <a href="#"><i class="fa-solid fa-bullhorn"></i> Announcements</a>
</div>

<!-- MAIN CONTENT -->
<div class="home-page">
    <div class="home-container">

        <h1>Programming Club</h1>
        <p class="subtitle">Innovate. Code. Create.</p>

        <div class="card-container">

            <div class="card">
                <h3>About the Club</h3>
                <p>
                    The Programming Club focuses on coding skills, problem solving,
                    and real-world project development.
                </p>
            </div>

            <div class="card">
                <h3>Mission & Vision</h3>
                <p>
                    Empower students through technology, teamwork, and innovation.
                </p>
            </div>

            <div class="card">
                <h3>Achievements</h3>
                <p>
                    Hackathon winners, national coding competitions, and workshops.
                </p>
            </div>

        </div>

        <div style="text-align:center; margin-top:30px;">
            <button class="join-btn">Join Club</button>
        </div>

    </div>
</div>

</body>
</html>