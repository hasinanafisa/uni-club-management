<%-- 
    Document   : header
    Created on : 27 Jan 2026, 2:46:05 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <nav class="navbar">
        <div class="logo">
            <i class="fa-solid fa-bars menu-toggle" id="toggleIcon" style="cursor:pointer; margin-right:15px;"></i>
            UniClub
        </div>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a></li>
        </ul>
    </nav>

    <nav class="sidebar" id="sidebar">
        <a href="home.jsp"><i class="fa-solid fa-house"></i> HOME</a>
        <a href="${pageContext.request.contextPath}/student/clubs"><i class="fa-solid fa-users"></i> CLUB LIST</a>
        <a href="${pageContext.request.contextPath}/student/clubDashboard"><i class="fa-solid fa-chart-line"></i> CLUB DASHBOARD</a>
        <a href="upcomingEvents.jsp"><i class="fa-solid fa-calendar-check"></i> UPCOMING EVENTS</a>
        <a href="announcement.jsp"><i class="fa-solid fa-bullhorn"></i> ANNOUNCEMENT</a>
        <a href="myProfile.jsp"><i class="fa-solid fa-user"></i> MY PROFILE</a>
    </nav>

    <script>
        // Using DOMContentLoaded ensures the script runs even if included at the top
        document.addEventListener('DOMContentLoaded', function() {
            const toggleIcon = document.getElementById('toggleIcon');
            const sidebar = document.getElementById('sidebar');
            const body = document.body;

            if (toggleIcon && sidebar) {
                toggleIcon.addEventListener('click', function() {
                    // Toggles the 'collapsed' class on the sidebar
                    sidebar.classList.toggle('collapsed');
                    // Toggles 'sidebar-collapsed' on body (adjusts main content margin)
                    body.classList.toggle('sidebar-collapsed');
                });
            }
        });
    </script>
</html>
