<%-- 
    Document   : upcomingEvents
    Created on : 2 Jan 2026
    Author     : Razan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Event"%>
<%@page import="java.time.LocalDate"%>

<!DOCTYPE html>
<html>
<head>
    <title>Upcoming Events</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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

            <%
                List<Event> events = (List<Event>) request.getAttribute("events");

                if (events != null && !events.isEmpty()) {
                    LocalDate today = LocalDate.now();

                    for (Event e : events) {
                        LocalDate eventDate = e.getEventDate().toLocalDate();
                        String status = eventDate.isBefore(today) ? "Past" : "Upcoming";
            %>

            <div class="card event-card">
                <h3><%= e.getEventTitle() %></h3>

                <p>
                    Date:
                    <%= e.getEventDate() %>
                </p>

                <p>
                    Status:
                    <span class="<%= status.equals("Upcoming") ? "status-open" : "status-closed" %>">
                        <%= status %>
                    </span>
                </p>

                <a href="${pageContext.request.contextPath}/student/eventDetails?eventId=<%= e.getEventID() %>">
                    <button class="join-btn">View Details</button>
                </a>
            </div>

            <%
                    }
                } else {
            %>

            <!-- NO EVENTS -->
            <div style="width:100%; text-align:center; padding:40px;">
                <i class="fa-solid fa-calendar-xmark" style="font-size:48px; color:#9ca3af;"></i>
                <p style="margin-top:12px;">No upcoming events available.</p>
            </div>

            <%
                }
            %>

        </div>
    </div>
</div>

<!-- JAVASCRIPT -->
<script>
function searchEvents() {
    let input = document.getElementById("searchInput").value.toLowerCase();
    let cards = document.getElementsByClassName("event-card");

    for (let i = 0; i < cards.length; i++) {
        let title = cards[i].getElementsByTagName("h3")[0].innerText.toLowerCase();
        cards[i].style.display = title.includes(input) ? "" : "none";
    }
}
</script>

</body>
</html>