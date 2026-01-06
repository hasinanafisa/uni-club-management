<%-- 
    Document   : postAnnouncement
    Created on : 30 Dec 2025, 12:45:17 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="dao.EventDAO"%>
<%@page import="model.Event"%>

<%
    EventDAO eventDao = new EventDAO();
    List<Event> events = eventDao.getAllEvents();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Post Announcement</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="no-sidebar">

<!-- ===== NAVBAR ===== -->
<div class="navbar">
    <div style="display:flex; align-items:center;">
        <div class="logo">POST ANNOUNCEMENT</div>
    </div>

    <ul class="nav-links">
        <li><a href="manageAnnouncement.jsp">Back</a></li>
        <li><a href="adminHome.jsp">Home</a></li>
    </ul>
</div>

<!-- ===== MAIN CONTENT ===== -->
<div class="home-page">
    <div class="home-container">

        <h1>Post New Announcement</h1>
        <p class="subtitle">Fill in announcement details below</p>

        <form class="create-event-form" action="${pageContext.request.contextPath}/PostAnnouncementServlet"
              method="post" enctype="multipart/form-data">

            <!-- Title -->
            <input type="text" name="announceTitle" placeholder="Announcement Title *" required>

            <!-- Content -->
            <textarea name="announceContent" rows="4" placeholder="Announcement Content *" required></textarea>

            <!-- Category -->
            <select name="announceCategory" required>
                <option value="" disabled selected hidden>-- Select Category --</option>
                <option value="Important">Important</option>
                <option value="General">General</option>
            </select>

            <!-- Upload Image -->
            <input type="file" name="imagePath" accept=".jpg,.png,image/jpeg,image/png" 
                   placeholder="Image (optional)">

            <!-- Upload File Attachment -->
            <input type="file" name="attachmentPath" accept=".pdf,application/pdf" 
                   placeholder="Attachment (optional)">

            <!-- Related Event -->
            <select name="eventID" required>
                <option value="">-- Related Event --</option>
                <% for (Event e : events) { %>
                    <option value="<%= e.getEventID() %>">
                        <%= e.getEventTitle() %>
                    </option>
                <% } %>
            </select>

            <!-- Submit -->
            <div class="form-actions">
                <button type="submit" class="submit-btn">
                    Post Announcement
                </button>
            </div>

        </form>

    </div>
</div>

</body>
</html>
