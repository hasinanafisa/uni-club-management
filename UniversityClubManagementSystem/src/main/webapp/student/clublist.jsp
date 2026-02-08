<%-- 
    Document   : clublist
    Created on : 26 Dec 2025, 10:01:26 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Club"%>
<%@page import="model.User"%>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Club> clubs = (List<Club>) request.getAttribute("clubs");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Club List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<%@include file="/includes/header.jsp" %>

<div class="home-page">
    <div class="home-container">
        <h1>Explore Clubs</h1>
        <p class="subtitle">Join clubs that match your passion</p>

        <div class="card-container">

            <% if (clubs != null && !clubs.isEmpty()) {
                for (Club c : clubs) {
            %>
                <div class="card">
                    <img src="${pageContext.request.contextPath}/clubImage?id=<%= c.getClubId() %>"
                        alt="Club Logo" class="club-logo">

                    <h3><%= c.getClubName() %></h3>
                    <p><%= c.getDescription() %></p>

                    <div class="card-actions">
                        <a href="${pageContext.request.contextPath}/student/club?clubId=<%= c.getClubId() %>"class="view-btn">View Details</a>
                        <form action="${pageContext.request.contextPath}/student/joinClub" method="post">
                            <input type="hidden" name="clubId" value="<%= c.getClubId() %>">

                            <%
                                dao.ClubMemberDAO cmDAO = new dao.ClubMemberDAO();
                                boolean joined = cmDAO.isMember(user.getUserId(), c.getClubId());
                            %>

                            <button type="submit"
                                    class="join-btn"
                                    <%= joined ? "disabled" : "" %>>
                                <%= joined ? "Joined" : "Join" %>
                            </button>
                        </form>
                    </div>
                </div>
            <% }} else { %>
                <p style="text-align:center;">No clubs available.</p>
            <% } %>

        </div>
    </div>
</div>

</body>
</html>