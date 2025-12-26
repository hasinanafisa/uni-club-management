<%-- 
    Document   : clublist
    Created on : 26 Dec 2025, 10:01:26 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Club List</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">UniClub</div>
    <ul class="nav-links">
        <li><a href="login.jsp">Login</a></li>
    </ul>
</div>

<!-- SIDEBAR -->
<div class="sidebar">
    <a href="#"><i class="fa-solid fa-house"></i> Home</a>
    <a href="#"><i class="fa-solid fa-users"></i> Club List</a>
    <a href="#"><i class="fa-solid fa-people-group"></i> Club Dashboard</a>
    <a href="#"><i class="fa-solid fa-calendar-days"></i> Upcoming Events</a>
    <a href="#"><i class="fa-solid fa-bullhorn"></i> Announcements</a>
</div>

<!-- MAIN CONTENT -->
<div class="home-page">
    <div class="home-container">

        <h1>Explore Clubs</h1>
        <p class="subtitle">Join clubs that match your passion</p>

        <div class="card-container">

            <!-- CLUB CARD -->
            <div class="card">
                <i class="fa-solid fa-code"></i>
                <h3>Programming Club</h3>
                <p>Build apps, learn coding, and compete in hackathons.</p>
                <button class="active-btn">Active</button>
            </div>

            <div class="card">
                <i class="fa-solid fa-futbol"></i>
                <h3>Sports Club</h3>
                <p>Stay active and represent your team.</p>
                <button class="join-btn">Join</button>
            </div>

            <div class="card">
                <i class="fa-solid fa-music"></i>
                <h3>Music Club</h3>
                <p>Jam, perform, and create music together.</p>
                <button class="join-btn">Join</button>
            </div>

            <div class="card">
                <i class="fa-solid fa-paintbrush"></i>
                <h3>Art Club</h3>
                <p>Express creativity through art & design.</p>
                <button class="join-btn">Join</button>
            </div>

        </div>
    </div>
</div>

</body>
</html>