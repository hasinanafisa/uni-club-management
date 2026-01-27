<%-- 
    Document   : announcement
    Created on : 24 Jan 2026, 11:39:12 pm
    Author     : Hasina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Announcement"%>

<h2>📢 Announcements</h2>

<%
List<Announcement> announcements = 
    (List<Announcement>) request.getAttribute("announcements");
%>

<% for (Announcement a : announcements) { %>
    <div class="card">
        <h3><%= a.getTitle() %></h3>
        <p><%= a.getContent().substring(0, Math.min(100, a.getContent().length())) %>...</p>
        <a href="AnnouncementDetailsServlet?id=<%= a.getId() %>">Read More</a>
    </div>
<% } %>
