<%-- 
    Document   : indexAdmin
    Created on : 24 Dec 2025, 2:07:15 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    // Collapse sidebar on page load
    window.onload = function () {
        document.querySelector('.sidebar').classList.add('collapsed');
        document.body.classList.add('sidebar-collapsed');
    };

    function toggleSidebar() {
        document.querySelector('.sidebar').classList.toggle('collapsed');
        document.body.classList.toggle('sidebar-collapsed');
    }
</script>

<!DOCTYPE html>
<html>
    <head>
        <title>Admin | University Club Management System</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
    
    <body>
        <!-- ===== TOP NAVBAR ===== -->
        <div class="navbar">
            <div style="display:flex; align-items:center;">
                <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
                <div class="logo">HOME</div>
            </div>

            <ul class="nav-links">
                <li><a href="admin/adminLogin.jsp">Login</a></li>
            </ul>
        </div>

        <!-- ===== SIDEBAR ===== -->
        <div class="sidebar">
            <a href="admin/adminLogin.jsp"><i class="fa-solid fa-house"></i>Home</a>
            <a href="admin/adminLogin.jsp"><i class="fa-solid fa-calendar-days"></i>Event</a>
            <a href="admin/adminLogin.jsp"><i class="fa-solid fa-bullhorn"></i>Announcement</a>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">
                <h1>Welcome to University Club Management System</h1>
                <p class="subtitle">Admin Dashboard</p>

                <div class="card-container">
                    <div class="card">
                        <i class="fa-solid fa-calendar-plus"></i>
                        <h3>Event Creation</h3>
                        <p>Create, update, and manage club events.</p>
                        <a href="admin/adminLogin.jsp">Manage</a>
                    </div>

                    <div class="card">
                        <i class="fa-solid fa-bullhorn"></i>
                        <h3>Post Announcement</h3>
                        <p>Share important updates with students.</p>
                        <a href="admin/adminLogin.jsp">Manage</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>