<%-- 
    Document   : postAnnouncement
    Created on : 30 Dec 2025, 12:45:17 pm
    Author     : izyanie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Event"%>
<%
    List<Event> events = (List<Event>) request.getAttribute("events");
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
                <li><a href="${pageContext.request.contextPath}/admin/manageAnnouncement">Back</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/adminHome.jsp">Home</a></li>
            </ul>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">
                <h1>Post New Announcement</h1>
                <p class="subtitle">Fill in announcement details below</p>

                <form class="create-event-form"
                      action="${pageContext.request.contextPath}/admin/postAnnouncement"
                      method="post"
                      enctype="multipart/form-data">

                    <!-- Related Event -->
                    <select name="eventId" required>
                        <option value="">-- Related Event --</option>
                        <% for (Event e : events) { %>
                            <option value="<%= e.getEventID() %>">
                                <%= e.getEventTitle() %>
                            </option>
                        <% } %>
                    </select>

                    <!-- Title -->
                    <label>Title *</label>
                    <input type="text" name="title" required>

                    <!-- Content -->
                    <label>Content *</label>
                    <textarea name="content" rows="4" required></textarea>

                    <!-- Category -->
                    <label>Category *</label>
                    <select name="category" required>
                        <option value="" disabled selected hidden>-- Select Category --</option>
                        <option value="Important">Important</option>
                        <option value="General">General</option>
                    </select>

                    <!-- Upload Image -->
                    <label>Image (Optional)</label>
                    <input type="file" name="imagePath" accept=".jpg,.png,image/jpeg,image/png">

                    <!-- Upload File Attachment -->
                    <label>Attachment (Optional)</label>
                    <input type="file" name="attachmentPath" accept=".pdf,application/pdf">

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
