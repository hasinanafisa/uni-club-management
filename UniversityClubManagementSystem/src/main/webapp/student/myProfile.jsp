<%-- 
    Document   : myProfile
    Created on : 24 Jan 2026
    Author     : Hasina
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Event" %>
<%@ page import="java.util.List" %>

<%
    User student = (User) session.getAttribute("user");
    List<Event> events = (List<Event>) request.getAttribute("events");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>My Profile</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
    <body>
    <%@include file="/includes/header.jsp" %>

        <div class="page-wrapper">
            <div class="container profile-page">

                <h1>My Profile</h1>
                <p class="subtitle">Manage your information & activities</p>

                <!-- PROFILE GRID -->
                <div class="profile-grid">

                    <div class="profile-card profile-left-card">
                        <!-- Profile picture -->
                        <div class="profile-avatar">
                            
                        </div>
                                 
                        <!-- VIEW MODE -->
                        <div id="profileView">
                            <p><strong>Full Name</strong><br><%= student.getFullName() %></p>
                            <p><strong>Student ID</strong><br><%= student.getUserId() %></p>
                            <p><strong>Course & Faculty</strong><br><%= student.getCourse() %></p>
                            <p><strong>Join Date</strong><br><!-- student.getJoinDate() --></p>
                            <p><strong>Email</strong><br><%= student.getEmail() %></p>

                            <div class="profile-actions">
                                <button class="edit-btn" onclick="toggleEdit(true)">Edit Profile</button>
                                <button class="leave-btn">Leave Club</button>
                            </div>
                        </div>

                        <!-- EDIT MODE (inline) -->
                        <form
                            id="profileEdit"
                            action="<%= request.getContextPath() %>/UpdateProfileServlet"
                            method="post"
                            enctype="multipart/form-data"
                            style="display:none;"
                        >
                            <label>Profile Picture</label>
                            <input type="file" name="profilePic" accept="image/*" onchange="previewProfilePic(event)">

                            <label>Full Name</label>
                            <input type="text" name="fullName" value="<%= student.getFullName() %>" required>

                            <label>Email</label>
                            <input type="email" name="email" value="<%= student.getEmail() %>" required>

                            <div class="profile-actions">
                                <button type="submit" class="save-btn">Save</button>
                                <button type="button" class="cancel-btn" onclick="toggleEdit(false)">Cancel</button>
                            </div>
                        </form>
                    </div>

                    <!-- RIGHT CARD : EVENTS -->
                    <div class="profile-card">
                        <div class="events-header">
                            <h3>📅 Events Joined</h3>
                            <select class="event-filter">
                                <option>All</option>
                                <option>Current Event</option>
                                <option>Past Event</option>
                            </select>
                        </div>

                        <div class="events-list">
                            <div class="events-title">
                                Event Name | Role | Date
                            </div>

                            <% if (events != null && !events.isEmpty()) { 
                                for (Event e : events) { %>
                                <div class="event-row">
                                    <span><%= e.getEventTitle() %></span>
                                    <span>Member</span>
                                    <span><%= e.getEventDate() %></span>
                                </div>
                            <%  } 
                            } else { %>
                                <p class="no-events">No events joined yet.</p>
                            <% } %>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
<script>
    function cancelEdit() {
        document.getElementById("profileEdit").style.display = "none";
        document.getElementById("profileView").style.display = "block";
    }

    function toggleEdit(edit) {
        document.getElementById("profileView").style.display = edit ? "none" : "block";
        document.getElementById("profileEdit").style.display = edit ? "block" : "none";
    }

</script>
