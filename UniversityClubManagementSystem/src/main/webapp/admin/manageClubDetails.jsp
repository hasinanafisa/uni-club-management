<%-- 
    Document   : manageClubDetails
    Created on : 28 Jan 2026, 2:07:52 am
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Club"%>
<%@page import="model.ClubMember"%>
<%@page import="model.User"%>
<%@page import="dao.UserDAO"%>
<%@page import="java.util.List"%>

<%
    User user = (User) session.getAttribute("user");
    Club club = (Club) request.getAttribute("club");
    List<ClubMember> members = (List<ClubMember>) request.getAttribute("members");

    UserDAO userDAO = new UserDAO();

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
            <a href="${pageContext.request.contextPath}/admin/manageEvent"><i class="fa-solid fa-calendar-days"></i>Manage Event</a>
            <a href="${pageContext.request.contextPath}/admin/manageAnnouncement"><i class="fa-solid fa-bullhorn"></i>Manage Announcement</a>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">
                <div class="club-details-card">

                    <!-- TOP: Club Info -->
                    <div class="club-info">
                        <div class="club-logo">
                            <img src="${pageContext.request.contextPath}/${club.logoPath}"/>
                        </div>

                        <div class="club-meta">
                            <h2><%= club.getClubName() %></h2>
                            <p class="club-desc"><%= club.getDescription() %></p>

                            <p class="club-created">
                                <strong>Created By:</strong> <%= club.getCreatedBy() %><br>
                                <strong>Created At:</strong> <%= club.getCreatedAt() %>
                            </p>

                            <a href="editClubDetails.jsp?clubId=<%= club.getClubId() %>"
                               class="edit-btn">
                               Edit Club Details
                            </a>
                        </div>
                    </div>

                    <!-- BOTTOM: Members -->
                    <div class="club-members">
                        <h3>
                            Club Members
                            <span class="member-badge"><%= members.size() %></span>
                        </h3>

                        <!-- table goes here -->
                        <% if (members == null || members.isEmpty()) { %>
                            <p class="empty-text">No members have joined this club yet.</p>
                        <% } else { %>

                        <table class="members-table">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>User Type</th>
                                    <th>Role</th>
                                    <th>Join Date</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                int i = 1;
                                for (ClubMember m : members) {
                                    User u = userDAO.getUserById(m.getUserId());
                            %>
                                <tr>
                                    <td><%= i++ %></td>
                                    <td><%= u.getFullName() %></td>
                                    <td><%= u.getUserType() %></td>
                                    <td class="role"><%= m.getRole() %></td>
                                    <td><%= m.getJoinDate() %></td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>

                        <% } %>
                    </div>
                </div>
            </div>
        </div>

        <script>
            window.onload = function () {
                document.querySelector('.sidebar').classList.add('collapsed');
                document.body.classLiSst.add('sidebar-collapsed');
            };

            function toggleSidebar() {
                document.querySelector('.sidebar').classList.toggle('collapsed');
                document.body.classList.toggle('sidebar-collapsed');
            }
        </script>
    </body>
</html>