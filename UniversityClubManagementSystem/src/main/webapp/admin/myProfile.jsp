<%-- 
    Document   : adminProfile
    Created on : 9 Feb 2026, 8:16:00 pm
    Author     : izyan
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"Lecturer".equals(user.getUserType())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile | Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div style="display:flex; align-items:center;">
        <i class="fa-solid fa-bars menu-toggle" onclick="toggleSidebar()"></i>
        <div class="logo">MY PROFILE</div>
    </div>
    <ul class="nav-links">
        <li><a href="<%= request.getContextPath() %>/admin/home">Back</a></li>
        <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
    </ul>
</div>

<!-- SIDEBAR -->
<div class="sidebar">
    <a href="${pageContext.request.contextPath}/admin/home">
        <i class="fa-solid fa-house"></i>Home
    </a>
    <a href="${pageContext.request.contextPath}/admin/manageClubDetails">
        <i class="fa-solid fa-gear"></i>Manage Club Details
    </a>
    <a href="${pageContext.request.contextPath}/admin/manageEvent">
        <i class="fa-solid fa-calendar-days"></i>Manage Event
    </a>
    <a href="${pageContext.request.contextPath}/admin/manageAnnouncement">
        <i class="fa-solid fa-bullhorn"></i>Manage Announcement
    </a>
    <a class="active-link">
        <i class="fa-solid fa-user"></i>My Profile
    </a>
</div>

<!-- MAIN CONTENT -->
<div class="page-wrapper">
    <div class="container">

        <div class="section-card">

            <h1>My Profile</h1>
            <p class="subtitle">Manage your personal information</p>

            <!-- PROFILE CARD -->
            <div class="profile-wrapper">
                <!-- VIEW MODE -->
                <div class="profile-view" id="profileView">

                    <div class="profile-header-centered">
                        <div class="profile-avatar-wrapper">
                            <img src="<%= request.getContextPath()%>/profileImage?id=<%= user.getUserId()%>"
                                 onerror="this.src='<%= request.getContextPath()%>/images/default-profile.png'">
                        </div>

                        <h2 class="profile-name"><%= user.getFullName()%></h2>
                    </div>

                    <div class="profile-info-grid">
                        <div>
                            <label>User Type</label>
                            <span class="role-badge lecturer">Lecturer</span>
                        </div>

                        <div>
                            <label>Faculty</label>
                            <span>
                                <%= user.getFaculty() == null || user.getFaculty().isBlank() ? "—" : user.getFaculty()%>
                            </span>
                        </div>

                        <div>
                            <label>Email</label>
                            <span><%= user.getEmail()%></span>
                        </div>
                    </div>

                    <button class="edit-btn" onclick="toggleEdit(true)">Edit Profile</button>
                </div>

                <!-- EDIT MODE -->
                <form id="profileEdit"
                      action="${pageContext.request.contextPath}/UpdateAdminProfileServlet"
                      method="post"
                      enctype="multipart/form-data"
                      class="profile-edit-form"
                      style="display:none;">

                    <!-- AVATAR -->
                    <div class="avatar-section">
                        <label class="avatar-upload">
                            <img id="previewImg"
                                 src="<%= request.getContextPath() %>/profileImage?id=<%= user.getUserId() %>
                                        &t=<%= System.currentTimeMillis() %>"
                                 onerror="this.src='<%= request.getContextPath()%>/images/default-profile.png'">
                            <span class="avatar-overlay">Change photo</span>
                            <input type="file" name="profilePic" accept="image/*" hidden>
                        </label>
                    </div>

                    <!-- NAME + FACULTY -->
                    <div class="edit-grid">
                        <div>
                            <label>Full Name</label>
                            <input type="text" name="fullName"
                                   value="<%= user.getFullName()%>" required>
                        </div>

                        <div>
                            <label>Faculty</label>
                            <input type="text" name="faculty"
                                   value="<%= user.getFaculty() == null ? "" : user.getFaculty()%>">
                        </div>
                    </div>

                    <hr class="profile-divider">

                    <!-- EMAIL -->
                    <div class="single-field">
                        <label>Email</label>
                        <input type="email"
                               value="<%= user.getEmail()%>"
                               disabled
                               class="disabled-input input-like">
                        <small class="field-hint">Email cannot be changed</small>
                    </div>

                    <!-- PASSWORD -->
                    <div class="edit-grid">
                        <div>
                            <label>New Password</label>
                            <input type="password" name="password"
                                   placeholder="Leave blank if unchanged">
                        </div>

                        <div>
                            <label>Confirm Password</label>
                            <input type="password" name="confirmPassword">
                        </div>
                    </div>

                    <div class="edit-actions">
                        <button type="submit" class="submit-btn">Save Changes</button>
                        <button type="button" class="cancel-btn" onclick="toggleEdit(false)">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
function toggleSidebar() {
    document.querySelector('.sidebar').classList.toggle('collapsed');
    document.body.classList.toggle('sidebar-collapsed');
}

function toggleEdit(edit) {
    document.getElementById("profileView").style.display = edit ? "none" : "block";
    document.getElementById("profileEdit").style.display = edit ? "block" : "none";
}

document.querySelector("input[name='profilePic']")
?.addEventListener("change", e => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = ev => document.getElementById("previewImg").src = ev.target.result;
    reader.readAsDataURL(file);
});
</script>

</body>
</html>
