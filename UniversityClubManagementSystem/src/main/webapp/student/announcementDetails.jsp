<%-- 
    Document   : announcementDetails
    Created on : 24 Jan 2026, 11:46:34 pm
    Author     : Hasina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Announcement"%>
<%@page import="model.Event"%>
<%@page import="dao.EventDAO"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
Announcement a = (Announcement) request.getAttribute("announcement");

if (a == null) {
    out.println("<p>No announcement found.</p>");
    return;
}

Event ev = null;
if (a.getEventId() != null) {
    EventDAO eventDAO = new EventDAO();
    ev = eventDAO.getEventById(a.getEventId());
}

SimpleDateFormat dateFormat =
        new SimpleDateFormat("dd MMM yyyy, h:mm a");

SimpleDateFormat eventDateFormat = new SimpleDateFormat("dd/MM/yyyy");
SimpleDateFormat eventTimeFormat = new SimpleDateFormat("h:mm a");

%>

<!DOCTYPE html>
<html>
<head>
    <title><%= a.getTitle() %></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
<%@include file="/includes/header.jsp" %>

<div class="home-page">
    <div class="home-container">

        <div class="details-container">

            <!-- LEFT CONTENT -->
            <div class="details-main">
                <h1><%= a.getTitle() %></h1>
                <hr>
                <p style="color:#666;">
                    Posted on <%= dateFormat.format(a.getPostedAt()) %>
                </p>
                <br>
                <p class="details-content">
                    <%= a.getContent() %>
                </p>
                
                <% if (a.getEventId() == null) { %>
                    <div style="margin-top:25px;">
                        <a href="<%= request.getContextPath() %>/student/club?clubId=<%= a.getClubId() %>"
                           class="btn-primary">
                            View Club
                        </a>
                    </div>
                <% } %>
                
                <!-- ATTACHMENTS -->
                <% if (a.getAttachmentPath() != null && !a.getAttachmentPath().isBlank() && !a.getAttachmentPath().contains("default")) { %>
                     <div class="details-section">
                         <h3>📎 Attachment</h3>
                         <a href="<%= request.getContextPath() %>/uploads/announcements/<%= a.getAttachmentPath() %>"
                            download>
                             Download Attachment
                         </a>
                     </div>
                <% } %>

                <!-- IMAGE -->
                <% if (a.getImagePath() != null && !a.getImagePath().isBlank() && !a.getImagePath().contains("default")) { %>
                <div class="details-section">
                    <h3>📷 Image</h3>
                        <img src="<%= request.getContextPath() %>/announcementImage?id=<%= a.getAnnouncementId() %>"
                                 class="details-image">
                </div>
                <% } %>
                
                <a href="${pageContext.request.contextPath}/student/announcements"
                   class="back-link">Back</a>
            </div>

            <!-- RIGHT INFO CARD -->
            <% if (ev != null) { %>
                <div class="details-side card">
                    <h3>📅 Event Info</h3>

                    <p>
                        <strong>Event:</strong><br>
                        <%= ev.getEventTitle() %>
                    </p>

                    <p>
                        <strong>Date:</strong><br>
                        <%= eventDateFormat.format(ev.getEventDate()) %>
                    </p>

                    <p>
                        <strong>Time:</strong><br>
                        <%= eventTimeFormat.format(ev.getEventTime()) %>
                    </p>

                    <p>
                        <strong>Location:</strong><br>
                        <%= ev.getEventLoc() %>
                    </p>
                </div>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
