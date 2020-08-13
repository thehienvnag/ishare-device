/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.config.Config;
import hienht.deviceitem.DeviceItemDAO;
import hienht.deviceitem.DeviceItemDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class SearchDeviceAction {

    private String deviceName, dateRange, lastSearchUrl;
    private int cateId, page, totalSearchRow, totalPages;
    private List<Integer> pagingArr;
    private String successMsg;
    private String errMsg;

    private List<DeviceItemDTO> listItem;

    public SearchDeviceAction() {
    }

    public String execute() throws Exception {
        try {
            if (Util.isNotEmptyParam(dateRange) || Util.isNotEmptyParam(deviceName) || cateId > 0) {
                lastSearchUrl = "search?"
                        + "deviceName=" + deviceName
                        + "&dateRange=" + dateRange
                        + "&cateId=" + (cateId == 0 ? "" : cateId);
                Date[] dates = Util.getDateRanges(dateRange);
                DeviceItemDAO deviceItemDAO = new DeviceItemDAO();
                if (page > 0) {
                    listItem = deviceItemDAO.searchItems(deviceName, cateId, dates, page);
                } else {
                    listItem = deviceItemDAO.searchItems(deviceName, cateId, dates);
                }
                totalSearchRow = deviceItemDAO.countTotalSearch(deviceName, cateId, dates);
                totalPages = (int) Math.ceil((double) totalSearchRow / Config.PAGE_SIZE);
                pagingArr = Util.generatePageArr(page, totalPages);
                if (page == 0) {
                    page = 1;
                }

                Map session = ActionContext.getContext().getSession();
                errMsg = (String) session.get(Config.ATTR_SESSION_ERROR_MSG);
                successMsg = (String) session.get(Config.ATTR_SESSION_SUCCESS_MSG);
                session.remove(Config.ATTR_SESSION_ERROR_MSG);
                session.remove(Config.ATTR_SESSION_SUCCESS_MSG);
            }
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), SearchDeviceAction.class);
        }
        return Action.ERROR;
    }

    public String getLastSearchUrl() {
        return lastSearchUrl;
    }

    public void setLastSearchUrl(String lastSearchUrl) {
        this.lastSearchUrl = lastSearchUrl;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public List<DeviceItemDTO> getListItem() {
        return listItem;
    }

    public void setListItem(List<DeviceItemDTO> listItem) {
        this.listItem = listItem;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalSearchRow() {
        return totalSearchRow;
    }

    public void setTotalSearchRow(int totalSearchRow) {
        this.totalSearchRow = totalSearchRow;
    }

    public List<Integer> getPagingArr() {
        return pagingArr;
    }

    public void setPagingArr(List<Integer> pagingArr) {
        this.pagingArr = pagingArr;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
