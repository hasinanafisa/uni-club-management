<%-- 
    Document   : announcement
    Created on : 24 Jan 2026, 11:39:12 pm
    Author     : Hasina
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.Announcement"%>
<%
List<Announcement> announcements =
        (List<Announcement>) request.getAttribute("announcements");

if (announcements == null) {
    announcements = new ArrayList<>();
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Announcements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script>
        function filterAnnouncements() {
            let search = document.getElementById("searchInput").value.toLowerCase();
            let category = document.getElementById("categoryFilter").value;
            let cards = document.querySelectorAll(".card");

            cards.forEach(card => {
                let title = card.dataset.title;
                let cardCategory = card.dataset.category;

                let matchSearch = title.includes(search);
                let matchCategory = (category === "ALL" || cardCategory === category);

                card.style.display = (matchSearch && matchCategory) ? "block" : "none";
            });
        }
    </script>
</head>
<body>

<%@include file="/includes/header.jsp" %>


<div class="home-page">
    <div class="home-container">
        <h1>📢 Announcements</h1>
        <p class="subtitle">Latest updates from clubs</p>

        <!-- < %
            List<Announcement> announcements =
                (List<Announcement>) request.getAttribute("announcements");
            if (announcements == null) {
                announcements = new ArrayList<>();
            }
        %>-->
        
        <div class="filter-bar">
            <input type="text" id="searchInput"
                   placeholder="Search announcements..."
                   onkeyup="filterAnnouncements()">

            <select id="categoryFilter" onchange="filterAnnouncements()">
                <option value="ALL">All</option>
                <option value="IMPORTANT">Important</option>
                <option value="GENERAL">General</option>
                <option value="EVENT">Events</option>
            </select>
        </div>


        <div class="card-container">
            <% for (Announcement a : announcements) { %>
                <div class="card"
                     data-title="<%= a.getTitle().toLowerCase() %>"
                     data-category="<%= a.getCategory() %>">

                    <i class="fa-solid 
                        <%= a.getCategory().equalsIgnoreCase("IMPORTANT") ? "fa-circle-exclamation" :
                            a.getCategory().equalsIgnoreCase("EVENT") ? "fa-calendar-days" :
                            "fa-bullhorn" %>"></i>

                    <h3><%= a.getTitle() %></h3>

                    <p>
                        <%= a.getContent().length() > 100
                            ? a.getContent().substring(0,100) + "..."
                            : a.getContent()
                        %>
                    </p>

                    <div class="card-actions">
                        <a href="${pageContext.request.contextPath}/student/announcementDetails?id=<%= a.getAnnouncementId() %>">
                            Read more
                        </a>
                    </div>
                </div>
            <% } %>

            <% if (announcements.isEmpty()) { %>
                <p style="text-align:center; color:#555;">
                    No announcements available.
                </p>
            <% } %>
        </div>

    </div>
</div>

</body>
</html>
