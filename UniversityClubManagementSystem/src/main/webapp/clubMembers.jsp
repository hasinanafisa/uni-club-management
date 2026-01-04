<%-- 
    Document   : clubMembers
    Created on : 1 Jan 2026, 11:09:36 am
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Club Members</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<div class="navbar">
    <div class="logo">🎓 CLUB MEMBERS</div>
    <ul class="nav-links">
        <li><a href="#">Home</a></li>
        <li><a href="#">Events</a></li>
        <li><a href="#">Members</a></li>
        <li><a href="#">Logout</a></li>
    </ul>
</div>

<div class="sidebar">
    <a href="#">Dashboard</a>
    <a href="#">Club Members</a>
    <a href="#">Upcoming Events</a>
    <a href="#">My Profile</a>
</div>

<div class="page-wrapper">
    <div class="container">

        <h1>Club Members</h1>
        <p class="subtitle">List of registered members</p>

        <input type="text" placeholder="Search by name">

        <div class="card-container">
            <div class="card">
                <i>👤</i>
                <p>Ali Abu</p>
                <p>President</p>
            </div>

            <div class="card">
                <i>👤</i>
                <p>Siti Aminah</p>
                <p>Secretary</p>
            </div>

            <div class="card">
                <i>👤</i>
                <p>Ahmad Zaki</p>
                <p>Member</p>
            </div>
        </div>

    </div>
</div>

</body>
</html>

