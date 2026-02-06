<%-- 
    Document   : myProfile
    Created on : 24 Jan 2026
    Author     : Hasina
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    User student = (User) session.getAttribute("user");
    List<Event> events = (List<Event>) request.getAttribute("events");
    
    if (student == null) {
        response.sendRedirect("login.jsp");
        return;
    }

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
                            <%
                                if (student.getProfileImage() != null) {
                            %>
                                <div class="profile-avatar">
                                    <img id="previewImg"
                                       src="<%= request.getContextPath() %>/profileImage?id=<%= student.getUserId() %>"
                                        width="120"
                                        onerror="this.src='<%= request.getContextPath() %>/images/default-profile.png'">
                                </div>

                            <%
                                } else {
                            %>
                                <img src="<%= request.getContextPath() %>/images/default-profile.png"
                                     width="120">
                            <%
                                }
                            %>
                        </div>


                        <!-- VIEW MODE -->
                        <div id="profileView">
                            <p><strong>Full Name</strong><br><%= student.getFullName() %></p>
                            <p><strong>Student ID</strong><br><%= student.getUserId() %></p>
                            <p><strong>Course</strong><br>
                                <%= (student.getCourse() == null || student.getCourse().trim().isEmpty())
                                    ? " - "
                                    : student.getCourse() %>
                            </p>

                            <p><strong>Faculty</strong><br>
                                <%= (student.getFaculty() == null || student.getFaculty().trim().isEmpty())
                                    ? " - "
                                    : student.getFaculty() %>
                            </p>
                            <p><strong>Email</strong><br><%= student.getEmail() %></p>

                            <div class="profile-actions">
                                <button class="edit-btn" onclick="toggleEdit(true)">Edit Profile</button>
                                <button class="leave-btn">Leave Club</button>
                            </div>
                        </div>

                        <!-- EDIT MODE (inline) -->
                        <form
                            id="profileEdit"
                            action="${pageContext.request.contextPath}/UpdateProfileServlet"
                            method="post"
                            enctype="multipart/form-data"
                            style="display:none;"
                        >
                            <label>Profile Picture</label>
                            <input type="file" name="profilePic" accept="image/*">

                            <label>Full Name</label>
                            <input type="text" name="fullName"
                                   value="<%= student.getFullName() %>" required>

                            <label>Email</label>
                            <input type="email" name="email"
                                   value="<%= student.getEmail() %>" required>

                            <label>Course</label>
                            <input type="text" name="course"
                                   value="<%= student.getCourse() %>">

                            <label>Faculty</label>
                            <input type="text" name="faculty"
                                   value="<%= student.getFaculty() %>">

                            <label>New Password</label>
                            <input type="password" name="password" id="password"
                                   placeholder="Leave blank if unchanged">

                            <label>Confirm Password</label>
                            <input type="password" name="confirmPassword" id="confirmPassword"
                                   placeholder="Re-enter new password">

                            <div class="profile-actions">
                                <button type="submit" class="save-btn">Save</button>
                                <button type="button" class="cancel-btn"
                                        onclick="toggleEdit(false)">Cancel</button>
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
                                <span>Event Name</span>
                                <span>Role</span>
                                <span>Date</span>
                            </div>

                            <% if (events != null && !events.isEmpty()) { 
                                for (Event e : events) { %>
                                    <div class="event-row">
                                        <span><%= e.getEventTitle() %></span>
                                        <span>Member</span>
                                        <%
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        %>
                                        <span><%= sdf.format(e.getEventDate()) %></span>

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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <script>
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: '<%= error %>'
                });
            </script>
            <%
                }
            %>
            
            <!-- Success Alert -->
            <%
                String success = (String) session.getAttribute("success");
                if (success != null) {
                    session.removeAttribute("success");
                %>
                <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Success',
                    text: '<%= success %>'
                });
                </script>
                <% } %>
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
    
    document.getElementById("profileEdit").onsubmit = function() {
        let p = document.getElementById("password").value;
        let c = document.getElementById("confirmPassword").value;

        if (p !== "" && p !== c) {
            alert("Passwords do not match!");
            return false;
        }
    };
    
    document.querySelector("input[name='profilePic']")
    .addEventListener("change", function(event) {

        const file = event.target.files[0];
        if (!file) return;

        const reader = new FileReader();

        reader.onload = function(e) {
            document.getElementById("previewImg").src = e.target.result;
        };

        reader.readAsDataURL(file);
    });
    
    document.querySelectorAll("button").forEach(btn => {
        btn.addEventListener("click", function (e) {
            const ripple = document.createElement("span");
            ripple.classList.add("ripple");

            const rect = btn.getBoundingClientRect();
            ripple.style.left = (e.clientX - rect.left) + "px";
            ripple.style.top = (e.clientY - rect.top) + "px";

            btn.appendChild(ripple);

            setTimeout(() => ripple.remove(), 500);
        });
    });
</script>
