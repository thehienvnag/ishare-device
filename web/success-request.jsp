<%-- 
    Document   : success-request
    Created on : Jul 2, 2020, 2:05:13 AM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success Request Page</title>
    </head>
    <body>
        <jsp:include page="components/header.jsp" />
        <div class="container mt-5 p-2" style="max-width: 550px; min-width: 350px">
            <div class="alert alert-success">
                <h3>Success Sending Request</h3>
                <p>View your request status: </p><a class="btn btn-primary px-5" href="request-history">Here</a>
            </div>
        </div>
    </body>
</html>
