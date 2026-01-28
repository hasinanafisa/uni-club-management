<%-- 
    Document   : clubMembers
    Created on : 1 Jan 2026, 11:09:36 am
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/includes/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Club Members</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<div class="home-page">
    <div class="home-container">

        <h1>Club Members</h1>
        <p class="subtitle">List of registered members</p>

        <!-- SEARCH BOX -->
        <div class="search-filter-bar">
            <div class="search-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" id="searchInput" placeholder="Search by name..." onkeyup="searchMembers()">
            </div>
        </div>

        <!-- MEMBER CARDS -->
        <div class="card-container" id="membersContainer">
            <div class="card member-card">
                <i>👤</i>
                <p class="member-name">Ali Abu</p>
                <p>President</p>
            </div>

            <div class="card member-card">
                <i>👤</i>
                <p class="member-name">Siti Aminah</p>
                <p>Secretary</p>
            </div>

            <div class="card member-card">
                <i>👤</i>
                <p class="member-name">Ahmad Zaki</p>
                <p>Member</p>
            </div>
        </div>

    </div>
</div>

<!-- JAVASCRIPT SEARCH FUNCTION -->
<script>
function searchMembers() {
    const input = document.getElementById("searchInput").value.toLowerCase();
    const cards = document.getElementsByClassName("member-card");

    for (let i = 0; i < cards.length; i++) {
        const name = cards[i].getElementsByClassName("member-name")[0].innerText.toLowerCase();
        if (name.includes(input)) {
            cards[i].style.display = "";  // show card
        } else {
            cards[i].style.display = "none";  // hide card
        }
    }
}
</script>

</body>
</html>