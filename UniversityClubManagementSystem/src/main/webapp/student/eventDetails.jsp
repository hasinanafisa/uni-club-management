<%-- 
    Document   : eventDetails
    Created on : 2 Jan 2026, 11:30:29 am
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Event Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">📄 EVENT DETAILS</div>
    <ul class="nav-links">
        <li><a href="upcomingEvents.jsp">Back</a></li>
        <li><a href="#">Logout</a></li>
    </ul>
</div>

<!-- SIDEBAR -->
<div class="sidebar">
    <a href="#">Upcoming Events</a>
    <a href="#">Photo Gallery</a>
    <a href="#">Feedback</a>
    <a href="#">Download Certificate</a>
</div>

<!-- MAIN PAGE -->
<div class="page-wrapper">
    <div class="container">

        <h1>AI Workshop 2025</h1>
        <p class="subtitle">Complete event information</p>

        <div class="card-container">
            <div class="card" style="width:100%; max-width:850px;">

                <!-- BANNER IMAGE -->
                <img src="images/event-banner.jpg"
                     alt="Event Banner"
                     style="width:100%; border-radius:12px; margin-bottom:20px;">

                <p><strong>Date:</strong> 20 March 2025</p>
                <p><strong>Time:</strong> 9:00 AM – 5:00 PM</p>
                <p><strong>Location:</strong> Main Hall</p>
                <p><strong>Status:</strong> Registration Open</p>

                <hr style="margin:20px 0;">

                <h3>Event Description</h3>
                <p>
                    This AI Workshop introduces students to Artificial Intelligence concepts,
                    hands-on machine learning activities, and real-world applications.
                </p>

                <h3>Event Flow</h3>
                <ul style="text-align:left;">
                    <li>Registration</li>
                    <li>Opening Ceremony</li>
                    <li>Hands-on Workshop</li>
                    <li>Group Activity</li>
                    <li>Certificate Distribution</li>
                </ul>

                <button class="join-btn">Join Event</button>

            </div>
        </div>

    </div>
</div>

</body>
</html>
