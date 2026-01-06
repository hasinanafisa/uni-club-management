<%-- 
    Document   : editAnnouncement
    Created on : 30 Dec 2025, 12:54:42 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.universityclubmanagementsystem.dao.AnnouncementDAO"%>
<%@page import="com.mycompany.universityclubmanagementsystem.model.Announcement"%>
<%@page import="com.mycompany.universityclubmanagementsystem.dao.EventDAO"%>
<%@page import="com.mycompany.universityclubmanagementsystem.model.Event"%>

<%
    int announceId = Integer.parseInt(request.getParameter("id"));

    AnnouncementDAO dao = new AnnouncementDAO();
    Announcement a = dao.getAnnouncementById(announceId);

    EventDAO eventDao = new EventDAO();
    List<Event> events = eventDao.getAllEvents();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Announcement</title>
    <link rel="stylesheet" href="../css/adminstyle.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="no-sidebar">

<!-- ===== NAVBAR ===== -->
<div class="navbar">
    <div class="logo">EDIT ANNOUNCEMENT</div>
    <ul class="nav-links">
        <li><a href="manageAnnouncement.jsp">Back</a></li>
        <li><a href="adminHome.jsp">Home</a></li>
    </ul>
</div>

<!-- ===== MAIN CONTENT ===== -->
<div class="home-page">
    <div class="home-container">

        <h1>Edit Announcement</h1>
        <p class="subtitle">Update announcement details below</p>

        <form class="create-event-form"
              action="<%= request.getContextPath() %>/EditAnnouncementServlet"
              method="post"
              enctype="multipart/form-data">

            <!-- Hidden ID -->
            <input type="hidden" name="announceID" value="<%= a.getAnnounceID() %>">

            <!-- TITLE -->
            <input type="text"
                   name="announceTitle"
                   value="<%= a.getAnnounceTitle() %>"
                   placeholder="Announcement Title *"
                   required>

            <!-- CONTENT -->
            <textarea name="announceContent"
                      rows="4"
                      placeholder="Announcement Content *"
                      required><%= a.getAnnounceContent() %></textarea>

            <!-- CATEGORY -->
            <select name="announceCategory" required>
                <option value="">-- Select Category --</option>
                <option value="Important"
                    <%= "Important".equals(a.getAnnounceCategory()) ? "selected" : "" %>>
                    Important
                </option>
                <option value="General"
                    <%= "General".equals(a.getAnnounceCategory()) ? "selected" : "" %>>
                    General
                </option>
            </select>

            <!-- CURRENT IMAGE -->
            <% if (a.getImagePath() != null && !a.getImagePath().isEmpty()) { %>
                <p>
                    <strong>Current Image:</strong>
                    <a href="<%= request.getContextPath() %>/uploads/<%= a.getImagePath() %>"
                       target="_blank">
                        <%= a.getImagePath() %>
                    </a>
                </p>
            <% } %>

            <input type="file"
                   name="imagePath"
                   accept=".jpg,.png,image/jpeg,image/png">

            <!-- CURRENT ATTACHMENT -->
            <% if (a.getAttachmentPath() != null && !a.getAttachmentPath().isEmpty()) { %>
                <p>
                    <strong>Current Attachment:</strong>
                    <a href="<%= request.getContextPath() %>/uploads/<%= a.getAttachmentPath() %>"
                       target="_blank">
                        <%= a.getAttachmentPath() %>
                    </a>
                </p>
            <% } %>

            <input type="file"
                   name="attachmentPath"
                   accept=".pdf,application/pdf">

            <!-- RELATED EVENT -->
            <select name="eventID" required>
                <option value="">-- Select Related Event --</option>
                <% for (Event ev : events) { %>
                    <option value="<%= ev.getEventID() %>"
                        <%= ev.getEventID() == a.getEventID() ? "selected" : "" %>>
                        <%= ev.getEventTitle() %>
                    </option>
                <% } %>
            </select>

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