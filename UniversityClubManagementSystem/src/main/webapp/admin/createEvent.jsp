<%-- 
    Document   : createEvent
    Created on : 27 Dec 2025, 8:34:45 pm
    Author     : izyanie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Create New Event</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body class="no-sidebar">
        <!-- ===== TOAST NOTIFICATION ===== -->
        <div id="toast" class="toast"></div>

        <!-- ===== NAVBAR ===== -->
        <div class="navbar">
            <div style="display:flex; align-items:center;">
                <div class="logo">CREATE NEW EVENT</div>
            </div>

            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/admin/manageEvent">Back</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/adminHome.jsp">Home</a></li>
            </ul>
        </div>

        <!-- ===== MAIN CONTENT ===== -->
        <div class="home-page">
            <div class="home-container">
                <h1>Create New Event</h1>
                <p class="subtitle">Fill in all event details below</p>

                <form class="create-event-form" action="${pageContext.request.contextPath}/admin/createEvent" 
                      method="post" enctype="multipart/form-data">
                    
                    <label>Banner Image *</label>
                    <input type="file" name="bannerImagePath" accept=".jpg,.png,image/jpeg,image/png">
                    
                    <label>Title *</label>
                    <input type="text" name="eventTitle" required>
                    
                    <label>Description *</label>
                    <textarea name="eventDesc" rows="4" required></textarea>
                    
                    <div class="form-row">
                        <input type="date" name="eventDate" required>
                        <input type="time" name="eventTime" required>
                    </div>
                    
                    <label>Location *</label>
                    <input type="text" name="eventLoc" required>
                    
                    <div class="info-box">
                        <i class="fa-solid fa-qrcode"></i>
                        Attendance QR will be generated automatically after event creation.
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="submit-btn">
                            Create Event
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>