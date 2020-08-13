/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import hienht.config.Config;
import hienht.devicerequest.DeviceRequestDAO;
import hienht.devicerequest.DeviceRequestDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author thehien
 */
public class SearchRequestAdminAction {

    private String userRequest, dateString, deviceName;
    private List<DeviceRequestDTO> listDeviceRequest;
    private int page, totalSearchRow, totalPages, statusId;
    private List<Integer> pagingArr;
    private String urlRewriting;

    public SearchRequestAdminAction() {
    }

    public String execute() throws Exception {
        try {
            Date importDate = Util.getDateFromString(dateString);
            if (Util.isNotEmptyParam(userRequest) || Util.isNotEmptyParam(deviceName) || importDate != null || statusId > 0) {
                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                if (page > 0) {
                    listDeviceRequest = deviceRequestDAO.searchRequest(deviceName, userRequest, statusId, importDate, page);
                } else {
                    listDeviceRequest = deviceRequestDAO.searchRequest(deviceName, userRequest, statusId, importDate);
                }
                totalSearchRow = deviceRequestDAO.countSearchRequest(deviceName, userRequest, statusId, importDate);
                totalPages = (int) Math.ceil((double) totalSearchRow / Config.PAGE_SIZE);
                pagingArr = Util.generatePageArr(page, totalPages);
                if (page == 0) {
                    page = 1;
                }
                urlRewriting = "search-request-admin"
                        + "?userRequest=" + userRequest
                        + "&dateString=" + dateString
                        + "&deviceName=" + deviceName
                        + "&statusId=" + statusId
                        + "&";
            }
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), SearchRequestAdminAction.class);
        }
        return Action.ERROR;
    }

    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<DeviceRequestDTO> getListDeviceRequest() {
        return listDeviceRequest;
    }

    public void setListDeviceRequest(List<DeviceRequestDTO> listDeviceRequest) {
        this.listDeviceRequest = listDeviceRequest;
    }

    public int getTotalSearchRow() {
        return totalSearchRow;
    }

    public void setTotalSearchRow(int totalSearchRow) {
        this.totalSearchRow = totalSearchRow;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Integer> getPagingArr() {
        return pagingArr;
    }

    public void setPagingArr(List<Integer> pagingArr) {
        this.pagingArr = pagingArr;
    }

    public String getUrlRewriting() {
        return urlRewriting;
    }

    public void setUrlRewriting(String urlRewriting) {
        this.urlRewriting = urlRewriting;
    }

}
