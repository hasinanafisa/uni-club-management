<%-- 
    Document   : editAnnouncement
    Created on : 30 Dec 2025, 12:54:42 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Announcement"%>
<%@page import="model.Event"%>

<%
    Announcement a = (Announcement) request.getAttribute("announcement");
    List<Event> events = (List<Event>) request.getAttribute("events");

    if (a == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manageAnnouncement");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Edit Announcement</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body class="no-sidebar">
        <!-- ===== TOAST NOTIFICATION ===== -->
        <div id="toast" class="toast"></div>

        <!-- ===== NAVBAR ===== -->
        <div class="navbar">
            <div class="logo">EDIT ANNOUNCEMENT</div>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/admin/manageAnnouncement">Back</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/adminHome.jsp">Home</a></li>
            </ul>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">
                <h1>Edit Announcement</h1>
                <p class="subtitle">Update announcement details below</p>

                <form class="create-event-form" action="${pageContext.request.contextPath}/admin/editAnnouncement"
                      method="post" enctype="multipart/form-data">

                    <!-- Hidden ID -->
                    <input type="hidden" name="announcementId" value="<%= a.getAnnouncementId() %>">
                    
                    <!-- RELATED EVENT -->
                    <select name="eventId" required>
                        <option value="">-- Select Related Event --</option>
                        <% for (Event ev : events) { %>
                            <option value="<%= ev.getEventID() %>" <%= ev.getEventID() == a.getEventId() ? "selected" : "" %>>
                                <%= ev.getEventTitle() %>
                            </option>
                        <% } %>
                    </select>

                    <!-- TITLE -->
                    <input type="text" name="title" value="<%= a.getTitle() %>" required>

                    <!-- CONTENT -->
                    <textarea name="content" rows="4" required><%= a.getContent() %></textarea>

                    <!-- CATEGORY -->
                    <select name="category" required>
                        <option value="">-- Select Category --</option>
                        <option value="Important"
                            <%= "Important".equals(a.getCategory()) ? "selected" : "" %>>
                            Important
                        </option>
                        <option value="General"
                            <%= "General".equals(a.getCategory()) ? "selected" : "" %>>
                            General
                        </option>
                    </select>

                    <!-- CURRENT IMAGE -->
                    <% if (a.getImagePath() != null && !a.getImagePath().isEmpty()) { %>
                        <p>
                            <strong>Current Image: </strong>
                            <img src="${pageContext.request.contextPath}/announcementFile?id=<%= a.getAnnouncementId() %>&type=image">
                        </p>
                    <% } %>
                    <input type="file" name="imagePath" accept=".jpg,.png,image/jpeg,image/png">

                    <!-- CURRENT ATTACHMENT -->
                    <% if (a.getAttachmentPath() != null && !a.getAttachmentPath().isEmpty()) { %>
                        <p>
                            <strong>Current Attachment:</strong>
                            <a href="${pageContext.request.contextPath}/announcementFile?id=<%= a.getAnnouncementId() %>&type=attachment">
                        </p>
                    <% } %>
                    <input type="file" name="attachmentPath" accept=".pdf,application/pdf">
                    
                    <!-- ACTIONS -->
                    <div class="form-actions">
                        <button type="submit" class="submit-btn">
                            Update Announcement
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>