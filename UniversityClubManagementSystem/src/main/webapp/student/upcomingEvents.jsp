<%-- 
    Document   : upcomingEvents
    Created on : 2 Jan 2026
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
<%@include file="/includes/header.jsp" %>
<!-- MAIN PAGE -->
<div class="home-page">
    <div class="home-container">
        <h1>Upcoming Events</h1>
        <p class="subtitle">Events organized by the club</p>

        <!-- SEARCH BAR -->
        <div class="search-filter-bar">
            <div class="search-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text"
                       id="searchInput"
                       placeholder="Search events..."
                       onkeyup="searchEvents()">
            </div>
        </div>

        <!-- EVENT CARDS -->
        <div class="card-container">

            <!-- EVENT 1 -->
            <div class="card event-card">
                <h3>AI Workshop</h3>
                <p>Date: 20 March 2025</p>
                <p>Status: Open</p>
                <a href="eventDetails.jsp?title=AI Workshop&date=20 March 2025&status=Open">
                    <button class="join-btn">View Details</button>
                </a>

            </div>

            <!-- EVENT 2 -->
            <div class="card event-card">
                <h3>Charity Run</h3>
                <p>Date: 5 April 2025</p>
                <p>Status: Open</p>
                <a href="eventDetails.jsp?title=Charity Run&date=5 April 2025&status=Open">
                    <button class="join-btn">View Details</button>
                </a>
            </div>

        </div>
    </div>
</div>

    <!-- JAVASCRIPT -->
    <script>
    function searchEvents() {
        let input = document.getElementById("searchInput").value.toLowerCase();
        let cards = document.getElementsByClassName("event-card");

        for (let i = 0; i < cards.length; i++) {
            let title = cards[i]
                .getElementsByTagName("h3")[0]
                .innerText.toLowerCase();

            if (title.includes(input)) {
                cards[i].style.display = "";   // RESET to CSS default
            } else {
                cards[i].style.display = "none";
            }
        }
    }
    </script>
</body>
</html>