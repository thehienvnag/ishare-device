<%-- 
    Document   : login
    Created on : Jun 26, 2020, 11:05:00 PM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto:300" rel="stylesheet">
        <style type="text/css">
            .modal-login {
                color: #636363;
                max-width: 352px !important;
            }
            .form-control {
                box-shadow: none;		
                font-weight: normal;
                font-size: 13px;
            }
            .form-control:focus {
                border-color: #33cabb;
                box-shadow: 0 0 8px rgba(0,0,0,0.1);
            }
            .or-seperator {
                margin-top: 32px;
                text-align: center;
                border-top: 1px solid #e0e0e0;
            }
            .or-seperator b {
                color: #666;
                padding: 0 8px;
                width: 30px;
                height: 30px;
                font-size: 13px;
                text-align: center;
                line-height: 26px;
                background: #fff;
                display: inline-block;
                border: 1px solid #e0e0e0;
                border-radius: 50%;
                position: relative;
                top: -15px;
                z-index: 1;
            }
            .navbar .checkbox-inline {
                font-size: 13px;
            }
            .navbar .navbar-right .dropdown-toggle::after {
                display: none;
            }
            @media (min-width: 1200px){
            }
            @media (max-width: 768px){

            }
            .modal-content{
                padding: 7%;
            }
            .google-btn {
                width: 184px;
                height: 42px;
                background-color: #4285f4;
                border-radius: 2px;
                box-shadow: 0 3px 4px 0 rgba(0,0,0,.25);
            }
            .google-btn .google-icon-wrapper {
                position: absolute;
                margin-top: 1px;
                margin-left: 1px;
                width: 40px;
                height: 40px;
                border-radius: 2px;
                background-color: #fff;
            }
            .google-btn .google-icon {
                position: absolute;
                margin-top: 11px;
                margin-left: 11px;
                width: 18px;
                height: 18px;
            }
            .google-btn .btn-text {
                float: right;
                margin: 11px 11px 0 0;
                color: #fff;
                font-size: 14px;
                letter-spacing: 0.2px;
                font-family: "Roboto";
            }
            .google-btn:hover{
                box-shadow: 0 0 6px #4285f4;
            }
            .google-btn:active{
                background: #1669F2;
            }
            .display-none{
                visibility: hidden;
            }
        </style>

    </head>
    <body>
        <jsp:include page="components/header.jsp"/>

        <script src="https://www.google.com/recaptcha/api.js"></script>
        <div class="modal-dialog modal-login mt-5">
            <div class="modal-content">

                <jsp:useBean id="googleUtil" class="hienht.google.GoogleUtil"/>

                <s:form onsubmit="return validateRecaptcha(event);" action="logged-user-in" method="POST" theme="simple">

                    <h2 class="text-center">Member Login</h2>
                    <p class="hint-text text-center">Sign in with your social media account</p>
                    <div>
                        <a href="${googleUtil.redirectUrl}"
                           style="display: block; cursor: pointer; margin: 0 auto" 
                           class="google-btn">
                            <div class="google-icon-wrapper">
                                <img class="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                            </div>
                            <p class="btn-text"><b>Sign in with Google</b></p>
                        </a>
                    </div>
                    <div class="or-seperator"><b>or</b></div>

                    <div class="form-group">
                        <s:textfield name="txtEmail" cssClass="form-control" placeholder="Email" />
                    </div>
                    <div class="form-group">
                        <s:password cssClass="form-control" name="txtPassword" placeholder="Password"/>
                    </div>
                    <input type="submit" class="btn btn-primary btn-block" value="Login">

                    <s:if test="%{errMsg != null}">
                        <div class="form-footer text-center alert alert-danger" style="margin-top: 15px">
                            <s:property value="errMsg"/>
                        </div>
                    </s:if>
                    <div 
                        class="g-recaptcha mt-3"
                        data-sitekey="${googleUtil.captchaSiteKey}"
                        data-callback="resolveCaptcha"
                        >
                    </div>
                    <p id="recaptchaMsg" style="display: none" class="mt-2 text-center alert alert-danger">
                        Please resolve above captcha!
                    </p>
                    <p class="mt-3 mb-0 p-2">
                        Don't have an account? Register <a href="register">here</a>
                    </p>
                </s:form>
            </div>
        </div>
        <script>
            window.resolveCaptcha = () => {
                const captchaResponse = grecaptcha.getResponse();
                if (captchaResponse.length != 0) {
                    document.getElementById("recaptchaMsg").style.display = "none";
                }

            }
            const validateRecaptcha = (e) => {
                const captchaResponse = grecaptcha.getResponse();
                if (captchaResponse.length > 0) {
                    return true;
                }
                e.preventDefault();
                document.getElementById("recaptchaMsg").style.display = "block";
                return false;

            }
        </script>

    </body>
</html>
