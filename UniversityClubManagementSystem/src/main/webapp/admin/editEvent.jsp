<%-- 
    Document   : editEvent
    Created on : 27 Dec 2025, 11:04:25 pm
    Author     : izyanie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Event"%>

<%
    Event event = (Event) request.getAttribute("event");
    if (event == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manageEvent");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Edit Event</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body class="no-sidebar">

    <div id="toast" class="toast"></div>

    <div class="navbar">
        <div style="display:flex; align-items:center;">
            <div class="logo">EDIT EVENT</div>
        </div>
        <ul class="nav-links">
            <li>
                <a href="${pageContext.request.contextPath}/admin/manageEvent">Back</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/adminHome.jsp">Home</a>
            </li>
        </ul>
    </div>

    <div class="home-page">
        <div class="home-container">
            <h1>Edit Event</h1>
            <p class="subtitle">Update event details below</p>

            <form class="create-event-form"
                  action="${pageContext.request.contextPath}/admin/editEvent"
                  method="post"
                  enctype="multipart/form-data">

                <input type="hidden" name="eventID" value="<%= event.getEventID() %>">

                <% if (event.getBannerImagePath() != null && !event.getBannerImagePath().isEmpty()) { %>
                    <p>
                        <strong>Current Banner:</strong>
                        <a href="<%= request.getContextPath() %>/uploads/events/<%= event.getBannerImagePath() %>" target="_blank">
                            <%= event.getBannerImagePath() %>
                        </a>
                    </p>
                <% } %>

                <input type="file" name="bannerImagePath" accept=".jpg,.png,image/jpeg,image/png">

                <input type="text" name="eventTitle"
                       value="<%= event.getEventTitle() %>" required>

                <textarea name="eventDesc" rows="4" required>
                    <%= event.getEventDesc() %>
                </textarea>

                <div class="form-row">
                    <input type="date" name="eventDate"
                           value="<%= event.getEventDate() %>" required>

                    <%
                        String timeValue = event.getEventTime().toString().substring(0,5);
                    %>

                    <input type="time" name="eventTime"
                           value="<%= timeValue %>" required>
                </div>

                <input type="text" name="eventLoc"
                       value="<%= event.getEventLoc() %>" required>

                <% if (event.getQrPath() != null && !event.getQrPath().isEmpty()) { %>
                    <p>
                        <strong>Current Attendance QR:</strong>
                        <a href="<%= request.getContextPath() %>/uploads/events/<%= event.getQrPath() %>" target="_blank">
                            <%= event.getQrPath() %>
                        </a>
                    </p>
                <% } %>

                <input type="file" name="qrPath" accept=".jpg,.png,image/jpeg,image/png">

                <div class="form-actions">
                    <button type="submit" class="submit-btn">
                        Update Event
                    </button>
                </div>

            </form>
        </div>
    </div>

    </body>
</html>
