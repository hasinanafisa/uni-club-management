<%-- 
    Document   : manageEvent
    Created on : 27 Dec 2025, 8:25:46 pm
    Author     : izyanie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Event"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    if (events == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Events</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
    <body>

    <div id="toast" class="toast"></div>

    <div class="navbar">
        <div style="display:flex; align-items:center;">
            <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
            <div class="logo">EVENT MANAGEMENT</div>
        </div>
        <ul class="nav-links">
            <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
        </ul>
    </div>

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin/adminHome.jsp">
            <i class="fa-solid fa-house"></i>Home
        </a>
        <a href="${pageContext.request.contextPath}/admin/manageClubDetails">
            <i class="fa-solid fa-gear"></i>Manage Club Details
        </a>
        <a href="${pageContext.request.contextPath}/admin/manageEvent" class="active-link">
            <i class="fa-solid fa-calendar-days"></i>Manage Event
        </a>
        <a href="${pageContext.request.contextPath}/admin/manageAnnouncement">
            <i class="fa-solid fa-bullhorn"></i>Manage Announcement
        </a>
    </div>

    <div class="home-page">
        <div class="home-container">

            <div class="search-filter-bar">
                <div class="search-box">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" placeholder="Search event...">
                    <i class="fa-solid fa-xmark clear-icon"></i>
                </div>
            </div>

            <% if (events.isEmpty()) { %>
                <div class="no-event-box">
                    <h3>No event found.</h3>
                </div>
            <% } else { %>

            <%
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                for (Event e : events) {

                    java.time.LocalDate eventDate =
                            e.getEventDate() != null
                            ? e.getEventDate().toLocalDate()
                            : java.time.LocalDate.now();

                    java.time.LocalTime eventTime =
                            e.getEventTime() != null
                            ? e.getEventTime().toLocalTime()
                            : java.time.LocalTime.MIDNIGHT;

                    java.time.LocalDateTime eventDateTime =
                            java.time.LocalDateTime.of(eventDate, eventTime);

                    String status =
                            eventDateTime.isAfter(java.time.LocalDateTime.now())
                            ? "Upcoming"
                            : "Past";
            %>

            <div class="event-card">
                <div class="event-info">
                    <p>
                        <strong>Event Name:</strong>
                        <a href="${pageContext.request.contextPath}/admin/previewEvent?id=<%= e.getEventID() %>"
                           style="text-decoration:none; font-weight:600; color:#1e3a8a;">
                            <%= e.getEventTitle() %>
                        </a>
                    </p>
                    <p><strong>Date:</strong> <%= e.getEventDate() %></p>
                    <p><strong>Time:</strong> <%= timeFormat.format(e.getEventTime()) %></p>
                    <p>
                        <strong>Status:</strong>
                        <span class="status <%= status.toLowerCase() %>"><%= status %></span>
                    </p>
                </div>

                <div class="event-actions">
                    <a href="${pageContext.request.contextPath}/admin/editEvent?id=<%= e.getEventID() %>"
                       class="icon-btn edit-btn">
                        <i class="fa-solid fa-pen"></i>
                    </a>

                    <a href="javascript:void(0)"
                       class="icon-btn delete-btn"
                       onclick="confirmDelete(<%= e.getEventID() %>)">
                        <i class="fa-solid fa-trash"></i>
                    </a>
                </div>
            </div>

            <% } } %>

            <div class="create-event-wrapper">
                <a href="${pageContext.request.contextPath}/admin/createEvent"
                   class="create-event-btn">
                    Create New Event
                </a>
            </div>

        </div>
    </div>

    <script>
        window.onload = function () {
            document.querySelector('.sidebar').classList.add('collapsed');
            document.body.classList.add('sidebar-collapsed');
        };

        function toggleSidebar() {
            document.querySelector('.sidebar').classList.toggle('collapsed');
            document.body.classList.toggle('sidebar-collapsed');
        }

        function confirmDelete(eventID) {
            if (!confirm("Are you sure you want to delete this event?")) return;

            window.location.href =
                "<%= request.getContextPath() %>/admin/deleteEvent?id=" + eventID;
        }
    </script>

    </body>
</html>
