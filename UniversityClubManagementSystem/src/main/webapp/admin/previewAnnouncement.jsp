<%-- 
    Document   : previewAnnouncement
    Created on : 2 Jan 2026, 12:57:13 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.AnnouncementDAO"%>
<%@page import="model.Announcement"%>
<%@page import="dao.EventDAO"%>
<%@page import="model.Event"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    int announceId = Integer.parseInt(request.getParameter("id"));

    AnnouncementDAO dao = new AnnouncementDAO();
    Announcement a = dao.getAnnouncementById(announceId);

    EventDAO eventDao = new EventDAO();
    Event e = eventDao.getEventById(a.getEventID());

    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Announcement Preview</title>
    <link rel="stylesheet" href="../css/adminstyle.css">
</head>

<body class="no-sidebar">

<!-- ===== NAVBAR ===== -->
<div class="navbar">
    <div class="logo">ANNOUNCEMENT PREVIEW</div>
    <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/admin/manageAnnouncement.jsp">Back</a></li>
    </ul>
</div>

<div class="home-page">
    <div class="home-container">

        <!-- CATEGORY -->
        <div class="announce-category-badge <%= a.getAnnounceCategory().toLowerCase() %>"
             style="font-size:16px; margin-bottom:20px;">
            <%= a.getAnnounceCategory() %>
        </div>

        <h1><%= a.getAnnounceTitle() %></h1>

        <% if (e != null) { %>
            <p class="subtitle">
                <strong>Event:</strong> <%= e.getEventTitle() %><br>
                <strong>Date:</strong> <%= e.getEventDate() %> |
                <strong>Time:</strong> <%= timeFormat.format(e.getEventTime()) %>
            </p>
        <% } %>

        <!-- CONTENT -->
        <p style="margin-top:30px; font-size:16px; line-height:1.7;">
            <%= a.getAnnounceContent() %>
        </p>

        <!-- IMAGE -->
        <% if (a.getImagePath() != null && !a.getImagePath().isEmpty()) { %>
            <img src="<%= request.getContextPath() %>/uploads/<%= a.getImagePath() %>"
                 style="width:100%; margin-top:30px; border-radius:16px;">
        <% } %>

        <!-- ATTACHMENT -->
        <% if (a.getAttachmentPath() != null && !a.getAttachmentPath().isEmpty()) { %>
            <p style="margin-top:25px;">
                <strong>Attachment:</strong>
                <a href="<%= request.getContextPath() %>/uploads/<%= a.getAttachmentPath() %>"
                   target="_blank">
                    <%= a.getAttachmentPath() %>
                </a>
            </p>
        <% } %>

    </div>
</div>

</body>
</html>
