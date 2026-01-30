<%-- 
    Document   : manageClubDetails
    Created on : 28 Jan 2026, 2:07:52â¯am
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%@page import="model.Club"%>
<%@page import="java.util.List"%>

<%
    User user = (User) session.getAttribute("user");
    Club club = (Club) request.getAttribute("club");
    List<User> members = (List<User>) request.getAttribute("members");

    if (user == null || club == null) {
        response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Manage Club Details</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
    <body>
        <!-- ===== NAVBAR ===== -->
        <div class="navbar">
            <div style="display:flex; align-items:center;">
                <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
                <div class="logo">CLUB MANAGEMENT</div>
            </div>

            <ul class="nav-links">
                <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
            </ul>
        </div>

        <!-- ===== SIDEBAR ===== -->
        <div class="sidebar">
            <a href="${pageContext.request.contextPath}/admin/adminHome.jsp"><i class="fa-solid fa-house"></i>Home</a>
            <a href="${pageContext.request.contextPath}/admin/manageClubDetails" class="active-link">
                <i class="fa-solid fa-gear"></i>Manage Club Details
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageEvent.jsp"><i class="fa-solid fa-calendar-days"></i>Manage Event</a>
            <a href="${pageContext.request.contextPath}/admin/manageAnnouncement.jsp"><i class="fa-solid fa-bullhorn"></i>Manage Announcement</a>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">

                <!-- ===== CLUB DETAILS CARD ===== -->
                <div class="card">

                    <div style="text-align:center; margin-bottom:20px;">
                        <img src="${pageContext.request.contextPath}/uploads/<%= club.getLogoPath() %>"
                             alt="Club Logo"
                             style="width:120px; height:120px; object-fit:cover; border-radius:50%; box-shadow:0 4px 10px rgba(0,0,0,0.15);">
                    </div>

                    <h2 style="text-align:center;"><%= club.getClubName() %></h2>

                    <p style="text-align:center; color:#555; margin-top:10px;">
                        <%= club.getDescription() %>
                    </p>

                    <div style="text-align:center; margin-top:30px;">
                        <a href="${pageContext.request.contextPath}/admin/editClubDetails.jsp?clubId=<%= club.getClubID() %>"
                           class="create-event-btn">
                            Edit Club Details
                        </a>
                    </div>
                </div>

                <!-- ===== CLUB MEMBERS ===== -->
                <div class="card" style="margin-top:30px;">
                    <h3>Club Members</h3>

                    <% if (members == null || members.isEmpty()) { %>
                        <p style="color:#666;">No members have joined this club yet.</p>
                    <% } else { %>
                        <ul style="list-style:none; padding-left:0;">
                            <% for (User m : members) { %>
                                <li style="padding:10px 0; border-bottom:1px solid #eee;">
                                    <i class="fa-solid fa-user"></i>
                                    <strong><%= m.getFullName() %></strong>
                                    <span style="color:#777;">(<%= m.getEmail() %>)</span>
                                </li>
                            <% } %>
                        </ul>
                    <% } %>
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
        </script>
    </body>
</html>