<%-- 
    Document   : register
    Created on : 26 Dec 2025, 10:08:07 pm
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="login-page">

<div class="container">
    <h2><i class="fa-solid fa-user-plus"></i> Create Account</h2>

    <form action="register" method="post">
        <input type="text" name="fullname" placeholder="Full Name" required>

        <input type="email" name="email" placeholder="Email Address" required>

        <input type="password" name="password" placeholder="Password" required>

        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>

        <button type="submit">Register</button>
    </form>

    <p style="text-align:center; margin-top:15px;">
        Already have an account?
        <a href="login.jsp">Login here</a>
    </p>
</div>

</body>
</html>
