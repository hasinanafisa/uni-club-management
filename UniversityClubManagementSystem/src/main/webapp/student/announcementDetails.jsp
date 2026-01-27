<%-- 
    Document   : announcementDetails
    Created on : 24 Jan 2026, 11:46:34 pm
    Author     : Hasina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Announcement" %>

<%
Announcement a = (Announcement) request.getAttribute("announcement");
%>

<h2><%= a.getTitle() %></h2>
<p><%= a.getContent() %></p>

<a href="StudentAnnouncementServlet">⬅ Back to Announcements</a>
