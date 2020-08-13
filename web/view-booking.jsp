<%-- 
    Document   : view-cart.jsp
    Created on : Jun 18, 2020, 3:25:03 PM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View booking page</title>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
        <style type="text/css">

            .btn-cart{
                font-size: 1.05em !important;
            }
            .book-now{
                background-color: #35ff53 !important;
                color: gray !important;
            }
            .book-now:hover{
                background-color: #00ca17 !important;
                color: white !important;
            }
            th{
                text-align: center !important;
            }
            .action{
                font-size: 1.8em !important;
            }
            .width-60{
                width: 60px !important;
            }
            .img-preview{
                width: 120px;
                border-radius: 4px;
            }
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
            .hint-text {
                float: left;
                margin-top: 10px;
                font-size: 13px;
            }
            #removeModal .modal-dialog {
                position: absolute !important;
                top: 200px;
                right: 150px;
                z-index: 10040;
                overflow: auto;
                overflow-y: auto;
            }
            .btn-confirm{
                color: #fff !important;
                background-color: #007bff !important;
                border-color: #007bff !important;
            }
            td{
                text-align: center;
            }
            .not-valid{
                background-color: #F8D7DA !important;
            }
            .not-valid:hover{
                background-color: #F8D7DA !important;
            }
            .warning{
                background-color: #FFC20B !important;
            }
            .success{
                background-color: #28A745 !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row align-items-center">
                        <div class="col-sm-1">
                            <h2>Booking <b>Management</b></h2>
                        </div>
                        <div class="col-sm-11 text-right">
                            <a href="home" class="btn btn-primary btn-cart">
                                <i class="material-icons"></i> 
                                <span>Add New Device</span>
                            </a>
                            <a href="request-history" class="btn btn-primary btn-cart">
                                <span class="material-icons">
                                    history_edu
                                </span>
                                <span>Request History</span>
                            </a>

                            <s:if test="%{#session.DEVICE_REQUEST != null}">
                                <button class="btn btn-primary book-now btn-cart" data-toggle="modal" data-target="#exampleModal">
                                    <i class="material-icons">
                                        event_available
                                    </i>
                                    <span>Request Now</span>
                                </button>
                                <div class="d-inline-block">
                                    <form id="changeBookingDate" action="change-booking-date" autocomplete="off">
                                        <div class="input-group">
                                            <s:if test="%{#session.DEVICE_REQUEST.dateRange == null}">
                                                <s:set var="alertType" value="%{'alert-danger'}"/>
                                            </s:if>
                                            <s:else>
                                                <s:set var="alertType" value="%{'alert-success'}"/>
                                            </s:else>

                                            <input type="text" name="dateRange" value="<s:property value="#session.DEVICE_REQUEST.dateRange"/>" id="txtDate" class="form-control <s:property value="#alertType"/>" placeholder="Pick Dates Here">

                                            <div class="input-group-append">
                                                <span class="input-group-text" id="basic-addon2">
                                                    <s:if test="%{#session.DEVICE_REQUEST.dateRange == null}">
                                                        <span class="material-icons text-danger" data-toggle="tooltip" data-placement="top" title="Please Pick Booking/Return Date">
                                                            error
                                                        </span>
                                                        <span id="dateRangeNotSet"></span>
                                                    </s:if>
                                                    <s:else>
                                                        <span class="material-icons text-success">
                                                            check_circle
                                                        </span>
                                                    </s:else>
                                                    Booking/Return
                                                </span>
                                            </div>

                                        </div>
                                    </form>

                                </div>
                            </s:if>




                        </div>

                    </div>
                </div>
                <s:if test="%{notValid}">
                    <p class="alert alert-danger d-flex">
                        <span class="material-icons">
                            report
                        </span>
                        There are some errors during booking, please check your cart item! <br/>
                    </p>
                </s:if>

                <s:if test="%{#session.DEVICE_REQUEST == null}">
                    <div class="text-center d-flex align-items-center justify-content-center text-secondary">
                        <img src="img-global/no-data.png" width="100"/>

                        <span style="font-size: 2em">
                            Your cart is empty!!
                        </span>
                    </div>
                </s:if>
                <s:else>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Image</th>						
                                <th>Name</th>		
                                <th>Color</th>
                                <th>Left Quantity</th>
                                <th>Amount</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>

                            <s:iterator status="counter" value="#session.DEVICE_REQUEST.items">
                                <s:set var="isNotValidAmount" value="%{value.deviceItem.leftQuantity < value.amount}"/>
                                <s:set var="isNoDateRange" value="%{value.deviceItem.leftQuantity == -1}"/>
                                <tr class="<s:if test="#isNotValidAmount && !#isNoDateRange">not-valid</s:if>">
                                    <s:form action="update-item" method="POST" theme="simple">
                                        <td>
                                            <span class="d-flex">
                                                <s:property value="#counter.count"/>
                                                <s:if test="%{#isNoDateRange}">
                                                    <s:set var="errRowMsg" value="'Please pick (Booking/Return) Dates'"/>
                                                </s:if>
                                                <s:elseif test="%{#isNotValidAmount}">
                                                    <s:set var="errRowMsg" value="'This item remains ' + value.deviceItem.leftQuantity + ' left!'"/>
                                                </s:elseif>
                                                <s:if test="#isNotValidAmount || #isNoDateRange">
                                                    <span data-toggle="popover" 
                                                          data-placement="left" 
                                                          title="Error Message" 
                                                          data-content="<s:property value="#errRowMsg"/>"
                                                          data-trigger="focus"
                                                          class="error-popover"
                                                          >
                                                        <span class="material-icons text-danger">cancel</span>
                                                    </span>
                                                </s:if>
                                                <s:if test="%{!#isNotValidAmount}">
                                                    <span data-toggle="popover" 
                                                          data-placement="left" 
                                                          title="Success Message" 
                                                          data-content="This request item is ok!"
                                                          data-trigger="focus"
                                                          class="success-popover"
                                                          >
                                                        <span class="material-icons text-success">check_circle</span>
                                                    </span>
                                                </s:if>
                                            </span>

                                        </td>
                                        <td>
                                            <img src="img-global/device-image.png" width="70" class="border rounded"/>
                                        </td>
                                        <td>
                                            <s:property value="value.deviceItem.name"/>
                                        </td>                        
                                        <td>
                                            <s:property value="value.deviceItem.color"/>
                                        </td>                             
                                        <td>
                                            <s:if test="#isNoDateRange">
                                                <span data-toggle="popover" 
                                                      data-placement="left" 
                                                      title="Error Message" 
                                                      data-content="Please pick (Booking/Return) Dates"
                                                      data-trigger="focus"
                                                      class="error-popover"
                                                      >
                                                    <span class="material-icons text-warning">info</span>
                                                </span>
                                            </s:if>
                                            <s:else>
                                                <s:property value="value.deviceItem.leftQuantity"/>
                                            </s:else>
                                        </td>                             
                                        <td>
                                            <span class="form-group d-flex align-items-center justify-content-center mb-0">
                                                <s:textfield name="amount" cssClass="form-control mr-1 width-60" value="%{value.amount}"/>
                                                <s:if test="%{!#isNoDateRange && #isNotValidAmount}">
                                                    <span data-toggle="tooltip" data-placement="top" title="Has <s:property value="value.deviceItem.leftQuantity"/> left!">
                                                        <span class="material-icons text-danger">info</span>
                                                    </span>
                                                </s:if>
                                            </span>
                                        </td>
                                        <td>
                                            <span>
                                                <div class="d-flex justify-content-center">
                                                    <button id="btnOpenRemoveModal" type="button" class="btn text-warning btn-light mr-2 border" data-toggle="modal" data-target="#removeModal" data-id="<s:property value="value.deviceItemId"/>">
                                                        <span class="material-icons action">
                                                            delete_forever
                                                        </span>
                                                    </button>
                                                    <s:hidden name="deviceItemId" value="%{value.deviceItemId}" />
                                                    <button class="btn text-success btn-light border" type="submit">
                                                        <span class="material-icons action">
                                                            update
                                                        </span>
                                                    </button>
                                                </div>
                                            </span>
                                        </td>
                                    </s:form>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table> 
                </s:else>





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
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <form action="send-request" method="POST">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Sending Request</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>Are you sure to send request?</p>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary">Continue</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();


                const generatePopover = (className, cssClass) => {
                    const popover = $(className);
                    popover.popover({
                        trigger: 'hover',
                        template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-header ' + cssClass + '"></h3><div class="popover-body"></div></div>'
                    });
                    popover.on("show.bs.popover", function (e) {
                        console.log("target", e.target);
                        $("[data-toggle='popover']").not(e.target).popover("hide");
                    });
                    return popover;
                };
                const showUtil = (popover, ms) => {
                    popover.first().popover('show');
                    setTimeout(() => {
                        popover.popover('hide');
                    }, ms);
                }
                const successPopover = generatePopover('.success-popover', "success");
                const errorPopover = generatePopover('.error-popover', "warning");
                showUtil(successPopover, 5000);
                showUtil(errorPopover, 5000);

                $('input[name="dateRange"]').daterangepicker(
                        {
                            locale: {
                                format: 'DD/MM/YYYY'
                            },
                            minDate: new Date()
                        });
                $('input[name="dateRange"]').on('apply.daterangepicker', function (ev, picker) {
                    $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
                    $("#changeBookingDate").submit();
                });
                const txtDateEle = document.getElementById("txtDate");
                $('input[name="dateRange"]').on('cancel.daterangepicker', function (ev, picker) {
                    txtDateEle.value = "";
                    $("#changeBookingDate").submit();
                });
                const dateRangeNotSetEle = document.getElementById("dateRangeNotSet");
                console.log(dateRangeNotSetEle);
                if (dateRangeNotSetEle) {
                    txtDateEle.value = "";
                }

                $(document).on("click", "#btnOpenRemoveModal", function () {
                    var itemId = $(this).data('id');
                    document.getElementById("btnRemove").href = 'remove-item?deviceItemId=' + itemId;
                });

            });

        </script>
    </body>
</html>
