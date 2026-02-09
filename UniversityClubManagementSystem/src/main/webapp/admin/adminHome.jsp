<%-- 
    Document   : adminHome
    Created on : 24 Dec 2025, 3:13:20 pm
    Author     : izyanie
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"Lecturer".equals(user.getUserType())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Boolean loginSuccess = (Boolean) session.getAttribute("loginSuccess");
    if (loginSuccess != null && loginSuccess) {
        session.removeAttribute("loginSuccess");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Admin Home | University Club Management System</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body>
        <div class="navbar">
            <div style="display:flex; align-items:center;">
                <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
                <div class="logo">HOME</div>
            </div>
            <ul class="nav-links">
                <li><a href="<%= request.getContextPath() %>/admin/myProfile">My Profile</a></li>
                <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
            </ul>
        </div>

        <div class="sidebar">
            <a href="${pageContext.request.contextPath}/admin/home" class="active-link">
                <i class="fa-solid fa-house"></i>Home
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageClubDetails">
                <i class="fa-solid fa-gear"></i>Manage Club Details
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageEvent">
                <i class="fa-solid fa-calendar-days"></i>Manage Event
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageAnnouncement">
                <i class="fa-solid fa-bullhorn"></i>Manage Announcement
            </a>
        </div>

        <!-- MAIN CONTENT -->
        <div class="home-page">
            <div class="home-container">

                <h1>HEY, <%= user.getFullName().toUpperCase() %>!</h1>
                <h1>Welcome Back to University Club Management System</h1>
                <p class="subtitle">Select what to manage for your club today</p>

                <!-- CLUB INSIGHTS (LIGHTWEIGHT, INLINE) -->
                <div class="club-insights">
                    <h2 class="section-title">📊 Club Insights</h2>

                    <div class="insight-row">
                        <div class="insight-item">
                            <div class="insight-label">Events This Month</div>
                            <div class="insight-value">${eventsThisMonth}</div>
                        </div>

                        <div class="insight-item">
                            <div class="insight-label">Total Participants</div>
                            <div class="insight-value">${totalParticipants}</div>
                        </div>

                        <div class="insight-item">
                            <div class="insight-label">Most Popular Event</div>
                            <div class="insight-value">
                                ${empty popularEvent ? "—" : popularEvent}
                            </div>
                        </div>
                    </div>

                    <a class="report-link"
                       href="${pageContext.request.contextPath}/admin/reports">
                        View Detailed Reports →
                    </a>
                </div>

                <!-- ACTION CARDS -->
                <div class="card-container">
                    <div class="card">
                        <i class="fa-solid fa-gear"></i>
                        <h3>Club Details</h3>
                        <p>View and update your club information and members.</p>
                        <a href="${pageContext.request.contextPath}/admin/manageClubDetails">Manage</a>
                    </div>

                    <div class="card">
                        <i class="fa-solid fa-calendar-plus"></i>
                        <h3>Event Creation</h3>
                        <p>Create, update, and manage club events.</p>
                        <a href="${pageContext.request.contextPath}/admin/manageEvent">Manage</a>
                    </div>

                    <div class="card">
                        <i class="fa-solid fa-bullhorn"></i>
                        <h3>Post Announcement</h3>
                        <p>Share important updates with students.</p>
                        <a href="${pageContext.request.contextPath}/admin/manageAnnouncement">Manage</a>
                    </div>
                </div>

            </div>
        </div>

        <script>
        function showToast(message) {
            const toast = document.getElementById("toast");
            toast.textContent = message;
            toast.classList.add("show");
            setTimeout(() => {
                toast.classList.remove("show");
            }, 3000);
        }

        function toggleSidebar() {
            document.querySelector('.sidebar').classList.toggle('collapsed');
            document.body.classList.toggle('sidebar-collapsed');
        }

        window.onload = function () {
            document.querySelector('.sidebar').classList.add('collapsed');
            document.body.classList.add('sidebar-collapsed');

            <% if (loginSuccess != null && loginSuccess) { %>
                showToast("Login successful!");
            <% } %>
        };
        </script>

    </body>
</html>
