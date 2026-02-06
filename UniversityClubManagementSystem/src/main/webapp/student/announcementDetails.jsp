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

EventDAO eventDAO = new EventDAO();
Event ev = eventDAO.getEventById(a.getEventId());

SimpleDateFormat dateFormat =
        new SimpleDateFormat("dd MMM yyyy, h:mm a");

SimpleDateFormat eventDateFormat = new SimpleDateFormat("dd/MM/yyyy");
SimpleDateFormat eventTimeFormat = new SimpleDateFormat("h:mm a");

if (a == null) {
    out.println("<p>No announcement found.</p>");
    return;
}
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
                
                <!-- ATTACHMENTS -->
                <div class="details-section">
                    <h3>📎 Attachment</h3>
                    <a href="<%= request.getContextPath() %>/uploads/announcements/<%= a.getAttachmentPath() %>"
                       download>
                        Download Attachment
                    </a>
                </div>

                <!-- IMAGE -->
                <div class="details-section">
                    <h3>📷 Image</h3>
                        <% if (a.getImagePath() != null && !a.getImagePath().isEmpty()) { %>
                            <img src="<%= request.getContextPath() %>/announcementImage?id=<%= a.getAnnouncementId() %>"
                                 class="details-image">
                        <% } else { %>
                            <p>No image available.</p>
                        <% } %>
                </div>
                
                <a href="${pageContext.request.contextPath}/announcements"
                   class="back-link">Back</a>
            </div>

            <!-- RIGHT INFO CARD -->
            <div class="details-side card">
                <h3>📅 Event Info</h3>

                <% if (ev != null) { %>
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
                <% } else { %>
                    <p>No event information available.</p>
                <% } %>
            </div>

        </div>
    </div>
</div>

</body>
</html>
