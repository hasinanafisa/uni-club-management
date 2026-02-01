<%-- 
    Document   : manageAnnouncement
    Created on : 30 Dec 2025, 12:32:48 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="dao.AnnouncementDAO"%>
<%@page import="model.Announcement"%>
<%@page import="dao.EventDAO"%>
<%@page import="model.Event"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");

    if (announcements == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Manage Announcements</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body>
        <!-- ===== TOAST NOTIFICATION ===== -->
        <div id="toast" class="toast"></div>

        <!-- ===== NAVBAR ===== -->
        <div class="navbar">
            <div style="display:flex; align-items:center;">
                <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
                <div class="logo">ANNOUNCEMENT MANAGEMENT</div>
            </div>

            <ul class="nav-links">
                <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
            </ul>
        </div>

        <!-- ===== SIDEBAR ===== -->
        <div class="sidebar">
            <a href="${pageContext.request.contextPath}/admin/adminHome.jsp"><i class="fa-solid fa-house"></i>Home</a>
            <a href="${pageContext.request.contextPath}/admin/manageClubDetails"><i class="fa-solid fa-gear"></i>Manage Club Details</a>
            <a href="${pageContext.request.contextPath}/admin/manageEvent"><i class="fa-solid fa-calendar-days"></i>Manage Event</a>
            <a href="${pageContext.request.contextPath}/admin/manageAnnouncement" class="active-link">
                <i class="fa-solid fa-bullhorn"></i>Manage Announcement
            </a>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">

                <div class="search-filter-bar">

                    <!-- SEARCH BOX -->
                    <div class="search-box">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" placeholder="Search announcement...">
                        <i class="fa-solid fa-xmark clear-icon"></i>
                    </div>

                    <!-- FILTER -->
                    <div class="filter-box">
                        <button class="filter-btn">
                            <i class="fa-solid fa-filter"></i>
                            Filter
                        </button>

                        <div class="filter-menu">
                            <a href="?category=Important">Important</a>
                            <a href="?category=General">General</a>
                        </div>
                    </div>
                </div>
                
                <%
                    EventDAO eventDao = new EventDAO();
                    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                %>

                <!-- EMPTY STATE -->
                <%  if (announcements.isEmpty()) { %>
                <div class="empty-box"><p>No announcement found.</p></div>
                
                <%
                    } else {
                        for (Announcement a : announcements) {
                        Event ev = eventDao.getEventById(a.getEventId());
                %>
                <div class="announcement-card">

                    <!-- LEFT -->
                    <div class="announce-category-badge <%= a.getCategory().toLowerCase() %>">
                        <%= a.getCategory() %>
                    </div>

                    <div class="announcement-content">
                        <p>
                            <strong>Posted at:</strong> <%= a.getPostedAt() %>
                        </p>
                        <p>
                            <strong>Title:</strong>
                            <a href="previewAnnouncement?id=<%= a.getAnnouncementId() %>"
                               style="text-decoration:none; font-weight:600; color:#1e3a8a;">
                                <%= a.getTitle() %>
                            </a>
                        </p>
                        <% if (ev != null) { %>
                            <p>
                                <strong>Event:</strong> <%= ev.getEventTitle() %><br>
                                <strong>Date:</strong> <%= ev.getEventDate() %><br>
                                <strong>Time:</strong> <%= timeFormat.format(ev.getEventTime()) %>
                            </p>
                        <% } %>
                    </div>

                    <!-- ACTION BUTTONS (RIGHT) -->
                    <div class="event-actions">
                        <a href="${pageContext.request.contextPath}/admin/editAnnouncement?id=<%= a.getAnnouncementId() %>"
                           class="icon-btn edit-btn">
                            <i class="fa-solid fa-pen"></i>
                        </a>

                        <a href="javascript:void(0)"
                            class="icon-btn delete-btn"
                            onclick="confirmDelete(<%= a.getAnnouncementId() %>)"
                            title="Delete">
                             <i class="fa-solid fa-trash"></i>
                        </a>
                    </div>

                </div>

                <%
                        } // end for
                    } // end else
                %>

                <!-- CREATE BUTTON -->
                <div class="create-btn-wrapper">
                    <a href="${pageContext.request.contextPath}/admin/postAnnouncement" class="wide-btn">
                        Post New Announcement
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
    
        function confirmDelete(announcementId) {
                if (!confirm("Are you sure you want to delete this event?")) {
                    return;
                }

                window.location.href =
                    "<%= request.getContextPath() %>/admin/deleteAnnouncement?id=" + announcementId;
            }
        </script>
    </body>
</html>