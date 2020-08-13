<%-- 
    Document   : verify-email
    Created on : Jul 11, 2020, 8:28:52 AM
    Author     : thehien
--%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Email Page</title>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <div class="container alert alert-success p-4" style="width: 500px; margin-top: 50px">
            <h3>Verify email</h3>
            <p>Please check your email for verification</p>
            <form action="verify-email" class="mt-2 p-2">
                <input class="form-control" type="text" name="code" value="" />
                <button class="btn btn-success mt-2" >Verify Now</button>
            </form>
            <s:if test="%{errMsg != null}">
                <p class="alert alert-danger"><s:property value="errMsg"/></p>
            </s:if>
        </div>
    </body>
</html>
