<%-- 
    Document   : login
    Created on : 26 Dec 2025, 7:08:25 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("student/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home - UniClub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

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
        <a href="clubList.jsp"><i class="fa-solid fa-users"></i> CLUB LIST</a>
        <a href="dashboard.jsp"><i class="fa-solid fa-chart-line"></i> CLUB DASHBOARD</a>
        <a href="events.jsp"><i class="fa-solid fa-calendar-check"></i> UPCOMING EVENTS</a>
        <a href="announcements.jsp"><i class="fa-solid fa-bullhorn"></i> ANNOUNCEMENT</a>
    </nav>

    <div class="home-page" id="mainPage">
        <div class="home-container">
            <h1>WELCOME, <%= user.getFullName().toUpperCase() %></h1>
            <p class="subtitle">Select a category to manage your club activities</p>

            <div class="search-filter-bar">
                <div class="search-box">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" placeholder="Search clubs or events...">
                </div>
            </div>

            <div class="card-container">
                <div class="card">
                    <i class="fa-solid fa-users-viewfinder"></i>
                    <h3>CLUB LIST</h3>
                    <p>Find new clubs to join this semester.</p>
                    <a href="clublist.jsp">BROWSE</a>
                </div>

                <div class="card">
                    <i class="fa-solid fa-gauge"></i>
                    <h3>DASHBOARD</h3>
                    <p>Check your membership status and roles.</p>
                    <a href="clubDashboard.jsp">VIEW</a>
                </div>

                <div class="card">
                    <i class="fa-solid fa-calendar-days"></i>
                    <h3>EVENTS</h3>
                    <p>See what's happening around campus.</p>
                    <a href="upcomingEvents.jsp">EXPLORE</a>
                </div>

                <div class="card">
                    <i class="fa-solid fa-bell"></i>
                    <h3>NEWS</h3>
                    <p>Read the latest club announcements.</p>
                    <a href="announcements.jsp">READ</a>
                </div>
            </div>
        </div>
    </div>

    <script>
        const toggleIcon = document.getElementById('toggleIcon');
        const sidebar = document.getElementById('sidebar');
        const body = document.body;

        toggleIcon.addEventListener('click', () => {
            // Toggles the sidebar visibility
            sidebar.classList.toggle('collapsed');
            // Toggles the main content margin
            body.classList.toggle('sidebar-collapsed');
        });
    </script>

    <%-- Success Message --%>
    <% if (request.getParameter("loginSuccess") != null) { %>
    <script>
        Swal.fire({
            title: 'Hello, <%= user.getFullName() %>!',
            text: 'You have logged in successfully.',
            icon: 'success',
            timer: 2500,
            showConfirmButton: false
        });
    </script>
    <% } %>

</body>
</html>