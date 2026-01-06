<%-- 
    Document   : manageEvent
    Created on : 27 Dec 2025, 8:25:46 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.universityclubmanagementsystem.dao.EventDAO"%>
<%@page import="com.mycompany.universityclubmanagementsystem.model.Event"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Manage Events</title>
        <link rel="stylesheet" href="../css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body>
        <!-- ===== TOAST NOTIFICATION ===== -->
        <div id="toast" class="toast"></div>

        <!-- ===== NAVBAR ===== -->
        <div class="navbar">
            <div style="display:flex; align-items:center;">
                <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
                <div class="logo">EVENT MANAGEMENT</div>
            </div>
            
            <ul class="nav-links">
                <li><a href="adminHome.jsp">Home</a></li>
            </ul>
        </div>

        <!-- ===== SIDEBAR ===== -->
        <div class="sidebar">
            <a href="../admin/adminHome.jsp"><i class="fa-solid fa-house"></i>Home</a>
            <a href="../admin/manageEvent.jsp" class="active-link">
                <i class="fa-solid fa-calendar-days"></i>Manage Event
            </a>
            <a href="../admin/manageAnnouncement.jsp"><i class="fa-solid fa-bullhorn"></i>Manage Announcement</a>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">

                <!-- SEARCH -->
                <div class="search-filter-bar">
                    <div class="search-box">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" placeholder="Search event...">
                        <i class="fa-solid fa-xmark clear-icon"></i>
                    </div>
                </div>

                <%
                    EventDAO dao = new EventDAO();
                    List<Event> events = dao.getAllEvents();
                %>

                <!-- NO EVENT -->
                <% if (events.isEmpty()) { %>
                    <div class="no-event-box">
                        <h3>No event found.</h3>
                    </div>
                <% } else { %>

                <!-- EVENT LIST -->
                    <% for (Event e : events) { %>
                        <%
                            java.time.LocalDate eventDate = e.getEventDate().toLocalDate();
                            java.time.LocalTime eventTime = e.getEventTime().toLocalTime();
                            java.time.LocalDateTime eventDateTime =
                                    java.time.LocalDateTime.of(eventDate, eventTime);

                            java.time.LocalDateTime now = java.time.LocalDateTime.now();

                            String status;
                            if (eventDateTime.isAfter(now)) {
                                status = "Upcoming";
                            } else {
                                status = "Past";
                            }
                        %>

                        <div class="event-card">
                            <div class="event-info">
                                <p>
                                    <strong>Event Name:</strong>
                                    <a href="previewEvent.jsp?id=<%= e.getEventID() %>"
                                       style="text-decoration:none; font-weight:600; color:#1e3a8a;">
                                        <%= e.getEventTitle() %>
                                    </a>
                                </p>
                                <p><strong>Date:</strong> <%= e.getEventDate() %></p>
                                <%
                                    java.time.LocalTime time = e.getEventTime().toLocalTime();
                                    String formattedTime =
                                        time.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));
                                %>
                                <p><strong>Time:</strong> <%= formattedTime %></p>
                                <p><strong>Status:</strong>
                                    <span class="status <%= status.toLowerCase() %>"> <%= status %></span>
                                </p>
                            </div>

                            <div class="event-actions">
                                <a href="editEvent.jsp?id=<%= e.getEventID() %>"
                                   class="icon-btn edit-btn"
                                   title="Edit">
                                    <i class="fa-solid fa-pen"></i>
                                </a>

                                <a href="javascript:void(0)"
                                    class="icon-btn delete-btn"
                                    onclick="confirmDelete(<%= e.getEventID() %>)"
                                    title="Delete">
                                     <i class="fa-solid fa-trash"></i>
                                 </a>
                            </div>
                        </div>
                    <% } %>
                <% } %>

                <!-- CREATE BUTTON -->
                <div class="create-event-wrapper">
                    <a href="createEvent.jsp" class="create-event-btn">
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

            function confirmDelete(eventId) {
                showToast("Event deleted.", "success");

                setTimeout(() => {
                    window.location.href = "<%= request.getContextPath() %>/DeleteEventServlet?id=" + eventId;
                }, 1200);
            }
        </script>
    </body>
</html>