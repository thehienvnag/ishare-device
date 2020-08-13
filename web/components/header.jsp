<%-- 
    Document   : header
    Created on : Feb 14, 2020, 4:48:57 PM
    Author     : thehien
--%>

<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Header</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
              crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.9/datepicker.min.css">
        <link rel="shortcut icon" type="image/png" href="../img-global/logo.png">
        <style type="text/css">
            body{
                padding-top: 76px;
            }
            #header{
                position: fixed;
                width: 100%;
                top: 0;
                z-index: 8;
            }
            .circle-avatar{
                border-radius: 50%;
                background-color: gray;
                width: 30px;
                height: 30px;
                display: inline-block;
                padding: 2%;
                text-align: center;
                margin-right: 10px;
                color: white;
            }
            .nav-hover{
                font-weight: bold;
            }
            .nav-hover:hover{
                border-radius: 3px;
                background-color: white;
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
            }
            .navbar-header.col {
                padding: 0 !important;
            }	
            .navbar {		
                background: #fff;
                padding-left: 16px;
                padding-right: 16px;
                border-bottom: 1px solid #d6d6d6;
                box-shadow: 0 0 4px rgba(0,0,0,.1);
            }
            .nav-link img {
                border-radius: 50%;
                width: 36px;
                height: 36px;
                margin: -8px 0;
                float: left;
                margin-right: 10px;
            }
            .navbar .navbar-brand {
                color: #555;
                padding-left: 0;
                font-family: 'Merienda One', sans-serif;
            }
            .navbar .navbar-brand i {
                font-size: 20px;
                margin-right: 5px;
            }
            .search-box {
                position: relative;
            }	
            .search-box input {
                box-shadow: none;
                padding-right: 35px;
                border-radius: 3px !important;
            }
            .navbar .nav-item i {
                font-size: 18px;
            }
            .navbar .dropdown-item i {
                font-size: 16px;
                min-width: 22px;
            }
            .navbar .nav-item.open > a {
                background: none !important;
            }
            .navbar .dropdown-menu {
                border-radius: 1px;
                border-color: #e5e5e5;
                box-shadow: 0 2px 8px rgba(0,0,0,.05);
                position: absolute;
            }
            .navbar .dropdown-menu li a {
                color: #777;
                padding: 8px 20px;
                line-height: normal;
            }
            .navbar .dropdown-menu li a:hover, .navbar .dropdown-menu li a:active {
                color: #333;
            }	
            .navbar .dropdown-item .material-icons {
                font-size: 21px;
                line-height: 16px;
                vertical-align: middle;
                margin-top: -2px;
            }
            .navbar .badge {
                background: #f44336;
                font-size: 11px;
                border-radius: 20px;
                position: absolute;
                min-width: 10px;
                padding: 4px 6px 0;
                min-height: 18px;
                top: 2px;
            }
            .navbar ul.nav li a.notifications, .navbar ul.nav li a.messages {
                position: relative;
                margin-right: 10px;
            }
            .navbar ul.nav li a.messages {
                margin-right: 20px;
            }
            .navbar a.notifications .badge {
                margin-left: -5px;
            }
            .navbar a.messages .badge {
                margin-left: -4px;
            }	
            .navbar .active a, .navbar .active a:hover, .navbar .active a:focus {
                background: transparent !important;
            }
            .left-8{
                left: 0% !important;
            }
        </style>
    </head>
    <body style="background-image: url('https://www.transparenttextures.com/patterns/inspiration-geometry.png'); background-color: #A277C7 !important">

        <nav id="header" class="navbar navbar-expand-lg navbar-light bg-white">
            <a class="navbar-brand" href="home">
                <img src="img-global/logo.png" width="85"/>
                <img src="img-global/logo-text.png" width="80"/>

            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse ml-5" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <s:if test="%{#session.USER != null}">
                        <li class="nav-item pt-2 mx-2">
                            <a class="nav-link nav-hover btn btn-light border" href="home">HOME</a>
                        </li>

                        <s:if test="%{#session.USER.roleId == 1}">
                            <li class="nav-item pt-2 mx-2">
                                <a class="nav-link nav-hover btn btn-light border" href="view-request-admin">MANAGE REQUEST</a>
                            </li>
                        </s:if>
                    </s:if>
                    <s:else>
                        <li class="nav-item pt-2 mx-2">
                            <a class="nav-link nav-hover btn btn-light border" href="login">LOGIN</a>
                        </li>
                        <li class="nav-item pt-2 mx-2">
                            <a class="nav-link nav-hover btn btn-light border" href="register">REGISTER</a>
                        </li>
                    </s:else>



                </ul>
                <s:if test="%{#session.USER != null}">
                    <ul class="nav navbar-nav navbar-right ml-auto d-flex px-3 align-items-center">
                        <s:if test="%{#session.USER.roleId != 1}">
                            <li class="nav-item">
                                <a href="view-booking" class="notifications btn btn-info border text-white px-3" style="display: flex; align-items: center">
                                    Booking
                                    <span class="ml-2">
                                        <span class="material-icons">
                                            speaker_notes
                                        </span>
                                        <s:if test="%{#session.DEVICE_REQUEST != null}">
                                            <span class="badge"><s:property value="%{#session.DEVICE_REQUEST.items.size()}"/></span>
                                        </s:if>

                                    </span>
                                </a>
                            </li>
                        </s:if>
                        <!--
                        <li class="nav-item">
                            <a href="#" class="nav-link messages">
                                <i class="fa fa-envelope-o">

                                </i>
                                <span class="badge">10</span>
                            </a>
                        </li>-->
                        <li class="nav-item dropdown d-flex align-items-center" >
                            <a href="#" data-toggle="dropdown" 
                               class="px-1 nav-link dropdown-toggle user-action btn btn-light border d-flex align-items-center p-0">
                                <s:if test="%{#session.USER.picture == null}">
                                    <span class="circle-avatar">
                                        <s:property value="%{#session.USER.nameLetter}"/>
                                    </span>
                                </s:if>
                                <s:else>
                                    <img src="<s:property value="%{#session.USER.picture}"/>" width="25"/>
                                </s:else>
                                <span class="user-name p-2">
                                    <s:property value="%{#session.USER.name}"/>
                                    <b class="caret"></b>
                                </span>

                            </a>
                            <ul class="dropdown-menu left-8">
                                <li>
                                    <a href="request-history" class="dropdown-item">
                                        <span class="material-icons">
                                            history_edu
                                        </span> Request History
                                    </a>
                                </li>
                                <li class="divider dropdown-divider"></li>
                                <li>
                                    <a href="logout" class="dropdown-item">
                                        <i class="material-icons"></i> Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>

                </s:if>

            </div>
        </nav>  
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous">
        </script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
                crossorigin="anonymous">
        </script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
                crossorigin="anonymous">
        </script>


    </body>
</html>
