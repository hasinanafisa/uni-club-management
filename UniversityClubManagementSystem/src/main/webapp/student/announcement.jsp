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
a1.setAnnounceID(1);
a1.setAnnounceTitle("Club Registration Open");
a1.setAnnounceContent("All students are welcome to register for clubs starting this week.");
a1.setAnnounceCategory("GENERAL");

Announcement a2 = new Announcement();
a2.setAnnounceID(2);
a2.setAnnounceTitle("Important: AGM Meeting");
a2.setAnnounceContent("Attendance is compulsory for all committee members.");
a2.setAnnounceCategory("IMPORTANT");

Announcement a3 = new Announcement();
a3.setAnnounceID(3);
a3.setAnnounceTitle("Sports Day 2026");
a3.setAnnounceContent("Join us for inter-faculty sports competitions this March.");
a3.setAnnounceCategory("EVENT");

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
                data-title="<%= a.getAnnounceTitle().toLowerCase() %>"
                data-category="<%= a.getAnnounceCategory() %>">

               <i class="fa-solid 
                   <%= a.getAnnounceCategory().equals("IMPORTANT") ? "fa-circle-exclamation" :
                       a.getAnnounceCategory().equals("EVENT") ? "fa-calendar-days" :
                       "fa-bullhorn" %>"></i>

               <h3><%= a.getAnnounceTitle() %></h3>

               <p>
                   <%= a.getAnnounceContent().length() > 100
                       ? a.getAnnounceContent().substring(0,100) + "..."
                       : a.getAnnounceContent()
                   %>
               </p>

               <div class="card-actions">
                   <a href="announcementDetails.jsp?id=<%= a.getAnnounceID() %>"
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
