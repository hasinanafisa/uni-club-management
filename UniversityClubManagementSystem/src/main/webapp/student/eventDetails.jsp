<%-- 
    Document   : eventDetails
    Created on : 2 Jan 2026, 11:30:29 am
    Author     : USER
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Event"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.LocalDateTime"%>


<%
    Event event = (Event) request.getAttribute("event");
    boolean hasJoined = request.getAttribute("hasJoined") != null
                        && (Boolean) request.getAttribute("hasJoined");
    
    //Check if event already passed
    LocalDate eventDate = event.getEventDate().toLocalDate();
    LocalTime eventTime = event.getEventTime().toLocalTime();

    LocalDateTime eventDateTime = LocalDateTime.of(eventDate, eventTime);
    LocalDateTime now = LocalDateTime.now();

    boolean isExpired = eventDateTime.isBefore(now);

%>

<!DOCTYPE html>
<html>
<head>
    <title><%= event.getEventTitle() %> | Event Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<%@include file="/includes/header.jsp" %>

<div class="home-page">
    <div class="home-container">

        <!-- BANNER -->
        <div class="event-banner">
            <img src="${pageContext.request.contextPath}/eventImage?id=<%= event.getEventID() %>&type=banner"
                 class="banner-img">
        </div>

        <div class="event-header">
            <h1><%= event.getEventTitle() %></h1>
        </div>

        <!-- SUCCESS MESSAGE -->
        <%
            if (request.getAttribute("success") != null) {
        %>
            <div class="alert-success">
                <%= request.getAttribute("success") %>
            </div>
        <%
            }
        %>

        <!-- ================= DETAILS CARD ================= -->
        <div class="event-details-card">

            <!-- INFO ROW -->
            <div class="event-info-row">

                <div class="event-info">
                    <i class="fa-solid fa-calendar-days"></i>
                    <div>
                        <span>Date</span>
                        <p><%= event.getEventDate() %></p>
                    </div>
                </div>

                <div class="event-info">
                    <i class="fa-solid fa-clock"></i>
                    <div>
                        <span>Time</span>
                        <p><%= event.getEventTime() %></p>
                    </div>
                </div>

                <div class="event-info">
                    <i class="fa-solid fa-location-dot"></i>
                    <div>
                        <span>Location</span>
                        <p><%= event.getEventLoc() %></p>
                    </div>
                </div>

            </div>

            <!-- ================= DESCRIPTION ================= -->
            <div class="event-description">
                <h3>📌 About This Event</h3>
                <p><%= event.getEventDesc() %></p>
            </div>

            <!-- QR CODE (only after joining) -->
            <%
                if (hasJoined) {
            %>
                <div class="event-qr">
                    <h3>Attendance QR Code</h3>
                    <img src="${pageContext.request.contextPath}/eventImage?id=<%= event.getEventID() %>&type=qr"
                         class="qr-img">
                </div>
            <%
                }
            %>

            <!-- ACTIONS -->
            <div class="event-actions">
                <%
                    if (isExpired) {
                %>
                    <button class="joined-btn" disabled>Event Ended</button>

                <%
                    } else if (!hasJoined) {
                %>
                    <form action="${pageContext.request.contextPath}/student/joinEvent" method="post">
                        <input type="hidden" name="eventId" value="<%= event.getEventID() %>">
                        <button type="submit" class="join-btn">Join Event</button>
                    </form>

                <%
                    } else {
                %>
                    <button class="joined-btn" disabled>Joined</button>
                <%
                    }
                %>
            </div>
    </div>
</div>
</body>
</html>

