<%-- 
    Document   : previewEvent
    Created on : 2 Jan 2026, 12:56:53 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.EventDAO"%>
<%@page import="model.Event"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    int eventId = Integer.parseInt(request.getParameter("id"));
    EventDAO dao = new EventDAO();
    Event e = dao.getEventById(eventId);

    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Event Preview</title>
    <link rel="stylesheet" href="../css/adminstyle.css">
</head>

<body class="no-sidebar">

<!-- ===== NAVBAR ===== -->
<div class="navbar">
    <div class="logo">EVENT PREVIEW</div>
    <ul class="nav-links">
        <li><a href="manageEvent.jsp">Back</a></li>
    </ul>
</div>

<div class="home-page">
    <div class="home-container">
        <div class="preview-wrapper">
            <div class="preview-grid">

                <!-- Banner -->
                <div class="preview-banner">
                    <img src="<%= request.getContextPath() %>/uploads/<%= e.getBannerImagePath() %>" alt="Banner">
                </div>

                <!-- QR -->
                <div class="preview-qr">
                    <h4>Attendance QR</h4>
                    <img src="<%= request.getContextPath() %>/uploads/<%= e.getQrPath() %>" alt="QR Code">
                </div>

                <!-- Title + Description -->
                <div class="preview-main">
                    <h2><%= e.getEventTitle() %></h2>
                    <p><%= e.getEventDesc() %></p>
                </div>

                <!-- Meta info -->
                <div class="preview-meta">
                    <p><strong>Date:</strong> <%= e.getEventDate() %></p>
                    <p><strong>Time:</strong> <%= timeFormat.format(e.getEventTime()) %></p>
                    <p><strong>Location:</strong> <%= e.getEventLoc() %></p>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
