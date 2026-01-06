<%-- 
    Document   : upcomingEvents
    Created on : 2 Jan 2026, 09:46:23 am
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upcoming Events</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">📅 UPCOMING EVENTS</div>
    <ul class="nav-links">
        <li><a href="#">Back</a></li>
        <li><a href="#">Logout</a></li>
    </ul>
</div>

<!-- SIDEBAR -->
<div class="sidebar">
    <a href="#">Dashboard</a>
    <a href="#">Events</a>
    <a href="#">My Events</a>
</div>

<!-- MAIN PAGE -->
<div class="page-wrapper">
    <div class="container">

        <h1>Upcoming Events</h1>
        <p class="subtitle">Events organized by the club</p>

        <input type="text" placeholder="Search events">

        <div class="card-container">

            <!-- EVENT CARD 1 -->
            <div class="card">
                <h3>AI Workshop</h3>
                <p>Date: 20 March 2025</p>
                <p>Status: Open</p>
                <a href="#">View Details</a>

                <button class="join-btn"
                        onclick="confirmJoin(this, 'AI Workshop')">
                    Join
                </button>
            </div>

            <!-- EVENT CARD 2 -->
            <div class="card">
                <h3>Charity Run</h3>
                <p>Date: 5 April 2025</p>
                <p>Status: Open</p>
                <a href="#">View Details</a>

                <button class="join-btn"
                        onclick="confirmJoin(this, 'Charity Run')">
                    Join
                </button>
            </div>

        </div>

    </div>
</div>

<!-- JAVASCRIPT -->
<script>
    function confirmJoin(button, eventName) {
        var confirmed = confirm(
            "Are you sure you want to join the event: " + eventName + "?"
        );

        if (confirmed) {
            alert("You have successfully joined " + eventName + "!");

            // Change button state after joining
            button.innerText = "Joined";
            button.classList.remove("join-btn");
            button.classList.add("active-btn");
            button.disabled = true;
        }
    }
</script>

</body>
</html>