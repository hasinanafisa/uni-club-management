<%-- 
    Document   : reports
    Created on : 9 Feb 2026, 7:11:00 pm
    Author     : izyan
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Event" %>

<!DOCTYPE html>
<html>
<head>
    <title>Event Participation Report</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body class="no-sidebar">
    <!-- NAVBAR -->
    <div class="navbar">
        <div style="display:flex; align-items:center;">
            <div class="logo">CLUB INSIGHTS</div>
        </div>
        <ul class="nav-links">
            <li>
                <a href="${pageContext.request.contextPath}/admin/home">Back</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
            </li>
        </ul>
    </div>

    <%
        List<Event> events =
            (List<Event>) request.getAttribute("events");

        Map<String, Integer> participationMap =
            (Map<String, Integer>) request.getAttribute("participationMap");

        boolean hasData = (events != null && !events.isEmpty());
    %>

    <div class="main-container">

        <!-- 📊 TABLE CARD -->
        <div class="card-box">
            <h2 class="section-title">📊 Event Participation Report</h2>

            <% if (!hasData) { %>
                <div class="empty-box">
                    No event participation data available yet.
                </div>
            <% } else { %>

                <table class="report-table">
                    <thead>
                        <tr>
                            <th>Event Name</th>
                            <th>Event Date</th>
                            <th>Participants</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Event e : events) { %>
                        <tr>
                            <td><%= e.getEventTitle() %></td>
                            <td><%= e.getEventDate() %></td>
                            <td>
                                <%= participationMap.getOrDefault(
                                        e.getEventTitle(), 0) %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

            <% } %>
        </div>

        <!-- 📈 CHART CARD -->
        <% if (hasData) { %>
        <div class="card-box">
            <h2 class="section-title">📈 Participation by Event</h2>

            <div class="chart-wrapper">
                <canvas id="eventChart"></canvas>
            </div>
        </div>
        <% } %>

    </div>

    <!-- CHART SCRIPT -->
    <% if (hasData) { %>
    <script>
        const labels = [
            <% for (String title : participationMap.keySet()) { %>
                "<%= title %>",
            <% } %>
        ];

        const data = [
            <% for (int count : participationMap.values()) { %>
                <%= count %>,
            <% } %>
        ];

        new Chart(document.getElementById("eventChart"), {
            type: "bar",
            data: {
                labels: labels,
                datasets: [{
                    label: "Participants",
                    data: data,
                    backgroundColor: "#93c5fd",
                    borderColor: "#3b82f6",
                    borderWidth: 2,
                    borderRadius: 8,
                    barThickness: 50
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false   // 👈 IMPORTANT
                    },
                    tooltip: {
                        enabled: true
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1,
                            precision: 0
                        },
                        grid: {
                            color: "#e5e7eb"
                        }
                    }
                }
            }
        });
    </script>
    <% } %>
</body>
</html>
