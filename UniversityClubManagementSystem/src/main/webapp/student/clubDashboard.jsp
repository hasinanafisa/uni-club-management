<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Club"%>
<%@page import="java.util.List"%>
<%@page import="model.Task"%>

<%
    Club club = (Club) request.getAttribute("club");
    if (club == null) {
        response.sendRedirect(request.getContextPath() + "/student/clubs");
        return;
    }

    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= club.getClubName() %> | Club Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<%@include file="/includes/header.jsp" %>

<div class="notion-wrapper">

    <h1><%= club.getClubName() %> Dashboard</h1>
    <p class="subtitle">Member-only club space</p>

    <!-- STATS -->
    <div class="stats-row">
        <div class="stat-box">
            <span>Members</span>
            <h2>${memberCount}</h2>
        </div>

        <div class="stat-box">
            <span>Upcoming Events</span>
            <h2>${upcomingEventCount}</h2>
        </div>

        <div class="stat-box">
            <span>Announcements</span>
            <h2>${announcementCount}</h2>
        </div>
    </div>

    <!-- MAIN GRID -->
    <div class="notion-grid">

        <!-- TASKS -->   
    <div class="panel">
            <h3>My Tasks</h3>

            <!-- ADD TASK -->
            <form action="${pageContext.request.contextPath}/student/addTask" method="post">
                <input type="hidden" name="clubId" value="<%= club.getClubID() %>">
                <input type="text" name="title" placeholder="New task" required>
                <button type="submit" class="join-btn">Add Task</button>
            </form>

            <ul class="task-list">
                <%
                    if (tasks == null || tasks.isEmpty()) {
                %>
                    <li>No tasks yet.</li>
                <%
                    } else {
                        for (Task t : tasks) {
                %>
                <li class="task-item ${t.status == 'Done' ? 'done' : ''}">
                    <span class="task-title"><%= t.getTitle() %></span>
                    <small class="task-status"><%= t.getStatus() %></small>

                    <div class="task-actions">
                        <% if (!"Done".equals(t.getStatus())) { %>
                            <form action="${pageContext.request.contextPath}/student/taskAction" method="post">
                                <input type="hidden" name="taskId" value="<%= t.getTaskId() %>">
                                <input type="hidden" name="action" value="done">
                                <button type="submit" class="task-btn done-btn">Done</button>
                            </form>
                        <% } %>

                        <form action="${pageContext.request.contextPath}/student/taskAction" method="post">
                            <input type="hidden" name="taskId" value="<%= t.getTaskId() %>">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="task-btn delete-btn">Delete</button>
                        </form>
                    </div>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
    </div>
</div>
</body>
</html>