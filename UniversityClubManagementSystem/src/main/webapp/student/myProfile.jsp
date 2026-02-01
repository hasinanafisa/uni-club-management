<%-- 
    Document   : myProfile
    Created on : 24 Jan 2026
    Author     : Hasina
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Event" %>
<%@ page import="java.util.List" %>

<%
    User student = (User) session.getAttribute("user");
    List<Event> events = (List<Event>) request.getAttribute("events");
%>

<h2>👤 My Profile</h2>

<% if (student != null) { %>
<form action="UpdateStudentProfileServlet" method="post">
    Name:
    <input type="text" name="name" value="<%= student.getFullName() %>"><br><br>

    Email:
    <input type="email" name="email" value="<%= student.getEmail() %>"><br><br>

    <button type="submit">Update</button>
</form>
<% } else { %>
    <p style="color:red;">User not logged in.</p>
<% } %>

<hr>

<h3>📅 Events Joined</h3>

<% if (events != null && !events.isEmpty()) { %>
<table border="1" cellpadding="5">
    <tr>
        <th>Event Name</th>
        <th>Date</th>
    </tr>

    <% for (Event e : events) { %>
    <tr>
        <td><%= e.getName() %></td>
        <td><%= e.getDate() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
    <p>No events joined yet.</p>
<% } %>
