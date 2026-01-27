<%-- 
    Document   : announcementDetails
    Created on : 24 Jan 2026, 11:46:34 pm
    Author     : Hasina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Announcement"%>

<%
//Announcement a = (Announcement) request.getAttribute("announcement");
Announcement a = new Announcement(); //dummy data

a.setAnnounceID(1);
a.setAnnounceTitle("Sports Day 2026");
a.setAnnounceContent(
    "Join us for inter-faculty sports competitions. All students are welcome!"
);
a.setAnnounceCategory("EVENT"); //sampe sitok
if (a == null) {
    out.println("<p>No announcement found.</p>");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= a.getAnnounceTitle() %></title>

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
                <h1><%= a.getAnnounceTitle() %></h1>
                <hr>

                <p class="details-content">
                    <%= a.getAnnounceContent() %>
                </p>

                <!-- ATTACHMENTS (dummy for now) -->
                <div class="details-section">
                    <h3>📎 Attached Files</h3>
                    <a href="#">file.pdf</a>,
                    <a href="#">poster.jpg</a>,
                    <a href="#">forms.docx</a>
                </div>

                <!-- IMAGE -->
                <div class="details-section">
                    <h3>🖼 Images</h3>
                    <img src="${pageContext.request.contextPath}/images/sample-event.jpg"
                         alt="Announcement Image"
                         class="details-image">
                </div>

                <a href="StudentAnnouncementServlet" class="back-link">
                    ← Back to Announcements
                </a>
            </div>

            <!-- RIGHT INFO CARD -->
            <div class="details-side card">
                <h3>📅 Event Info</h3>

                <p><strong>Date / Time:</strong><br> 10 March 2026, 2:00 PM</p>
                <p><strong>Location:</strong><br> Main Hall</p>

                <hr>

                <p><strong>Registration:</strong></p>
                <a href="#" class="view-btn">Join Event</a>
                <a href="#" class="cancel-link">Cancel Registration</a>

                <hr>

                <p><strong>Attendance QR:</strong></p>
                <img src="${pageContext.request.contextPath}/images/qr-sample.png"
                     class="qr-img" alt="QR Code">

                <hr>

                <p><strong>Volunteer Roles:</strong></p>
                <a href="#" class="view-btn">Apply Here</a>
            </div>

        </div>
    </div>
</div>

</body>
</html>
