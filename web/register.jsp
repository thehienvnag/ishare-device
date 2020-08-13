<%-- 
    Document   : register
    Created on : Jun 27, 2020, 9:47:53 AM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>

        <style>
            .error{
                padding: 2%;
                width: 100%;
                background-color: #F8D7DA;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <div class="container card mt-5 p-4" style="max-width: 500px">
            <h2 class="text-center">Register Page</h2>
            <s:form id="registerForm" action="register-user" theme="simple" validate="true">
                <s:textfield placeholder="Email" cssClass="form-control mb-2" name="user.email" value="%{user.email}"/> 
                <s:textfield placeholder="Name" cssClass="form-control mb-2" name="user.name" value="%{user.name}"/>
                <div class="form-row">
                    <div class="col-md-6">
                        <s:password placeholder="Password" cssClass="form-control mb-2" id="txtPassword" name="user.password" value="%{user.password}"/> 
                    </div>
                    <div class="col-md-6">
                        <s:password placeholder="Confirm Password" cssClass="form-control mb-2" id="txtConfirm" name="confirm" value="%{confirm}"/> 
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6">
                        <s:textfield placeholder="Address" cssClass="form-control mb-2" name="user.address" value="%{user.address}"/>
                    </div>
                    <div class="col-md-6">
                        <s:textfield placeholder="Phone" cssClass="form-control mb-2" name="user.phone" value="%{user.phone}"/> 
                    </div>         
                </div>
                <input class="btn btn-primary mt-2 px-4" type="submit" value="Register" />
                <input type="reset" id="btnReset" class="btn btn-light border mt-2 px-4"/>
            </s:form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
        <script>
            $(function () {
                jQuery.validator.methods.matches = function (value, element, regex) {
                    return this.optional(element) || regex.test(value);
                }
                const validator = $('#registerForm').validate({
                    ignore: [], // initialize the plugin
                    rules: {
                        "user.email": {
                            required: true,
                            email: true
                        },
                        "user.name": {
                            required: true,
                            minlength: 5,
                            maxlength: 50,
                        },
                        "user.password": {
                            required: true,
                            minlength: 8,
                            maxlength: 50,
                        },
                        "confirm": {
                            required: true,
                            equalTo: "#txtPassword"
                        },
                        "user.phone": {
                            required: true,
                            matches: /^0[0-9]{9}$/
                        },
                        "user.address": {
                            required: true,
                            minlength: 5,
                            maxlength: 50,
                        }
                    },
                    messages: {
                        "user.email": {
                            required: "This field is required!",
                            email: "Email input is invalid"
                        },
                        "user.name": {
                            required: "This field is required",
                            minlength: "At least 5 letters",
                            maxlength: "At most 50 letters",
                        },
                        "user.password": {
                            required: "This field is required!",
                            minlength: "At least 8 letters",
                            maxlength: "At most 50 letters",
                        },
                        "confirm": {
                            required: "This field is required!",
                            equalTo: "Confirm password is not valid"
                        },
                        "user.phone": {
                            required: "This field is required!",
                            matches: "Invalid phone number"
                        },
                        "user.address": {
                            required: "This field is required!",
                            minlength: "At least 5 letters",
                            maxlength: "At most 50 letters",
                        }
                    },
                });
                
                document.getElementById("btnReset").addEventListener('click', () => {
                    validator.resetForm();
                });
            });




        </script>


    </body>
</html>
