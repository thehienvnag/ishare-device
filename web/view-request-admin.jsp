<%-- 
    Document   : request-history
    Created on : Jul 2, 2020, 2:48:50 AM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Request Admin Page</title>
        <style type="text/css">
            body {
                color: #566787;
                background: #f5f5f5;
                font-family: 'Varela Round', sans-serif;
                font-size: 13px;
            }
            .table-wrapper {
                background: #fff;
                padding: 20px 25px;
                margin: 30px 0;
                border-radius: 3px;
                box-shadow: 0 1px 1px rgba(0,0,0,.05);
            }
            .table-title {
                padding-bottom: 15px;
                background: #299be4;
                color: #fff;
                padding: 16px 30px;
                margin: -20px -25px 10px;
                border-radius: 3px 3px 0 0;
            }
            .table-title h2 {
                margin: 5px 0 0;
                font-size: 24px;
            }
            .table-title .btn {
                color: #566787;
                float: right;
                font-size: 13px;
                background: #fff;
                border: none;
                min-width: 50px;
                border-radius: 2px;
                border: none;
                outline: none !important;
                margin-left: 10px;
            }
            .table-title .btn:hover, .table-title .btn:focus {
                color: #566787;
                background: #f2f2f2;
            }
            .table-title .btn i {
                float: left;
                font-size: 21px;
                margin-right: 5px;
            }
            .table-title .btn span {
                float: left;
                margin-top: 2px;
            }
            .page-item a{
                border: 1px solid #dee2e6 !important;
                padding: 2px 10px !important;
            }
            table.table tr th, table.table tr td {
                border-color: #e9e9e9;
                padding: 12px 15px;
                vertical-align: middle;
            }
            table.table tr th:first-child {
                width: 60px;
            }
            table.table tr th:last-child {
                width: 100px;
            }
            table.table-striped tbody tr:nth-of-type(odd) {
                background-color: #fcfcfc;
            }
            table.table-striped.table-hover tbody tr:hover {
                background: #f5f5f5;
            }
            table.table th i {
                font-size: 13px;
                margin: 0 5px;
                cursor: pointer;
            }	
            .hint-text{
                font-size: 18px;
            }
            table.table td:last-child i {
                opacity: 0.9;
                font-size: 22px;
                margin: 0 5px;
            }
            table.table td a {
                font-weight: bold;
                color: #566787;
                display: inline-block;
                text-decoration: none;
            }
            table.table td a:hover {
                color: #2196F3;
            }
            table.table td a.settings {
                color: #2196F3;
            }
            table.table td a.delete {
                color: #F44336;
            }
            table.table td i {
                font-size: 19px;
            }
            table.table .avatar {
                border-radius: 50%;
                vertical-align: middle;
                margin-right: 10px;
            }
            .status {
                font-size: 30px;
                margin: 2px 2px 0 0;
                display: inline-block;
                vertical-align: middle;
                line-height: 10px;
            }
            .text-success {
                color: #10c469;
            }
            .text-info {
                color: #62c9e8;
            }
            .text-warning {
                color: #FFC107;
            }
            .text-danger {
                color: #ff5b5b;
            }
            .pagination {
                float: right;
                margin: 0 0 5px;
                padding-bottom: 10px;
            }
            .pagination li a {
                border: none;
                font-size: 18px;
                min-width: 30px;
                min-height: 30px;
                color: #999;
                margin: 0 2px;
                line-height: 30px;
                border-radius: 2px !important;
                text-align: center;
                padding: 0 6px;
            }
            .pagination li a:hover {
                color: #666;
            }	
            .pagination li.active a, .pagination li.active a.page-link {
                background: #03A9F4;
            }
            .pagination li.active a:hover {        
                background: #0397d6;
            }
            .pagination li.disabled i {
                color: #ccc;
            }
            .pagination li i {
                font-size: 16px;
                padding-top: 6px
            }
            #removeModal .modal-dialog {
                position: absolute !important;
                top: 200px;
                right: 150px;
                z-index: 10040;
                overflow: auto;
                overflow-y: auto;
            }
            .btn-primary {
                font-size: 1.1em !important;
                color: #fff !important;
                background-color: #007bff !important;
                border-color: #007bff !important;
            }
            .btn-primary:hover {
                color: #fff !important;
                background-color: #0069d9 !important;
                border-color: #0062cc !important;
            }
            .paging-bar{
                width: 100%;
                position: fixed;
                bottom: 0;
                right: 0;
            }
        </style>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.9/datepicker.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <div class="container mt-5">
            <div class="table-wrapper">
                <div class="table-title bg-info">
                    <div class="row">
                        <div class="col-sm-5">
                            <h2>Requests <b>Management</b></h2>
                        </div>

                        <div id="dropDown"   class="col-sm-7 text-right dropleft">
                            <button 
                                id="btnDropdown"
                                data-toggle="dropdown"
                                aria-haspopup="true" 
                                aria-expanded="false"
                                class="show-search btn btn-light border" 
                                style="font-size: 1.1em">
                                <span class="material-icons text-warning">
                                    filter_alt
                                </span>
                                <span>Filter</span>
                            </button>
                            <form style="width: 500px" action="search-request-admin" class="dropdown-menu p-4" method="GET">
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <div class="input-group mb-3" >
                                            <s:textfield name="userRequest" cssClass="form-control" placeholder="User request" />
                                            <div class="input-group-append">
                                                <span class="material-icons input-group-text text-info" >
                                                    face
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="input-group mb-3" >
                                            <s:select 
                                                name="statusId"
                                                cssClass="custom-select" 
                                                headerValue="-- Select status --" 
                                                headerKey=""
                                                list="listStatus"
                                                listValue="name"
                                                listKey="id"
                                                />
                                            <div class="input-group-append">
                                                <span class="material-icons input-group-text text-info" >
                                                    scatter_plot
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <div class="input-group mb-3" >
                                            <s:textfield name="dateString" cssClass="form-control" placeholder="Import Date" data-toggle="datepicker"/>
                                            <div class="input-group-append">
                                                <span class="material-icons input-group-text text-info" >
                                                    calendar_today
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="input-group mb-3" >
                                            <s:textfield name="deviceName" cssClass="form-control" placeholder="Device name" />
                                            <div class="input-group-append">
                                                <span class="material-icons input-group-text text-info" >
                                                    category
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary" style="float: none; margin: 0 auto; display: block">
                                    <span class="material-icons">
                                        search
                                    </span>
                                    Search
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <s:if test="%{errMsg != null}">
                    <div class="alert alert-danger">

                        <s:iterator var="msg" value="errMsg" status="counter">
                            <p class="d-flex align-items-center p-1">
                                <s:if test="%{#counter.count == 1}">
                                    <span class="material-icons">
                                        report
                                    </span>
                                    <s:property value="#msg"/>
                                </s:if>
                                <s:else>
                                    <s:property value="#msg"/>
                                </s:else>
                            </p>
                        </s:iterator>
                    </div>
                </s:if>
                <s:if test="%{listDeviceRequest == null}">
                    <div class="text-center d-flex align-items-center justify-content-center text-secondary">
                        <img src="img-global/no-data.png" width="100"/>

                        <span style="font-size: 2em">
                            It seems like there's not related data!!
                        </span>
                    </div>
                </s:if>
                <s:else>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>User Name</th>
                                <th>Booking Date</th>						
                                <th>Return Date</th>
                                <th>Import Date</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator status="counter" value="listDeviceRequest">
                                <tr>
                                    <td>
                                        <s:property value="%{(page - 1) * 20 + #counter.count}"/>
                                    </td>
                                    <td>
                                        <s:property value="userInfo.name"/>
                                    </td>
                                    <td>
                                        <s:property value="bookingDateDisplay"/>
                                    </td>
                                    <td>
                                        <s:property value="returnDateDisplay"/>
                                    </td>                        
                                    <td>
                                        <s:property value="importDateDisplay"/>
                                    </td>
                                    <td>
                                        <s:if test="%{statusId == 6}">
                                            <s:set name="colorType" value="'text-success'"/>
                                            <s:set name="textDisplay" value="'Accept'"/>
                                            <s:set name="icon" value="'check_circle'"/>
                                        </s:if>
                                        <s:elseif test="%{statusId == 2}">
                                            <s:if test="%{expired}">
                                                <s:set name="colorType" value="'text-danger'"/>
                                                <s:set name="textDisplay" value="'Expired'"/>
                                                <s:set name="icon" value="'close'"/>
                                            </s:if>
                                            <s:else>
                                                <s:set name="colorType" value="'text-warning'"/>
                                                <s:set name="textDisplay" value="'New'"/>
                                                <s:set name="icon" value="'hdr_strong'"/>
                                            </s:else>
                                        </s:elseif>
                                        <s:elseif test="%{statusId == 7}">
                                            <s:set name="colorType" value="'text-danger'"/>
                                            <s:set name="textDisplay" value="'Delete'"/>
                                            <s:set name="icon" value="'close'"/>
                                        </s:elseif>
                                        <s:elseif test="%{statusId == 5}">
                                            <s:set name="colorType" value="'text-danger'"/>
                                            <s:set name="textDisplay" value="'Inactive'"/>
                                            <s:set name="icon" value="'close'"/>
                                        </s:elseif>
                                        <div class="d-flex align-items-center <s:property value="#colorType"/>">
                                            <span style="font-size: 1.5em" class="material-icons mr-2"><s:property value="#icon"/></span>
                                            <span><b><s:property value="#textDisplay"/></b></span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="d-flex">
                                            <s:set name="param" value="%{'?requestId=' + id}"/>
                                            <a href="view-request<s:property value="#param"/>" class="settings btn btn-info p-1 mr-2" >
                                                <span class="material-icons text-white">
                                                    search
                                                </span>
                                            </a>
                                            <s:if test="%{(statusId == 2 || statusId == 6) && !expired}">
                                                <div class="dropdown">
                                                    <button 
                                                        id="dropDownSetting"
                                                        data-toggle="dropdown"
                                                        aria-haspopup="true" 
                                                        aria-expanded="false"
                                                        class="show-search btn btn-light border" 
                                                        style="font-size: 1.1em">
                                                        <span class="material-icons text-secondary">
                                                            settings
                                                        </span>
                                                    </button>
                                                    <div class="dropdown-menu" aria-labelledby="dropDownSetting">
                                                        <a class="dropdown-item d-flex align-items-center" href="<s:property value="'approve-request?requestId=' + id"/>">
                                                            <span class="material-icons text-success mr-3">
                                                                done
                                                            </span>
                                                            Approve
                                                        </a>
                                                        <a class="dropdown-item d-flex align-items-center" href="<s:property value="'delete-request-admin?requestId=' + id"/>">
                                                            <span class="material-icons text-danger mr-3">
                                                                close
                                                            </span>
                                                            Delete
                                                        </a>
                                                    </div>
                                                </div>
                                            </s:if>
<!--                                                <button id="btnOpenRemoveModal" type="button" class="btn btn-danger" data-toggle="modal" data-target="#removeModal" data-id="<s:property value="id"/>">
       <span class="material-icons text-white">
           delete
       </span>
   </button>-->
                                        </div>
                                    </td>
                                </tr>
                            </s:iterator>

                        </tbody>
                    </table>
                </s:else>



            </div>
        </div>
        <div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel2" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Remove Confirmation</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>
                            Your item selected will be removed?
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <a id="btnRemove" href="remove-item?deviceItemId=" class="btn btn-warning text-white">Continue</a>
                    </div>
                </div>
            </div>
        </div>
        <s:if test="%{totalPages >= 2}">
            <div class="paging-bar bg-light pt-3">
                <nav class="container d-flex align-items-center justify-content-between flex-wrap">
                    <div class="hint-text">Found <b><s:property value="totalSearchRow"/></b> entries</div>
                    <ul class="pagination justify-content-end">
                        <li class="page-item <s:if test="%{page <= 1}">disabled</s:if>">
                            <a class="page-link" href="<s:property value="urlRewriting + 'page=' + %{page - 1}"/>" >Previous</a>
                        </li>
                        <s:iterator value="pagingArr" status="counter">

                            <s:set var="pageIndex" value="pagingArr.get(#counter.index)"/>
                            <s:set var="changeUrl" value="urlRewriting + 'page=' + #pageIndex"/>
                            <li class="page-item <s:if test="%{page == #pageIndex}">active</s:if>">
                                <a class="page-link" href="<s:property value="changeUrl"/>">
                                    <s:property value="#pageIndex"/>
                                </a>
                            </li>
                        </s:iterator>

                        <li class="page-item <s:if test="%{page >= totalPages}">disabled</s:if>">
                            <a class="page-link" href="<s:property value="urlRewriting + 'page=' + %{page + 1}"/>">
                                Next
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </s:if>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.9/datepicker.min.js"></script>
        <script>
            $(function () {

                const datePicker = $('[data-toggle="datepicker"]').datepicker({
                    format: 'dd-mm-yyyy',
                    autoHide: true
                });
                $(".dropdown-menu").on('click', function (e) {

                    if (!$(e.target).is($('#dateString'))) {
                        datePicker.datepicker('hide');
                    }

                });

                $(document).on('click', '#dropDown .dropdown-menu', function (e) {
                    e.stopPropagation();
                });

                $(document).on("click", "#btnOpenRemoveModal", function () {
                    var requestId = $(this).data('id');
                    document.getElementById("btnRemove").href = 'delete-request-user?requestId=' + requestId;
                });



            });
        </script>
    </body>
</html>
