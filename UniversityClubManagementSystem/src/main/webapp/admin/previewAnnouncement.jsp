<%-- 
    Document   : previewAnnouncement
    Created on : 2 Jan 2026, 12:57:13 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Announcement"%>
<%@page import="model.Event"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    Announcement a = (Announcement) request.getAttribute("announcement");
    Event e = (Event) request.getAttribute("event");

    if (a == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
        return;
    }

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
                <li><a href="${pageContext.request.contextPath}/admin/manageAnnouncement">Back</a></li>
            </ul>
        </div>

        <div class="home-page">
            <div class="home-container">

                <!-- CATEGORY -->
                <div class="announce-category-badge <%= a.getCategory().toLowerCase() %>"
                     style="font-size:16px; margin-bottom:20px;">
                    <%= a.getCategory() %>
                </div>

                <h1><%= a.getTitle() %></h1>

                <% if (e != null) { %>
                    <p class="subtitle">
                        <strong>Event:</strong> <%= e.getEventTitle() %><br>
                        <strong>Date:</strong> <%= e.getEventDate() %> |
                        <strong>Time:</strong> <%= timeFormat.format(e.getEventTime()) %>
                    </p>
                <% } %>

                <!-- CONTENT -->
                <p style="margin-top:30px; font-size:16px; line-height:1.7;">
                    <%= a.getContent() %>
                </p>

                <!-- IMAGE -->
                <% if (a.getImagePath() != null && !a.getImagePath().contains("default")) { %>
                    <img src="${pageContext.request.contextPath}/announcementFile?id=<%= a.getAnnouncementId() %>&type=image"
                        style="width:75%; margin-top:30px; border-radius:16px;">
                <% } %>

                <!-- ATTACHMENT -->
                <% if (a.getAttachmentPath() != null && !a.getAttachmentPath().contains("default")) { %>
                    <p style="margin-top:25px;">
                        <strong>Attachment: </strong>
                        <a href="${pageContext.request.contextPath}/announcementFile?id=<%= a.getAnnouncementId() %>&type=attachment">
                            Download
                        </a>
                    </p>
                <% } %>
            </div>
        </div>
    </body>
</html>