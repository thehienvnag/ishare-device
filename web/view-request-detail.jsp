<%-- 
    Document   : view-request-detail
    Created on : Jul 2, 2020, 3:58:53 AM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request Detail Page</title>
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
                    <h2>Requests <b>Detail</b></h2>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Image</th>						
                            <th>Name</th>		
                            <th>Color</th>
                            <th>Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator status="counter" value="listRequestItem">
                            <tr>
                                <td>
                                    <s:property value="#counter.count"/>
                                </td>
                                <td>
                                    <img src="img-global/device-image.png" width="70" class="border rounded"/>
                                </td>
                                <td>
                                    <s:property value="deviceItem.name"/>
                                </td>                        
                                <td>
                                    <s:property value="deviceItem.color"/>
                                </td>                               
                                <td>
                                    <s:property value="amount"/>
                                </td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table> 
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
    </body>
</html>
