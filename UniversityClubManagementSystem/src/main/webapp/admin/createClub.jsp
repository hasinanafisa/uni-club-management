<%-- 
    Document   : createClub
    Created on : 28 Jan 2026, 2:09:07 am
    Author     : izyan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Club</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
</head>

<body class="no-sidebar">

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">CREATE CLUB</div>
</div>

<div class="home-page">
    <div class="home-container">

        <h1>Create Your Club</h1>
        <p class="subtitle">
            This is a one-time setup. You can edit details later.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <p style="color:red; text-align:center;">
                <%= request.getAttribute("error") %>
            </p>
        <% } %>

        <form class="create-event-form"
              action="${pageContext.request.contextPath}/CreateClubServlet"
              method="post">

            <label>Club Name *</label>
            <input type="text" name="clubName" required>

            <label>Description</label>
            <textarea name="description" rows="4"></textarea>

            <div class="form-actions">
                <button type="submit" class="submit-btn">
                    Create Club
                </button>
            </div>
        </form>

    </div>
</div>

</body>
</html>
