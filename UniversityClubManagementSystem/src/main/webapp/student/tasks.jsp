<%-- 
    Document   : tasks
    Created on : 25 Dec 2025, 6:58:29?pm
    Author     : Hasina
--%>

<%@ page import="java.util.*, model.Task" %>
<html>
<head>
    <title>My Tasks</title>
</head>
<body>

<h2>My Tasks</h2>

<table border="1">
<tr>
    <th>Title</th>
    <th>Description</th>
    <th>Due</th>
    <th>Status</th>
    <th>Action</th>
</tr>

<%
    List<Task> list = (List<Task>) request.getAttribute("taskList");
    for (Task t : list) {
%>
<tr>
    <td><%= t.getTitle() %></td>
    <td><%= t.getDescription() %></td>
    <td><%= t.getDueDate() %></td>
    <td><%= t.getStatus() %></td>
    <td>
        <form method="post" action="../TaskServlet">
            <input type="hidden" name="taskId" value="<%= t.getTaskId() %>">
            <select name="status">
                <option>PENDING</option>
                <option>IN_PROGRESS</option>
                <option>COMPLETED</option>
            </select>
            <input type="submit" value="Update">
        </form>
    </td>
</tr>
<% } %>

</table>

</body>
</html>