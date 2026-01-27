<%-- 
    Document   : myProfile
    Created on : 24 Jan 2026, 11:52:50 pm
    Author     : Hasina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.Event" %>
<%@ page import="java.util.List" %>

<%
User student = (User) session.getAttribute("user");
List<Event> events = (List<Event>) request.getAttribute("events");
%>

<h2>👤 My Profile</h2>

<form action="UpdateStudentProfileServlet" method="post">
    Name: <input type="text" name="name" value="<%= student.getName() %>"><br>
    Email: <input type="email" name="email" value="<%= student.getEmail() %>"><br>
    <button type="submit">Update</button>
</form>

<h3>📅 Events Joined</h3>
<table border="1">
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

