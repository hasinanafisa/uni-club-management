<%-- 
    Document   : announcement
    Created on : 24 Jan 2026, 11:39:12 pm
    Author     : Hasina
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.Announcement"%>
<!-- dummy data start -->
<%
List<Announcement> announcements = new ArrayList<>();

Announcement a1 = new Announcement();
a1.setAnnouncementId(1);
a1.setTitle("Club Registration Open");
a1.setContent("All students are welcome to register for clubs starting this week.");
a1.setCategory("GENERAL");

Announcement a2 = new Announcement();
a2.setAnnouncementId(2);
a2.setTitle("Important: AGM Meeting");
a2.setContent("Attendance is compulsory for all committee members.");
a2.setCategory("IMPORTANT");

Announcement a3 = new Announcement();
a3.setAnnouncementId(3);
a3.setTitle("Sports Day 2026");
a3.setContent("Join us for inter-faculty sports competitions this March.");
a3.setCategory("EVENT");

announcements.add(a1);
announcements.add(a2);
announcements.add(a3);
%> <!-- dummy data end -->

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
                   <%= a.getCategory().equals("IMPORTANT") ? "fa-circle-exclamation" :
                       a.getCategory().equals("EVENT") ? "fa-calendar-days" :
                       "fa-bullhorn" %>"></i>

               <h3><%= a.getTitle() %></h3>

               <p>
                   <%= a.getContent().length() > 100
                       ? a.getContent().substring(0,100) + "..."
                       : a.getContent()
                   %>
               </p>

               <div class="card-actions">
                   <a href="announcementDetails.jsp?id=<%= a.getAnnouncementId() %>"
                         class="view-btn">Read More</a>
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
