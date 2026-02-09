<%-- 
    Document   : editClubDetails
    Created on : 30 Jan 2026, 11:04:51 pm
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Club"%>

<%
    Club club = (Club) request.getAttribute("club");
    if (club == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manageClubDetails?clubId=" + club.getClubId());
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Edit Club Details</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
    </head>

    <body class="no-sidebar">
        <!-- ===== NAVBAR ===== -->
        <div class="navbar">
            <div class="logo">EDIT CLUB DETAILS</div>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/admin/manageClubDetails">Back</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
            </ul>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">

                <h1>Edit Club Details</h1>
                <p class="subtitle">Update club information below</p>

                <form class="create-event-form"
                      action="${pageContext.request.contextPath}/admin/editClubDetails"
                      method="post"
                      enctype="multipart/form-data">

                    <!-- Hidden club id -->
                    <input type="hidden" name="clubId" value="<%= club.getClubId() %>">

                    <!-- Club Logo -->
                    <label>Club Logo</label>

                    <% if (club.getLogoPath() != null && !club.getLogoPath().contains("default")) { %>
                        <div style="margin-bottom:15px;">
                            <img src="${pageContext.request.contextPath}/clubImage?id=<%= club.getClubId() %>"
                                style="width:120px; height:120px; object-fit:cover; border-radius:12px;">
                        </div>
                    <% } %>

                    <input type="file" name="logoPath" accept=".jpg,.png,image/jpeg,image/png">

                    <!-- Club Name -->
                    <label>Club Name *</label>
                    <input type="text" name="clubName"
                           value="<%= club.getClubName() %>" required>

                    <!-- Description -->
                    <label>Description</label>
                    <textarea name="description" rows="4"><%= 
                        club.getDescription() != null ? club.getDescription() : "" 
                    %></textarea>

                    <!-- Mission -->
                    <label>Mission</label>
                    <textarea name="mission" rows="3"><%= 
                        club.getMission() != null ? club.getMission() : "" 
                    %></textarea>

                    <!-- Achievements -->
                    <label>Achievements</label>
                    <textarea name="achievements" rows="3"><%= 
                        club.getAchievements() != null ? club.getAchievements() : "" 
                    %></textarea>

                    <!-- Actions -->
                    <div class="form-actions">
                        <button type="submit" class="submit-btn">
                            Save Changes
                        </button>
                    </div>

                </form>
            </div>
        </div>

    </body>
</html>
