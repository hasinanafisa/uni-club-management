<%-- 
    Document   : login
    Created on : 26 Dec 2025, 5:59:35 pm
    Author     : Razan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="login-page">

<div class="container">
    <h2><i class="fa-solid fa-user-lock"></i> Login</h2>

    <form action="login" method="post">
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>
    
    <p style="text-align:center; margin-top:15px;">
        <a href="register.jsp">Create an account</a> |
        <a href="forgot-password.jsp">Forgot password?</a>
    </p> 
</div>

</body>
</html>