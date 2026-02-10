<%-- 
    Document   : manageClubMembers
    Created on : 6 Feb 2026, 8:57:10 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="model.User"%>

<%
    Integer clubIdObj = (Integer) request.getAttribute("clubId");
    int clubId = clubIdObj != null ? clubIdObj : 0;

    String currentUserRole = (String) session.getAttribute("clubRole");
    List<User> members = (List<User>) request.getAttribute("members");

    if (clubId == 0 || members == null) {
        response.sendRedirect(request.getContextPath() + "/admin/adminHome.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Club Members</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
</head>

<body>

<!-- ===== NAVBAR ===== -->
<div class="navbar">
    <div class="logo">MANAGE CLUB MEMBERS</div>
    <ul class="nav-links">
        <li>
            <a href="${pageContext.request.contextPath}/admin/manageClubDetails?clubId=<%= clubId %>">
                Back
            </a>
        </li>
    </ul>
</div>

<!-- ===== MAIN CONTENT ===== -->
<div class="home-page">
    <div class="home-container">

        <h1>Club Members</h1>
        <p class="subtitle">Assign and manage member roles</p>

        <table class="members-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Current Role</th>
                    <th>Action</th>
                </tr>
            </thead>

            <tbody>
            <% for (User u : members) { %>
                <tr>
                    <td><%= u.getFullName() %></td>
                    <td><%= u.getEmail() %></td>

                    <td>
                        <span class="role-badge">
                            <%= u.getRole() %>
                        </span>
                    </td>

                    <td>
                        <%
                            boolean isAdvisorRow = "Advisor".equals(u.getRole());
                            boolean isAdvisorUser = "Advisor".equals(currentUserRole);
                        %>

                        <% if (isAdvisorUser) { %>
                            <% if (isAdvisorRow) { %>
                                <!-- Advisor row: fully disabled -->
                                <span class="no-access">Advisor (Locked)</span>
                            <% } else {%>
                                <form action="<%= request.getContextPath()%>/admin/assignRole"
                                      method="post"
                                      style="display:flex; gap:8px; align-items:center; justify-content:center;">

                                    <input type="hidden" name="clubId" value="<%= clubId%>">
                                    <input type="hidden" name="userId" value="<%= u.getUserId()%>">

                                    <select name="role" class="role-select">
                                        <option value="Member"
                                                <%= "Member".equals(u.getRole()) ? "selected" : ""%>>
                                            Member
                                        </option>

                                        <option value="President"
                                                <%= "President".equals(u.getRole()) ? "selected" : ""%>>
                                            President
                                        </option>
                                        
                                        <option value="Vice President"
                                                <%= "Vice President".equals(u.getRole()) ? "selected" : ""%>>
                                            Vice President
                                        </option>

                                        <option value="Secretary"
                                                <%= "Secretary".equals(u.getRole()) ? "selected" : ""%>>
                                            Secretary
                                        </option>

                                        <option value="Treasurer"
                                                <%= "Treasurer".equals(u.getRole()) ? "selected" : ""%>>
                                            Treasurer
                                        </option>
                                    </select>

                                    <button type="submit" class="submit-btn small-btn">
                                        Assign
                                    </button>
                                </form>
                            <% } %>
                        <% } else { %>
                            <span class="no-access">No permission</span>
                        <% } %>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>