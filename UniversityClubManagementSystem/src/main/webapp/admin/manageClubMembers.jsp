<%-- 
    Document   : manageClubMembers
    Created on : 6 Feb 2026, 8:57:10 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="model.User"%>

<%
    int clubId = (int) request.getAttribute("clubId");
    String currentUserRole = (String) session.getAttribute("clubRole");
    List<User> members = (List<User>) request.getAttribute("members");
%>

<html>
<head>
    <title>Manage Club Members</title>
</head>
<body>

<h2>Club Members</h2>

<table border="1" cellpadding="8">
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Current Role</th>
        <th>Action</th>
    </tr>

    <% for (User u : members) { %>
    <tr>
        <td><%= u.getFullName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getRole() %></td>
        <td>

            <%-- Only advisor or president can assign roles --%>
            <% if ("Advisor".equals(currentUserRole) || "President".equals(currentUserRole)) { %>

            <form action="<%=request.getContextPath()%>/admin/assignRole" method="post">
                <input type="hidden" name="clubId" value="<%=clubId%>">
                <input type="hidden" name="userId" value="<%=u.getUserId()%>">

                <select name="role">
                    <option value="MEMBER">Member</option>
                    <% if ("Advisor".equals(currentUserRole)) { %>
                        <option value="President">President</option>
                    <% } %>

                    <% if ("President".equals(currentUserRole)) { %>
                        <option value="Secretary">Secretary</option>
                        <option value="Treasurer">Treasurer</option>
                    <% } %>
                </select>

                <button type="submit">Assign</button>
            </form>

            <% } else { %>
                No permission
            <% } %>

        </td>
    </tr>
    <% } %>

</table>

</body>
</html>
