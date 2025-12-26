<%-- 
    Document   : index
    Created on : 26 Dec 2025, 5:57:48 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>UniClub</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<div class="navbar">
    <div class="logo">HOME</div>
    <ul class="nav-links">
        <li><a href="login.jsp">Login</a></li>
    </ul>
</div>

<div class="sidebar">
    <a href="#"><i class="fa-solid fa-house"></i> Home</a>
    <a href="#"><i class="fa-solid fa-users"></i> Club List</a>
    <a href="#"><i class="fa-solid fa-people-group"></i> Club Dashboard</a>
    <a href="#"><i class="fa-solid fa-calendar-days"></i> Upcoming Events</a>
    <a href="#"><i class="fa-solid fa-bullhorn"></i> Announcements</a>
</div>


<div class="home-page">
    <div class="home-container">

        <h1>Welcome to University Club</h1>
        <p class="subtitle">Your campus, your community.</p>

        <div class="card-container">
            <div class="card">
                <i class="fa-solid fa-users"></i>
                <h3>Student Clubs List</h3>
                <p>Connect, grow, and explore exciting clubs made for you.</p>
                <a href="login.jsp">Explore</a>
            </div>

            <div class="card">
                <i class="fa-solid fa-calendar-days"></i>
                <h3>Events</h3>
                <p>Be Part of What’s Next</p>
                <a href="login.jsp">View</a>
            </div>

            <div class="card">
                <i class="fa-solid fa-people-group"></i>
                <h3>Club Dashboard</h3>
                <p>Your club, your space. Let’s make things happen!</p>
                <a href="login.jsp">View</a>
            </div>
            
            <div class="card">
                <i class="fa-solid fa-bullhorn"></i>
                <h3>Announcement</h3>
                <p>Important updates and announcements all in one place.</p>
                <a href="login.jsp">View</a>
            </div>
        </div>

    </div>
</div>

</body>
</html>