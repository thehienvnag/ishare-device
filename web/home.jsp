<%-- 
    Document   : home
    Created on : Jun 24, 2020, 3:49:48 PM
    Author     : thehien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
        <style>
            .bg-white {
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
            }
            .paging-bar{
                width: 100%;
                position: fixed;
                bottom: 0;
                right: 0;
            }
            .popover-header{
                background-color: #FFC20B !important;
            }
            .warning{
                background-color: #FFC20B !important;
            }
            .success{
                background-color: #28A745 !important;
            }
            .color-white{
                color: white !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <div style="position: fixed; top: 90px; right: 20px; z-index: 10; min-width: 350px">

            <!-- Then put toasts within -->
            <s:if test="%{successMsg != null}">
                <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header">
                        <span class="material-icons text-success mr-2">check_circle</span>
                        <strong class="mr-auto text-success">SUCCESS</strong>
                        <small class="text-muted">just now</small>
                        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="toast-body">
                        <s:property value="successMsg"/>
                    </div>
                </div>
            </s:if>
            <s:if test="%{errMsg != null}">
                <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header">
                        <span class="material-icons text-warning mr-2">report</span>
                        <strong class="mr-auto text-warning">ERROR</strong>
                        <small class="text-muted">just now</small>
                        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="toast-body">
                        <s:property value="errMsg"/>
                    </div>
                </div>
            </s:if>

        </div>

        <div class="container mt-5" style="max-width: 450px">
            <div class="card bg-light p-4">
                <form action="search" theme="simple">
                    <div class="form-row">
                        <div class="col-md-6">
                            <label>Device name: </label>
                            <s:textfield cssClass="form-control" type="text" name="deviceName"/>
                        </div>
                        <div class="col-md-6">
                            <label>Category: </label>

                            <%--<s:select name="cateName" headerKey="" headerValue="" list="listCate">
</s:select>--%>


                            <select class="custom-select" name="cateId">
                                <option value="">-- Select --</option>
                                <s:iterator value="listCate">
                                    <option
                                        <s:if test="%{cateId == id}">selected</s:if>
                                        value="<s:property value="id"/>">
                                        <s:property value="name"/>
                                    </option>
                                </s:iterator>
                            </select>
                        </div>
                    </div>

                    <label>Pick date range: </label>
                    <s:textfield id="txtDate" cssClass="form-control" name="dateRange" />
                    <s:if test="%{dateRange != null && dateRange.length() > 0 }">
                        <span id="isDateSearched"></span>
                    </s:if>
                    <div class="text-center mt-3 mb-0">
                        <button class="btn btn-success px-5" type="submit">Search</button>
                    </div>
                </form>

            </div>

        </div>

        <div class="container mt-4" style="max-width: 1200px">
            <div class="row justify-content-center" style="margin-bottom: 70px">
                <s:iterator value="listItem">
                    <div class="col-md-4 mb-3"  style="min-width: 400px">
                        <div class="card p-1 bg-light">
                            <div class="d-flex justify-content-center">
                                <img src="img-global/device-image.png" height="130" alt="device-image"/>
                                <div style="width: 67%">
                                    <h5 class="text-success text-center">
                                        <s:property value="name"/>
                                    </h5>
                                    <div class="d-flex">
                                        <div class="alert alert-dark p-1 d-flex" style="width: fit-content; color: <s:property value="colorLower"/>">
                                            <span class="material-icons" >
                                                color_lens
                                            </span>
                                            <b><s:property value="color"/></b>
                                        </div>
                                        <span class="ml-2">
                                            <div class="alert alert-dark p-1 text-right" style="width: fit-content">
                                                <s:property value="cate.name"/>
                                            </div>

                                        </span>


                                    </div>
                                    <s:form action="booking" method="POST" theme="simple">
                                        <p class="d-flex justify-content-between align-items-center">
                                            <span>
                                                <b>Quantity:</b> 
                                                <s:property value="quantity"/>
                                            </span>
                                            <s:url var="redirectUrl" value="search">
                                                <s:param name="deviceName">
                                                    <s:property value="deviceName"/>
                                                </s:param>
                                                <s:param name="cateId">
                                                    <s:property value="cateId"/>
                                                </s:param>
                                                <s:param name="dateRange">
                                                    <s:property value="dateRange"/>
                                                </s:param>
                                            </s:url>
                                            <s:hidden name="redirectUrl" value="%{#redirectUrl}"/>
                                            <s:hidden name="deviceItemId" value="%{id}" />
                                            <s:hidden name="deviceName" value="%{name}" />
                                            <s:hidden name="color" value="%{color}" />
                                            <span 
                                                <s:if test="%{deviceItemId == id && errorAddItem != null}">
                                                    id="openPopover"
                                                    data-toggle="popover" 
                                                    title="Error Booking" 
                                                    data-placement="top"
                                                    data-content="<s:property value="errorAddItem"/>"
                                                </s:if>
                                                >
                                                <button type="submit" class="btn btn-primary mr-3"
                                                        <s:if test="%{deviceItemId == id && errorAddItem != null}">
                                                            disabled
                                                            style="pointer-events: none;"
                                                        </s:if>
                                                        >
                                                    Booking
                                                </button>
                                            </span>

                                        </p>
                                    </s:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </s:iterator>
            </div>
            <s:if test="%{totalPages >= 2}">
                <div class="paging-bar bg-light pt-3">
                    <nav class="container d-flex align-items-center justify-content-between flex-wrap">
                        <div class="hint-text">Found <b><s:property value="totalSearchRow"/></b> entries</div>
                        <ul class="pagination justify-content-end">
                            <li class="page-item <s:if test="%{page <= 1}">disabled</s:if>">
                                <a class="page-link" href="<s:property value="lastSearchUrl"/>&page=<s:property value="%{page - 1}"/>" >Previous</a>
                            </li>
                            <s:iterator value="pagingArr" status="counter">

                                <s:set var="pageIndex" value="pagingArr.get(#counter.index)"/>
                                <s:set var="changeUrl" value="lastSearchUrl + '&page=' + #pageIndex"/>
                                <li class="page-item <s:if test="%{page == #pageIndex}">active</s:if>">
                                    <a class="page-link" href="<s:property value="changeUrl"/>">
                                        <s:property value="#pageIndex"/>
                                    </a>
                                </li>
                            </s:iterator>

                            <li class="page-item <s:if test="%{page >= totalPages}">disabled</s:if>">
                                <a class="page-link" href="<s:property value="lastSearchUrl"/>&page=<s:property value="%{page + 1}"/>">
                                    Next
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>



            </s:if>
        </div>


        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js" integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB" crossorigin="anonymous"></script>
        <script>
            $(function () {
                $('.toast').toast({
                    delay: 15000
                });
                $('.toast').toast('show');
                $('input[name="dateRange"]').daterangepicker(
                        {
                            locale: {
                                format: 'DD/MM/YYYY'
                            },
                            minDate: new Date()
                        });
                const isDateSearched = document.getElementById("isDateSearched");
                if (!isDateSearched) {
                    document.getElementById("txtDate").value = "";
                }
                const txtDateEle = document.getElementById("txtDate");
                $('input[name="dateRange"]').on('cancel.daterangepicker', function (ev, picker) {
                    txtDateEle.value = "";
                });
                const popover = $('[data-toggle="popover"]');
                popover.popover({trigger: 'hover'});
                popover.popover('show');
            });

        </script>

    </body>
</html>
