<%-- 
    Document   : eventDetails
    Created on : 2 Jan 2026, 11:30:29 am
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String title = request.getParameter("title");
    String date = request.getParameter("date");
    String status = request.getParameter("status");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= title %> - Event Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<%@include file="/includes/header.jsp" %>

<div class="home-page">
    <div class="home-container">

        <!-- HEADER -->
        <div class="event-header">
            <h1><%= title %></h1>
            <p class="subtitle">Organised by the club</p>
        </div>

        <!-- MAIN CONTENT -->
        <div class="event-details-card">

            <!-- INFO ROW -->
            <div class="event-info-row">
                <div class="event-info">
                    <i class="fa-solid fa-calendar-days"></i>
                    <div>
                        <span>Date</span>
                        <p><%= date %></p>
                    </div>
                </div>

                <div class="event-info">
                    <i class="fa-solid fa-circle-info"></i>
                    <div>
                        <span>Status</span>
                        <p><%= status %></p>
                    </div>
                </div>

                <div class="event-info">
                    <i class="fa-solid fa-location-dot"></i>
                    <div>
                        <span>Location</span>
                        <p>Main Campus Hall</p>
                    </div>
                </div>
            </div>

            <!-- DESCRIPTION -->
            <div class="event-description">
                <h3>📌 About This Event</h3>
                <p>
                    This event is organised by the club to encourage participation
                    and engagement among members. Participants will gain valuable
                    experience, networking opportunities, and hands-on activities.
                </p>
            </div>

                <!-- ACTIONS -->
                <div class="event-actions">
                    <button class="join-btn" onclick="confirmJoin(this)">Join Event</button>
                    <a href="upcomingEvents.jsp" class="back-link">Back to Events</a>
                </div>
            </div>
         </div>
   </div>
        <script>
            function confirmJoin(button) {
                const confirmed = confirm("Are you sure you want to join this event?");

                if (confirmed) {
                    alert("You have successfully joined the event!");

                   // Update button state
                    button.innerText = "Joined";
                    button.classList.remove("join-btn");
                    button.classList.add("active-btn");
                    button.disabled = true;
                }
            }
        </script>
</body>
</html>
